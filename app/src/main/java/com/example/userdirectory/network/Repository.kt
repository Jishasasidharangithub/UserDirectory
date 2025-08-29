package com.example.userdirectory.network

import com.example.userdirectory.model.response.UserListResponseItem
import com.example.userdirectory.model.response.UserPostResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val service: ApiService,
) {
    suspend fun usersList(): Flow<NetworkResult<List<UserListResponseItem>>> {
        return apiCall { service.usersList() }
    }

    suspend fun usersPost(userId: Int): Flow<NetworkResult<List<UserPostResponseItem>>> {
        return apiCall { service.usersPost(userId) }
    }
    private suspend fun <T> apiCall(
        apiCall: suspend () -> Response<T>
    ): Flow<NetworkResult<T>> {
        return flow {
            emit(NetworkResult.Loading)
            val response = apiCall()

            val result: NetworkResult<T> = if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                val msg = response.errorBody()?.string()?.let {
                    JSONObject(it).optString("message")
                }
                NetworkResult.Failure(
                    code = response.code(),
                    throwable = HttpException(response),
                    message = msg ?: "Something went wrong"
                )
            }

            emit(result)
        }.catch { throwable ->
            emit(handleNetworkExceptions(throwable))
        }.flowOn(Dispatchers.IO)
    }

    private fun handleNetworkExceptions(
        throwable: Throwable
    ): NetworkResult.Failure {
        return when (throwable) {
            is UnknownHostException, is SocketTimeoutException,
            is NoRouteToHostException, is ConnectException, is IOException -> {
                NetworkResult.Failure(
                    code = 503,
                    throwable = throwable,
                    message = "No internet connection"
                )
            }
            is HttpException -> {
                NetworkResult.Failure(
                    code = throwable.code(),
                    throwable = throwable,
                    message = throwable.response()?.message() ?: "HTTP error"
                )
            }
            else -> {
                NetworkResult.Failure(
                    code = 500,
                    throwable = throwable,
                    message = throwable.message ?: "Unknown error occurred."
                )
            }
        }
    }
}
