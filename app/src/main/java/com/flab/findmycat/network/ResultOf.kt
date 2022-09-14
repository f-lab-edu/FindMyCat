package com.flab.findmycat.network

sealed class ResultOf<out T> {
    object Loading : ResultOf<Nothing>()
    data class Success<out R>(val value: R) : ResultOf<R>()
    data class Failure(
        val message: String?
    ) : ResultOf<Nothing>()
}
