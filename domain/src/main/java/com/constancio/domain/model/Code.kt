package com.constancio.domain.model

/**
 * Represent a single Code.
 *
 * @property path The path from where the response code was fetched.
 * @property responseCode The response code returned from API.
 */
data class Code(
    val path: String,
    val responseCode: String
)