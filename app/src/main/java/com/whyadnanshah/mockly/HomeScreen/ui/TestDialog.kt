package com.whyadnanshah.mockly.HomeScreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.whyadnanshah.mockly.R
import com.whyadnanshah.mockly.viewModel.TestUiState
import kotlinx.coroutines.delay

@Composable
fun TestDialog(
    onDismiss: () -> Unit,
    course: String,
    subject: String,
    questions: String,
    difficulty: String,
    uiState: State<TestUiState>
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ){
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 24.dp, bottom = 24.dp)
                ){
                    Text(text = course, fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Paper Name : $subject", fontSize = 20.sp)
                    Text(text = "No. of questions: $questions", fontSize = 20.sp)
                    Text(text = "Difficulty: $difficulty", fontSize = 20.sp)
                }
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                ){
                    when{
                        uiState.value.generatedTest.isNotEmpty()-> {
                            var textIndex by remember { mutableIntStateOf(0) }
                            val animatedText = uiState.value.generatedTest.substring(0, textIndex)
                            LaunchedEffect(uiState.value.generatedTest) {
                                while (textIndex < uiState.value.generatedTest.length){
                                    textIndex++
                                    delay(30)
                                }
                            }
                            Text(text = animatedText)
                        }
                        uiState.value.errorMessage != null -> {
                            Text(text = "Error :${uiState.value.errorMessage}")
                        }
                        uiState.value.isLoading -> {
                            LottieAnimation(composition, iterations = LottieConstants.IterateForever, modifier = Modifier.size(250.dp).align(Alignment.CenterHorizontally))
                        }
                    }
                }
            }
        }
    }
}