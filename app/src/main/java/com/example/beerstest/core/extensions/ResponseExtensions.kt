package com.example.beerstest.core.extensions

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

fun String.fromJsonToResponseBody(): ResponseBody {
    val mediaType = "application/json".toMediaTypeOrNull()
    return toResponseBody(mediaType)
}
