package com.varani.keyvaluestore.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.varani.keyvaluestore.ui.operation.OperationViewModel

@Composable
fun SimpleAlertDialog(
    viewModel: OperationViewModel = hiltViewModel()
) {
    AlertDialog(
        onDismissRequest = { viewModel.onDialogDismiss() },
        confirmButton = {
            TextButton(onClick = { viewModel.onDialogConfirm() })
            { Text(text = "OK") }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.onDialogDismiss() })
            { Text(text = "Cancel") }
        },
        title = { Text(text = "Please confirm") },
        text = { Text(text = "Do you want to continue with this operation?") }
    )
}