package com.whyadnanshah.mockly.SavedScreen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.whyadnanshah.mockly.viewModel.TestViewModel

@Composable
fun SavedTestDialog(
    onDismiss: () -> Unit,
    course: String,
    paperName: String,
    questions: String,
    difficulty: String,
    response: String
) {

    val viewModel : TestViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState()

    var isHintDialog by remember { mutableStateOf(false) }
    var isHintField by remember { mutableStateOf(false) }
    var hintQuestion by remember { mutableStateOf("") }
    var hintQuestionInt = hintQuestion.toIntOrNull() ?: 0
    var isAttemptDialog by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    modifier = Modifier.clickable { onDismiss() }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = course,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Paper Name : $paperName", fontSize = 20.sp)
                Text(text = "No. of questions: $questions", fontSize = 20.sp)
                Text(text = "Difficulty: $difficulty", fontSize = 20.sp)
                Text(text = response, modifier = Modifier.align(Alignment.CenterHorizontally))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        modifier = Modifier.wrapContentSize(),
                        onClick = { isHintField = !isHintField }
                    ) {
                        Text("Hints")
                    }

                    Button(
                        modifier = Modifier.wrapContentSize(),
                        onClick = { isAttemptDialog = true }
                    ) {
                        Text("Attempt")
                    }
                }
                AnimatedVisibility(
                    visible = isHintField,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    OutlinedTextField(
                        value = hintQuestion,
                        onValueChange = {  hintQuestion = it },
                        label = { Text("Hint for Question Number") },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(10.dp),
                        trailingIcon = {
                            Icon(modifier = Modifier
                                .clickable(
                                    onClick = {
                                        if(hintQuestionInt > questions.toInt())
                                            isHintDialog = false
                                        else {
                                            isHintDialog = true
                                            isHintField = !isHintField
                                            viewModel.generateTest(
                                                testResponse = response,
                                                hintQuestion = hintQuestion
                                            )
                                        }
                                    }
                                ),
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send msg"
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = hintQuestionInt > questions.toInt(),
                    )
                }
            }
        }
    }
    if (isHintDialog) {
        HintDialog(
            onDismiss = { isHintDialog = false },
            course = course,
            hintQuestion = hintQuestion,
            paperName = paperName,
            difficulty = difficulty,
            response = response,
            uiState = uiState
        )
    }
    if (isAttemptDialog) {
        AttemptDialog(
            onDismiss = { isAttemptDialog = false },
            paperName = paperName,
            response = response
        )
    }
}

@Composable
fun AttemptDialog(onDismiss: () -> Unit, paperName: String, response: String) {
//    TODO("Not yet implemented")
}