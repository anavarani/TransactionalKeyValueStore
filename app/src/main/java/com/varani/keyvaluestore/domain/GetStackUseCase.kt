package com.varani.keyvaluestore.domain

import com.varani.keyvaluestore.data.KeyValueStoreRepository
import javax.inject.Inject

class GetStackUseCase @Inject constructor(
    private val repository: KeyValueStoreRepository
) {
    operator fun invoke(): String {
        return if (repository.stack.isNotEmpty()) {
            val lastTransaction = repository.stack.last.map

            val string = lastTransaction.entries.joinToString("\n") {
                "${it.key} ${it.value}"
            }

            string
        } else {
            ""
        }
    }
}