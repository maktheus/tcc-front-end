package core

sealed class Result<out T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Failure(val throwable: Throwable) : Result<Nothing>()
}

inline fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> {
    if (this is Result.Success) block(value)
    return this
}

inline fun <T> Result<T>.onFailure(block: (Throwable) -> Unit): Result<T> {
    if (this is Result.Failure) block(throwable)
    return this
}
