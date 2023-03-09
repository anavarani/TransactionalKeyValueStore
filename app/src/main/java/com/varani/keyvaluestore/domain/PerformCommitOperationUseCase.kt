package com.varani.keyvaluestore.domain

import com.varani.keyvaluestore.data.KeyValueStoreRepository
import com.varani.keyvaluestore.domain.model.OperationError
import com.varani.keyvaluestore.domain.model.OperationResult
import javax.inject.Inject

class PerformCommitOperationUseCase @Inject constructor(
    private val repository: KeyValueStoreRepository
) {
    operator fun invoke() {
        val count = repository.stack.size

        if (repository.stack.size > 1) {
            repository.stack.last().map.forEach { (key, value) ->
                repository.stack.getOrNull(count - 2)?.map?.set(key, value)
            }
            OperationResult.Success(Unit)
        } else {
            OperationResult.Error(OperationError.NoTransaction)
        }
        repository.commitTransaction()
    }
}