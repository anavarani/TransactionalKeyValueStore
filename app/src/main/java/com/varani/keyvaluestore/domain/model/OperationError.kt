package com.varani.keyvaluestore.domain.model

enum class OperationError(val message: String) {
    KeyNotSet("key not set"),
    ErrorDuringKeySet("error while setting key"),
    NoTransaction("no transaction"),
}