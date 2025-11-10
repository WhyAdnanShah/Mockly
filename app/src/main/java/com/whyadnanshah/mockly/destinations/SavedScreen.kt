package com.whyadnanshah.mockly.destinations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.whyadnanshah.mockly.SavedScreen.ui.SavedTestDialog
import com.whyadnanshah.mockly.viewModel.SavedTestViewModel

@Composable
fun SavedScreen(savedTestViewModel: SavedTestViewModel, paddingValues: PaddingValues) {
    val savedTest by savedTestViewModel.allTests.collectAsState(initial = emptyList())

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if (savedTest.isEmpty()) {
            item {
                Text("No Tests Saved")
            }
        } else {
            items(savedTest.size) { index ->
                val test = savedTest[index]
                var isSavedTestDialog by remember { mutableStateOf(false) } // Move inside items

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { isSavedTestDialog = true }
                ) {
                    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        Text(modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = test.course,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Paper Name: " + test.paperName,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Difficulty: " + test.difficulty
                        )
                    }
                }

                if (isSavedTestDialog) {
                    SavedTestDialog(
                        onDismiss = { isSavedTestDialog = false },
                        test.course,
                        test.paperName,
                        test.questions,
                        test.difficulty,
                        test.response
                    )
                }
            }
        }
    }
}