package com.varani.keyvaluestore.ui.operation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.varani.keyvaluestore.data.KeyValueStoreRepository
import com.varani.keyvaluestore.domain.*
import com.varani.keyvaluestore.domain.model.OperationResult
import com.varani.keyvaluestore.model.Command
import com.varani.keyvaluestore.model.Operation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OperationViewModel @Inject constructor(
    private val performGetOperationUseCase: PerformGetOperationUseCase,
    private val performSetOperationUseCase: PerformSetOperationUseCase,
    private val performDeleteOperationUseCase: PerformDeleteOperationUseCase,
    private val performCountOperationUseCase: PerformCountOperationUseCase,
    private val performBeginOperationUseCase: PerformBeginOperationUseCase,
    private val performCommitOperationUseCase: PerformCommitOperationUseCase,
    private val performRollbackOperationUseCase: PerformRollbackOperationUseCase,
    private val getStackUseCase: GetStackUseCase,
    private val repository: KeyValueStoreRepository,
) : ViewModel() {

    private var _inputStateUi = MutableLiveData<Command>()
    val inputStateUi: LiveData<Command>
        get() = _inputStateUi

    var commandsList = mutableStateListOf<Command>()
    var messageStateUi by mutableStateOf("")
    var stackStateUi by mutableStateOf("")

    init {
        _inputStateUi.value = Command()
    }

    fun onSubmit() {
        _inputStateUi.value?.let { command ->

            if (isCommandValid(command)) {

                when (command.operation) {
                    Operation.GET -> handleGetCommand(command.key)
                    Operation.SET -> handleSetCommand(command.key, command.value)
                    Operation.DELETE -> handleDeleteCommand(command.key)
                    Operation.COUNT -> handleCountCommand(command.value)
                    Operation.BEGIN -> handleBeginCommand()
                    Operation.COMMIT -> handleCommitCommand()
                    Operation.ROLLBACK -> handleRollbackCommand()
                }

                updateCommandList(command)
                stackStateUi = getStackUseCase.invoke()
            } else {
                messageStateUi = "invalid command"
            }
        }
    }

    private fun isCommandValid(command: Command): Boolean {
        return when (command.operation) {
            Operation.GET -> command.key.isNotEmpty()
            Operation.SET -> command.key.isNotEmpty() && command.value.isNotEmpty()
            Operation.COUNT -> command.value.isNotEmpty()
            Operation.DELETE -> command.key.isNotEmpty()
            else -> true
        }
    }

    private fun handleGetCommand(key: String) {
        messageStateUi = when (val result = performGetOperationUseCase.invoke(key)) {
            is OperationResult.Success -> "${key}: ${result.data}"
            is OperationResult.Error -> result.operationError.message
        }
    }

    private fun handleSetCommand(key: String, value: String) {
        performSetOperationUseCase.invoke(key, value)
    }

    private fun handleDeleteCommand(key: String) {
        performDeleteOperationUseCase.invoke(key)
    }

    private fun handleCountCommand(value: String) {
        val result = performCountOperationUseCase.invoke(value)
        messageStateUi = "Count: ${result.data}"
    }

    private fun handleBeginCommand() {
        performBeginOperationUseCase.invoke()
    }

    private fun handleCommitCommand() {
        performCommitOperationUseCase.invoke()
    }

    private fun handleRollbackCommand() {
        messageStateUi = when (val result = performRollbackOperationUseCase.invoke()) {
            is OperationResult.Success -> ""
            is OperationResult.Error -> result.operationError.message
        }
    }

    private fun updateCommandList(command: Command) {
        commandsList.add(command)
    }

    fun onOperationSelected(operation: Operation) {
        _inputStateUi.value = Command(operation = operation)
        messageStateUi = ""
    }

    fun onKeyChanged(key: String) {
        _inputStateUi.value?.let {
            _inputStateUi.value = it.copy(key = key)
        }
    }

    fun onValueChanged(value: String) {
        _inputStateUi.value?.let {
            _inputStateUi.value = it.copy(value = value)
        }
    }

    fun onNewSession() {
        repository.startNewSession()
        _inputStateUi.value = Command()
        commandsList.clear()
        messageStateUi = ""
        stackStateUi = ""
    }
}