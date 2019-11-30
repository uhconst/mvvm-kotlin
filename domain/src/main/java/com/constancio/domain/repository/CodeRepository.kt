package com.constancio.domain.repository

import com.constancio.domain.model.Code
import io.reactivex.Completable
import io.reactivex.Single

interface CodeRepository {
    fun getNextPath(): Completable

    fun getResponseCode(): Single<Code>

    fun getTimesFetched(): Single<Int>
}