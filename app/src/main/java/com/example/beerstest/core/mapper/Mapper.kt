package com.example.beerstest.core.mapper

import java.lang.Exception

abstract class Mapper<Input : Any, Output : Any> {

    fun map(input: Input): Output = try {
        mapper(input)
    } catch (throwable: Throwable) {
        throw MapperException("Could not map ${input::class.simpleName}", throwable)
    }

    protected abstract fun mapper(input: Input): Output
}

class MapperException(message: String, throwable: Throwable? = null) : Exception(message, throwable)
