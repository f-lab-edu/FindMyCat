package com.flab.findmycat.data.database

sealed class Status<M> {

    class Loading<M> : Status<M>()

    data class Success<M>(val data: M) : Status<M>()

    data class Error<M>(val errorMessage: String) : Status<M>()

    companion object {

        fun <M> loading() = Loading<M>()

        fun <M> success(data: M) =
            Success(data)

        fun <M> error(errorMessage: String) = Error<M>(errorMessage)
    }
}
