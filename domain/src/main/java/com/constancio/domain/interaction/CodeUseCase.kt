package com.constancio.domain.interaction

import com.constancio.domain.model.Code
import com.constancio.domain.repository.CodeRepository
import io.reactivex.Completable
import io.reactivex.Single

class CodeUseCase(
    private val codeRepository: CodeRepository
) {
    fun getNextPath(): Completable =
        codeRepository.getNextPath()

    fun getResponseCode(): Single<Code> =
        codeRepository.getResponseCode()
}
