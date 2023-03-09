package com.varani.keyvaluestore.domain

import com.varani.keyvaluestore.data.KeyValueStoreRepository
import com.varani.keyvaluestore.domain.model.OperationError
import com.varani.keyvaluestore.domain.model.OperationResult
import javax.inject.Inject

class PerformRollbackOperationUseCase @Inject constructor(
    private val repository: KeyValueStoreRepository
) {
    operator fun invoke(): OperationResult<Unit> {
        return if (repository.stack.size > 1) {
            OperationResult.Success(repository.rollbackLastTransaction())
        } else {
            OperationResult.Error(OperationError.NoTransaction)
        }
    }
}