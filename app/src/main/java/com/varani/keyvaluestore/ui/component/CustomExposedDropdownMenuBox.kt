package com.varani.keyvaluestore.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.varani.keyvaluestore.model.Operation
import java.util.*

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomExposedDropdownMenuBox(
    options: List<String>,
    commandSelected: Operation,
    onCommandSelect: (command: Operation) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = commandSelected.name,
            onValueChange = { },
            label = { Text("Commands") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onTertiary,
                placeholderColor = MaterialTheme.colorScheme.tertiary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onCommandSelect(Operation.valueOf(selectionOption))
                    },
                    text = {
                        Text(
                            text = selectionOption,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                )
            }
        }
    }
}