package com.varani.keyvaluestore.model

data class Command(
    var operation: Operation = Operation.GET,
    val key: String = "",
    val value: String = ""
) {
    override fun toString(): String {
        return "$operation $key $value"
    }

    val keyEnabled: Boolean = when (operation) {
        Operation.GET,
        Operation.SET,
        Operation.DELETE -> true
        else -> false
    }

    val valueEnabled: Boolean = when (operation) {
        Operation.SET,
        Operation.COUNT -> true
        else -> false
    }
}