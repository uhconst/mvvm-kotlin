package com.constancio.data.remote

import com.constancio.data.local.remote.dto.CodeDto
import com.constancio.data.remote.interceptor.RemoteRequestInterceptor
import com.constancio.data.remote.interceptor.RxRemoteErrorInterceptor
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

/**
 * REST API for Code Service
 */
interface CodeService {

    /**
     * Attempts to get [CodeDto.PathResponse] for next path.
     */
    @GET("/")
    fun getPath(): Single<CodeDto.PathResponse>

    /**
     * Attempts to get [CodeDto.CodeResponse] with path of [String] in Single.
     */
    @GET("/{nextPath}/")
    fun getCode(@Path("nextPath") path: String): Single<CodeDto.CodeResponse>

    companion object {
        fun createCodeService(
            baseUrl: String,
            requestInterceptor: RemoteRequestInterceptor,
            rxRemoteErrorInterceptor: RxRemoteErrorInterceptor
        ): CodeService {
            val client = OkHttpClient().newBuilder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(rxRemoteErrorInterceptor)
                .addInterceptor(getHttpLoggingInterceptor())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(baseUrl)
                .build()
                .create(CodeService::class.java)
        }

        private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return loggingInterceptor
        }
    }
}