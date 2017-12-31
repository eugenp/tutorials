package com.baeldung.kotlin

sealed class Result<out S, out F> {
    abstract fun <R> map(func: (S) -> R) : Result<R, F>
    abstract fun <R> mapFailure(func: (F) -> R) : Result<S, R>
    abstract fun get() : S?
}

data class Success<out S, out F>(val success: S) : Result<S, F>() {
    override fun <R> map(func: (S) -> R) : Result<R, F> = Success(func(success))
    override fun <R> mapFailure(func: (F) -> R): Result<S, R> = Success(success)
    override fun get(): S? = success
}

data class Failure<out S, out F>(val failure: F) : Result<S, F>() {
    override fun <R> map(func: (S) -> R) : Result<R, F> = Failure(failure)
    override fun <R> mapFailure(func: (F) -> R): Result<S, R> = Failure(func(failure))
    override fun get(): S? = null
}
