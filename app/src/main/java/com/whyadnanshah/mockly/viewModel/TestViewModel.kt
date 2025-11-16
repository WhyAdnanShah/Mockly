package com.whyadnanshah.mockly.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyadnanshah.mockly.HomeScreen.data.APIService
import com.whyadnanshah.mockly.HomeScreen.data.TestRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

class TestViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TestUiState())
    val uiState: StateFlow<TestUiState> = _uiState.asStateFlow()

    private val myApiService = Retrofit.Builder()
        .baseUrl("https://notably-fontal-noe.ngrok-free.dev")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(APIService::class.java)
    fun generateTest(
        course: String = "",
        subject: String = "" ,
        topics: String = "",
        difficulty: String = "",
        questions: String = "" ,
        format: String = "",
        info: String = "",
        testResponse: String = "",
        hintQuestion: String= ""
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val request = TestRequest(
                    course = course,
                    subject = subject,
                    topics = topics,
                    difficulty = difficulty,
                    questions = questions,
                    format = format,
                    info = info,
                    testResponse = testResponse,
                    hintQuestion = hintQuestion
                )

                val response = myApiService.generateTest(request)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    generatedTest = response.response
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to generate the test response: ${e.message}"
                )
            }
        }
    }
}

data class TestUiState(
    val isLoading: Boolean = false,
    val generatedTest: String = "",
    val errorMessage: String? = null
)