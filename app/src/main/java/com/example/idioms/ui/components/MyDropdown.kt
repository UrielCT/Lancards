package com.example.idioms.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import com.example.idioms.ui.theme.CommonPaddingLarge_lm
import com.example.idioms.ui.theme.CommonPaddingMicro
import com.example.idioms.ui.theme.CommonPaddingMin
import com.example.idioms.ui.theme.CommonPaddingMinDefault
import com.example.idioms.ui.theme.CommonPaddingNone
import com.example.idioms.ui.theme.CommonPaddingOne
import com.example.idioms.ui.theme.DropdownHeightInMax
import com.example.idioms.ui.theme.DropdownOffSetY

@Composable
fun MyDropdown(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    fun adaptText(text: String): String {
        return text.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(CommonPaddingLarge_lm)
                .padding(vertical = CommonPaddingMicro)
                .border(
                    CommonPaddingOne,
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(CommonPaddingMin)
                )
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(CommonPaddingMin))
                .padding(horizontal = CommonPaddingMinDefault, vertical = CommonPaddingMin)
                .clickable {
                    focusManager.clearFocus()
                    expanded = true
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = adaptText(selectedOption),
                style = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = DropdownHeightInMax)
                .background(MaterialTheme.colorScheme.surface),
            offset = DpOffset(CommonPaddingNone, DropdownOffSetY)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            adaptText(option),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        focusManager.clearFocus()
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }

    }
}