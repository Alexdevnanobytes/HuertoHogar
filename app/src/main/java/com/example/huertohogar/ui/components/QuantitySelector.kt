package com.example.huertohogar.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions   // ← IMPORT CORRECTO
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable

fun QuantitySelector(
    value: Int,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember(value) { mutableStateOf(value.toString()) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            onClick = {
                val v = (text.toIntOrNull() ?: 0).coerceAtLeast(1) - 1
                text = v.toString()
                onChange(v)
            }
        ) { Icon(Icons.Filled.Remove, contentDescription = "Menos") }

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it.filter { c -> c.isDigit() }.take(3) // hasta 3 dígitos
                val v = text.toIntOrNull() ?: 0
                onChange(v)
            },
            singleLine = true,
            modifier = Modifier.width(80.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )

        )

        IconButton(
            onClick = {
                val v = (text.toIntOrNull() ?: 0) + 1
                text = v.toString()
                onChange(v)
            }
        ) { Icon(Icons.Filled.Add, contentDescription = "Más") }
    }
}
