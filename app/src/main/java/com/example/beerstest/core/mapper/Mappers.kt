package com.example.beerstest.core.mapper

import java.lang.Exception

abstract class ApiToDomainMapper<Input : Any, Output : Any> {

    fun toDomain(input: Input): Output = try {
        map(input)
    } catch (throwable: Throwable) {
        throw ApiMapperException("Could not map ${input::class.simpleName}", throwable)
    }

    protected abstract fun map(input: Input): Output
}

class ApiMapperException(message: String, throwable: Throwable? = null) : Exception(message, throwable)
