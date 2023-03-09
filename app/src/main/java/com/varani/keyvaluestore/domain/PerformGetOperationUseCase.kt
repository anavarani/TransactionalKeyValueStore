package com.varani.keyvaluestore.domain

import com.varani.keyvaluestore.data.KeyValueStoreRepository
import com.varani.keyvaluestore.domain.model.OperationError
import com.varani.keyvaluestore.domain.model.OperationResult
import javax.inject.Inject

class PerformGetOperationUseCase @Inject constructor(
    private val repository: KeyValueStoreRepository
) {
    operator fun invoke(key: String): OperationResult<String> {
        return repository.getValue(key).let {
            if (it.isEmpty()) {
                OperationResult.Error(OperationError.KeyNotSet)
            } else {
                OperationResult.Success(it)
            }
        }
    }
}