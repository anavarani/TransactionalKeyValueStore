package com.varani.keyvaluestore.data

import java.util.*

interface KeyValueStoreRepository {
    val stack: LinkedList<Transaction>

    fun getValue(key: String): String
    fun setKeyValue(key: String, value: String)
    fun deleteKey(key: String)
    fun countValues(value: String): Int
    fun beginNewTransaction()
    fun commitTransaction()
    fun rollbackLastTransaction()
    fun startNewSession()
}