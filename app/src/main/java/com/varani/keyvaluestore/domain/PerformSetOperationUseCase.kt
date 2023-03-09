package com.varani.keyvaluestore.domain

import com.varani.keyvaluestore.data.KeyValueStoreRepository
import com.varani.keyvaluestore.domain.model.OperationError
import com.varani.keyvaluestore.domain.model.OperationResult
import javax.inject.Inject

class PerformSetOperationUseCase @Inject constructor(
    private val repository: KeyValueStoreRepository
) {
    operator fun invoke(key: String, value: String): OperationResult<Unit> {
        return try {
            repository.setKeyValue(key, value)
            OperationResult.Success(Unit)
        } catch (e: NoSuchElementException) {
            OperationResult.Error(OperationError.ErrorDuringKeySet)
        }
    }
}

