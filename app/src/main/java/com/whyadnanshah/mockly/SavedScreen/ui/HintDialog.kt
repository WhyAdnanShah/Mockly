package com.whyadnanshah.mockly.SavedScreen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.whyadnanshah.mockly.viewModel.TestUiState
import kotlinx.coroutines.delay

@Composable
fun HintDialog(
    onDismiss: () -> Unit,
    hintQuestion: String,
    uiState: State<TestUiState>
) {
    val hintResponse = uiState.value.generatedTest
        .replace("\\n" , "\n")
        .replace("**", "")
        .replace("\n", "\n\n")
        .replace("[", "")
        .replace("]", "")
        .replace("```json", "")
        .replace("```", "")
        .replace("\"", "")
        .replace("{", "")
        .replace("}", "")
        .replace(".,", ".")
        .replace("?,", "?")
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier
                .wrapContentSize()
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ){
                Text("Hint for Q$hintQuestion", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                when{
                    uiState.value.generatedTest.isNotEmpty()-> {
                        var textIndex by remember { mutableIntStateOf(0) }
                        val animatedText = hintResponse.take(textIndex.coerceAtMost(hintResponse.length))
                        LaunchedEffect(uiState.value.generatedTest) {
                            while (textIndex < uiState.value.generatedTest.length){
                                textIndex++
                                delay(1)
                            }
                        }
                        Text(text = "\"$animatedText\"", modifier = Modifier.padding(16.dp))
                    }
                    uiState.value.errorMessage != null -> {
                        Text(text = "Error :${uiState.value.errorMessage}")
                    }
                    uiState.value.isLoading -> {
                    }
                }
            }
        }
    }
}