package com.whyadnanshah.mockly.destinations

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whyadnanshah.mockly.viewModel.SavedTestViewModel

@Composable
fun SavedScreen(savedTestViewModel: SavedTestViewModel, paddingValues: PaddingValues) {
    val savedTest by savedTestViewModel.allTests.collectAsState(initial = emptyList())

    LazyColumn(
        Modifier.fillMaxSize().padding(paddingValues)
    ) {
        if (savedTest.isEmpty()){
            item {
                Text("No Tests Saved")
            }
        }
        else{
            items(savedTest.size) { index ->
                val test = savedTest[index]
                Card(
                    modifier = Modifier.wrapContentSize().padding(8.dp)
                ) {
                    Text("Test Name: ${test.course}")
                    Text("Test Description: ${test.paperName}")
                    Text("Test Duration: ${test.questions}")
                    Text("Test Score: ${test.difficulty}")
                    Text("Test Date: ${test.response}")
                }
            }
        }
    }
}