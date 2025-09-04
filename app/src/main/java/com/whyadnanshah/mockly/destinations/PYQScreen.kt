package com.whyadnanshah.mockly.destinations

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.whyadnanshah.mockly.PYQScreen.MockPaperDialog
import com.whyadnanshah.mockly.R

@Composable
fun PYQScreen(paddingValues: PaddingValues) {
    val context = LocalContext.current
    val branches = mapOf(
        "B.Tech CSE" to listOf("1st Year", "2nd Year", "3rd Year", "4th Year"),
        "B.Tech ECE" to listOf("1st Year", "2nd Year", "3rd Year", "4th Year"),
        "B.Tech AI" to listOf("1st Year", "2nd Year", "3rd Year", "4th Year"),
        "BCA" to listOf("1st Year", "2nd Year", "3rd Year")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        branches.forEach { (branch, years) ->
            BranchSection(branch = branch, years = years, context = context)
        }
    }
}

@Composable
fun BranchSection(branch: String, years: List<String>, context: Context) {
    Text(branch, fontSize = 24.sp, modifier = Modifier.padding(16.dp))
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth().height(230.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(years) { index ->
            YearCard(branch = branch, year = index, context = context)
        }
    }
    Spacer(Modifier.height(24.dp))
}

@Composable
fun YearCard(branch: String, year: String, context: Context) {
    var isDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .size(200.dp, 100.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                isDialog = true
            },
        RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(R.drawable.scholar),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = year,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
    if (isDialog){
        MockPaperDialog(onDismiss = { isDialog = false }, year, branch)
    }
}

