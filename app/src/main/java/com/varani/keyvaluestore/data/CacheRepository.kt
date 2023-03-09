package com.varani.keyvaluestore.data

import java.util.*
import javax.inject.Inject

class CacheRepository @Inject constructor(
) : KeyValueStoreRepository {
    override val stack: LinkedList<Transaction> = LinkedList()

    init {
        // initialize stack with a new transaction
        startNewSession()
    }

    override fun getValue(key: String): String = stack.last.map[key].orEmpty()

    override fun setKeyValue(key: String, value: String) {
        stack.last.map[key] = value
    }

    override fun deleteKey(key: String) {
        stack.last.map.remove(key)
    }

    override fun countValues(value: String): Int =
        Collections.frequency(stack.last.map.values, value)

    override fun beginNewTransaction() {
        val branchNewTransaction = stack.last.map.toMutableMap()
        stack.add(Transaction(branchNewTransaction))
    }

    override fun commitTransaction() {
        stack.removeLast()
    }

    override fun rollbackLastTransaction() {
        stack.removeLast()
    }

    override fun startNewSession() {
        stack.clear()
        val newTransactionMap = hashMapOf<String, String>()
        stack.add(Transaction(newTransactionMap))
    }
}

data class Transaction(
    val map: MutableMap<String, String> = hashMapOf()
)