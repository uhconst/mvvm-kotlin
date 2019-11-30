package com.constancio.data.local.remote.dto

import com.constancio.domain.model.Code
import com.constancio.domain.model.NextPath
import com.google.gson.annotations.SerializedName

sealed class CodeDto {

    /**
     * Represent a single Path Response.
     *
     * @property nextPath The next path to fetch the response code.
     */
    data class PathResponse(
        @SerializedName("next_path")
        val nextPath: String
    ) {

        /** Convert from [PathResponse] to [NextPath]
         * Also extracting the next path substring from http:localhost
         * When use emulator it will not have localhost
         */
        fun toPath() = NextPath(
            nextPath
                .substringAfter("8000/")
                .substringBefore("/")
        )
    }

    /**
     * Represent a single Code Response.
     *
     * @property path The path from where the response code was fetched.
     * @property responseCode The response code returned from API.
     */
    data class CodeResponse(
        val path: String,

        @SerializedName("response_code")
        val responseCode: String
    ) {

        /** Convert from [CodeResponse] to [Code]
         */
        fun toCode() = Code(
            path,
            responseCode
        )
    }
}