package com.constancio.data.repository

import com.constancio.data.local.db.code.CodeDao
import com.constancio.data.local.db.code.PathEntity
import com.constancio.data.local.db.code.ResponseCodeEntity
import com.constancio.data.remote.CodeService
import com.constancio.domain.model.Code
import com.constancio.domain.model.NextPath
import com.constancio.domain.repository.CodeRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Code Repository Implementation
 */
class CodeRepositoryImpl(
    private val service: CodeService,
    private val dao: CodeDao
) : CodeRepository {

    /**
     * Get next path from API.
     * Insert next path into DB
     */
    override fun getNextPath(): Completable =
        service.getPath()
            .map {
                it.toPath()
            }
            .flatMapCompletable {
                dao.insertNextPath(it.toPathEntity())
                return@flatMapCompletable Completable.complete()
            }
            .subscribeOn(Schedulers.io())

    /**
     * Get response code from API using the path from DB.
     * Insert the response code returned to control how many times it was called.
     */
    override fun getResponseCode(): Single<Code> =
        dao.getNextPath()
            .subscribeOn(Schedulers.io())
            .map { entity ->
                try {
                    service.getCode(entity.nextPath)
                        .map {
                            it.toCode().let { code ->
                                dao.insertResponseCode(code.toResponseCodeEntity())
                                return@let code
                            }
                        }
                        .blockingGet()
                } catch (ex: Exception) {
                    null
                }
            }

    /**
     * Extension function to convert `NextPath` to `PathEntity`
     */
    private fun NextPath.toPathEntity() = PathEntity(
        id = null,
        nextPath = nextPath
    )

    /**
     * Extension function to convert `Code` to `ResponseCodeEntity`
     */
    private fun Code.toResponseCodeEntity() = ResponseCodeEntity(
        id = null,
        code = responseCode
    )
}