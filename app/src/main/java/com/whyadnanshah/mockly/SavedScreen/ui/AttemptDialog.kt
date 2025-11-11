package com.whyadnanshah.mockly.SavedScreen.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.whyadnanshah.mockly.SavedScreen.data.TestEntity
import com.whyadnanshah.mockly.viewModel.SavedTestViewModel
import com.whyadnanshah.mockly.viewModel.TestUiState
import kotlinx.coroutines.delay

@Composable
fun AttemptDialog(
    onDismiss: () -> Unit,
    paperName: String,
    uiState: State<TestUiState>,
    course: String,
    difficulty: String,
    questions: String,
    savedTestViewModel: SavedTestViewModel
) {
    val context  = LocalContext.current
    val attemptedResponse = uiState.value.generatedTest
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
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize().padding(24.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(
                                onClick = onDismiss
                            ),
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                    )
                    Button(
                        onClick = {
                            Toast.makeText(context, "Test Saved", Toast.LENGTH_SHORT).show()
                            val newTest = TestEntity(
                                course = course,
                                paperName = paperName,
                                questions = questions,
                                difficulty = difficulty,
                                response = attemptedResponse
                            )
                            savedTestViewModel.addTest(newTest)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ){
                        Text("Save")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "TEST $course", fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Paper Name : $paperName", fontSize = 20.sp)
                Text(text = "No. of questions: $questions", fontSize = 20.sp)
                Text(text = "Difficulty: $difficulty", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(24.dp))
                when{
                    uiState.value.generatedTest.isNotEmpty()-> {
                        var textIndex by remember { mutableIntStateOf(0) }
                        val animatedText = attemptedResponse.take(textIndex.coerceAtMost(attemptedResponse.length))
                        LaunchedEffect(uiState.value.generatedTest) {
                            while (textIndex < uiState.value.generatedTest.length){
                                textIndex++
                                delay(1)
                            }
                        }
                        Text(text = animatedText)
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