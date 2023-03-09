package com.varani.keyvaluestore.domain

import com.varani.keyvaluestore.data.KeyValueStoreRepository
import com.varani.keyvaluestore.domain.model.OperationResult
import javax.inject.Inject

class PerformCountOperationUseCase @Inject constructor(
    private val repository: KeyValueStoreRepository
) {
    operator fun invoke(key: String): OperationResult.Success<Int> {
        val count = repository.countValues(key)
        return OperationResult.Success(count)
    }
}