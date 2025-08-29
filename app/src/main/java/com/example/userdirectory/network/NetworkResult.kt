package io.proximety.hilitemall.network

sealed class NetworkResult<out T> {

    data class Success<out T>(val data: T) : NetworkResult<T>()

    data class Failure(
        val code: Int,
        val throwable: Throwable?,
        val message: String? = "Something went wrong!",
    ) : NetworkResult<Nothing>()

    data object Loading : NetworkResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Failure -> "Failure[code=$code, message=$message, throwable=$throwable]"
            Loading -> "Loading"
        }
    }
}

open class Event<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}