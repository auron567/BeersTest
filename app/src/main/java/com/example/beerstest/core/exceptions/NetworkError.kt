package com.example.beerstest.core.exceptions

sealed class NetworkError(override val message: String?) : Throwable(message) {
    class NotFound : NetworkError("Network Error: Not Found")
    class AccessDenied : NetworkError("Network Error: Access Denied")
    class ServiceUnavailable : NetworkError("Network Error: Service Unavailable")
    class Unknown : NetworkError("Network Error: Unknown")
}

fun Int.toNetworkError() = when (this) {
    CODE_NOT_FOUND -> NetworkError.NotFound()
    CODE_UNAUTHORIZED, CODE_NOT_ACCEPTABLE -> NetworkError.AccessDenied()
    CODE_INTERNAL_SERVER_ERROR -> NetworkError.ServiceUnavailable()
    else -> NetworkError.Unknown()
}

private const val CODE_NOT_FOUND = 404
private const val CODE_UNAUTHORIZED = 401
private const val CODE_NOT_ACCEPTABLE = 406
private const val CODE_INTERNAL_SERVER_ERROR = 500
