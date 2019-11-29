package com.constancio.domain.exception

/**
 * Default exception
 */
class DefaultException(
    val code: String = "",
    override val message: String = "Unexpected error"
) : Exception()