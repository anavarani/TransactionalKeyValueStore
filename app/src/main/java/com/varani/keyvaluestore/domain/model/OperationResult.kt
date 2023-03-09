package com.varani.keyvaluestore.domain.model

sealed class OperationResult<out R> {
    data class Success<out T>(val data: T) : OperationResult<T>()
    data class Error(val operationError: OperationError) : OperationResult<Nothing>()
}