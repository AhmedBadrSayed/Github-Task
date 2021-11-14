package com.example.githubtask.models

data class Request<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Request<T> {
            return Request(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String?): Request<T> {
            return Request(Status.ERROR, null, message)
        }

        fun <T> loading(): Request<T> {
            return Request(Status.LOADING, null, null)
        }
    }
}
