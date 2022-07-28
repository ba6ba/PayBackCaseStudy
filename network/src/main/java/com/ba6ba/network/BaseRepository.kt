package com.ba6ba.network

import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

interface BaseRepository {
    suspend fun <T> execute(apiFunction: suspend () -> T): ApiResult<T> =
        callApi { apiFunction.invoke() }
}

private suspend inline fun <T> callApi(crossinline apiFunction: suspend () -> T): ApiResult<T> =
    try {
        ApiResult.Success(apiFunction.invoke())
    } catch (exception: Exception) {
        when (exception) {
            is IOException, is UnknownHostException -> ApiResult.getNetworkFailure()
            is HttpException -> ApiResult.getFailureFromException(exception)
            else -> ApiResult.technicalFailure()
        }
    }
