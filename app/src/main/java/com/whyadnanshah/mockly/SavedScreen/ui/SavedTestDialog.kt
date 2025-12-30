package com.whyadnanshah.mockly.SavedScreen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.whyadnanshah.mockly.PDFGenerator
import com.whyadnanshah.mockly.viewModel.SavedTestViewModel
import com.whyadnanshah.mockly.viewModel.TestViewModel

@Composable
fun SavedTestDialog(
    onDismiss: () -> Unit,
    course: String,
    paperName: String,
    questions: String,
    difficulty: String,
    response: String,
    savedTestViewModel: SavedTestViewModel
) {
    val context = LocalContext.current

    //This will be only for collecting data from the ViewModel
    val viewModel : TestViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState()

    //This will be only for getting hint and opening the hint Dialog
    var isHintDialog by remember { mutableStateOf(false) }
    var isHintButton by remember { mutableStateOf(false) }
    var hintQuestion by remember { mutableStateOf("") }
    var hintQuestionInt = hintQuestion.toIntOrNull() ?: 0

    //This will be only for opening the Attempt Dialog
    var isAttemptDialog by remember { mutableStateOf(false) }
    var showQuestionRatings by remember { mutableStateOf(false) }

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier.clickable { onDismiss() }
                    )
                    Button(
                        onClick = {
                            val pdfGenerator = PDFGenerator(context)
                            pdfGenerator.generateQuestionPaperPDF(
                                course = course,
                                subject = paperName,
                                questions = questions,
                                difficulty = difficulty,
                                testResponse = response,
                                fileName = "${paperName}_Test.pdf"
                            )
                        }

                    ){
                        Text("Export as PDF")
                    }
                }
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
                        onClick = { isHintButton = !isHintButton }
                    ) {
                        Text("Hints")
                    }

                    Button(
                        modifier = Modifier.wrapContentSize(),
                        onClick = {
                            showQuestionRatings = true
                        }
                    ) {
                        Text("Attempt")
                    }
                }
                AnimatedVisibility(
                    visible = isHintButton,
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
                                            isHintButton = !isHintButton
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
            hintQuestion = hintQuestion,
            uiState = uiState
        )
    }

    if (showQuestionRatings) {
        QuestionRatingDialog(
            totalQuestions = questions.toInt(),
            onRatingsComplete = { ratings ->
                showQuestionRatings = false
                isAttemptDialog = true
                generateCustomizedTest(
                    ratings = ratings,
                    course = course,
                    paperName = paperName,
                    questions = questions,
                    viewModel = viewModel
                )
            },
            onDismiss = { showQuestionRatings = false }
        )
    }

    if (isAttemptDialog) {
        AttemptDialog(
            onDismiss = { isAttemptDialog = false },
            course = course,
            paperName = paperName,
            difficulty = "Customized",
            questions = questions,
            uiState = uiState,
            savedTestViewModel = savedTestViewModel
        )
    }
}
@Composable
fun QuestionRatingDialog(
    totalQuestions: Int,
    onRatingsComplete: (List<String>) -> Unit,
    onDismiss: () -> Unit
) {
    val ratings = remember {
        mutableStateListOf<String>().apply {
            repeat(totalQuestions) { add("") }
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.8f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Rate Question Difficulty")
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("How difficult was each question for you?")

                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn {
                    items(totalQuestions) { index ->
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text("Question ${index + 1}:")

                            Spacer(modifier = Modifier.height(8.dp))

                            Row {
                                FilterChip(
                                    selected = ratings[index] == "Easy",
                                    onClick = { ratings[index] = "Easy" },
                                    label = { Text("Easy") }
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                FilterChip(
                                    selected = ratings[index] == "Medium",
                                    onClick = { ratings[index] = "Medium" },
                                    label = { Text("Medium") }
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                FilterChip(
                                    selected = ratings[index] == "Hard",
                                    onClick = { ratings[index] = "Hard" },
                                    label = { Text("Hard") }
                                )
                            }
                        }

                        if (index < totalQuestions - 1) {
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }

                    item{
                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { onRatingsComplete(ratings) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = ratings.all { it.isNotEmpty() }
                        ) {
                            Text("Generate Customized Test")
                        }
                    }
                }
            }
        }
    }
}

fun generateCustomizedTest(
    ratings: List<String>,
    course: String,
    paperName: String,
    questions: String,
    viewModel: TestViewModel
) {
    val hardCount = ratings.count { it == "Hard" }
    val mediumCount = ratings.count { it == "Medium" }
    val easyCount = ratings.count { it == "Easy" }
    val total = ratings.size

    val (targetDifficulty, summary) = when {
        hardCount > total * 0.6 -> Pair(
            "Easy",
            "You found ${hardCount}/${total} questions Hard → Practice Easy questions"
        )
        mediumCount > total * 0.6 -> Pair(
            "Medium",
            "You found ${mediumCount}/${total} questions Medium → More Medium practice"
        )
        easyCount > total * 0.6 -> Pair(
            "Hard",
            "You found ${easyCount}/${total} questions Easy → Challenge with Hard questions"
        )
        hardCount >= mediumCount && hardCount >= easyCount -> Pair(
            "Medium",
            "You found ${hardCount}/${total} questions Hard → Practice Medium questions"
        )
        else -> Pair(
            "Mixed",
            "Mixed ratings → Balanced practice"
        )
    }

    viewModel.generateTest(
        course = course,
        subject = paperName,
        questions = questions,
        difficulty = targetDifficulty,
        info = "Customized practice based on ratings: $summary"
    )
}