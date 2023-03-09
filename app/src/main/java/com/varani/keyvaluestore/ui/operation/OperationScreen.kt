package com.varani.keyvaluestore.ui.operation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.varani.keyvaluestore.model.Command
import com.varani.keyvaluestore.model.Operation
import com.varani.keyvaluestore.ui.component.CustomExposedDropdownMenuBox
import com.varani.keyvaluestore.ui.theme.GreenGecko
import com.varani.keyvaluestore.ui.theme.KeyValueStoreTheme

@Composable
fun OperationScreen(
    modifier: Modifier = Modifier,
) {

    Column(modifier) {
        CommandsSection(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
        MessageSection(
            modifier = Modifier
                .fillMaxWidth()
        )
        InputSection(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
        )
    }
}

@Composable
fun CommandsSection(
    modifier: Modifier
) {
    Row(modifier) {
        CommandsBox(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(8.dp),
        )
        StackBox(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(8.dp),
        )
    }
}

@Composable
fun CommandsBox(
    modifier: Modifier,
    viewModel: OperationViewModel = hiltViewModel()
) {
    Surface(
        modifier = modifier,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = viewModel.commandsList.joinToString("\n"),
                fontSize = 9.sp,
                color = GreenGecko,
                lineHeight = 11.sp
            )
        }
    }
}

@Composable
fun StackBox(
    modifier: Modifier,
    viewModel: OperationViewModel = hiltViewModel()
) {
    Surface(
        modifier = modifier,
        shadowElevation = 2.dp
    ) {
        Column(modifier) {
            Text(
                text = viewModel.stackStateUi,
                fontSize = 9.sp,
                color = GreenGecko,
                lineHeight = 11.sp
            )
        }
    }
}

@Composable
fun MessageSection(
    modifier: Modifier,
    viewModel: OperationViewModel = hiltViewModel()
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = viewModel.messageStateUi,
            fontSize = 14.sp,
            modifier = Modifier
                .padding((8.dp))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputSection(
    modifier: Modifier,
    viewModel: OperationViewModel = hiltViewModel()
) {
    val options = Operation.values().map { it.name }

    val inputStateUi = viewModel.inputStateUi.observeAsState(Command())

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomExposedDropdownMenuBox(
            options,
            inputStateUi.value.operation,
            viewModel::onOperationSelected
        )
        if (inputStateUi.value.keyEnabled) {
            TextField(
                modifier = Modifier.padding(top = 8.dp),
                value = inputStateUi.value.key,
                onValueChange = { viewModel.onKeyChanged(it) },
                maxLines = 1,
                placeholder = { Text(text = "Key") }
            )
        }
        if (inputStateUi.value.valueEnabled) {
            TextField(
                modifier = Modifier.padding(vertical = 8.dp),
                value = inputStateUi.value.value,
                onValueChange = { viewModel.onValueChanged(it) },
                maxLines = 1,
                placeholder = { Text(text = "Value") }
            )
        }

        Row(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.onNewSession()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                )
            ) {
                Text(
                    text = "New Session",
                    color = MaterialTheme.colorScheme.background,
                )

            }
            Spacer(modifier = Modifier.width(80.dp))
            Button(
                modifier = Modifier.weight(1f),
                enabled = viewModel.isSubmitButtonEnabled,
                onClick = {
                    viewModel.onSubmit()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                )
            ) {
                Text(
                    text = "Submit",
                    color = MaterialTheme.colorScheme.background,
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TransactionScreenPreview() {
    KeyValueStoreTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            OperationScreen(modifier = Modifier.fillMaxSize())
        }
    }
}