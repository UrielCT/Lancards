package com.example.idioms.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.example.idioms.ui.theme.CommonPaddingDefault
import com.example.idioms.ui.theme.CommonPaddingMin
import com.example.idioms.ui.theme.CommonPaddingMinDefault
import com.example.idioms.utils.Constants
import kotlinx.coroutines.delay

// ------------------- CustomSnackBar -------------------
class CustomSnackBarState {
    var message by mutableStateOf("")
    var visible by mutableStateOf(false)

    fun showMessage(msg: String) {
        message = msg
        visible = true
    }

    fun dismiss() {
        visible = false
    }
}

@Composable
fun CustomSnackBar(
    state: CustomSnackBarState,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    shape: Shape = RoundedCornerShape(CommonPaddingMinDefault),
    duration: Long = Constants.DURATION_SHORT
) {
    LaunchedEffect(state.visible, state.message) {
        if (state.visible && state.message.isNotBlank()) {
            delay(duration)
            state.dismiss()
        }
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = state.visible && state.message.isNotBlank(),
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight }
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight }
            ) + fadeOut()
        ) {
            Text(
                text = state.message,
                color = textColor,
                modifier = Modifier
                    .background(color = backgroundColor, shape = shape)
                    .padding(vertical = CommonPaddingMin, horizontal = CommonPaddingDefault)
            )
        }
    }
}