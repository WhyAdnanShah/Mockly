package com.whyadnanshah.mockly.destinations

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.whyadnanshah.mockly.R
@Composable
fun HomeScreen(paddingValues: PaddingValues) {
    val context = LocalContext.current
    var course by rememberSaveable { mutableStateOf("") }
    var subject by rememberSaveable { mutableStateOf("") }
    var topics by rememberSaveable { mutableStateOf("") }
    var difficulty by rememberSaveable { mutableStateOf("") }
    var questions by rememberSaveable { mutableStateOf("") }
    var format by rememberSaveable { mutableStateOf("") }
    var info by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.shapes_animations))
        LottieAnimation(composition, modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally))

        InputField(
            value = course,
            onValueChange = { course = it },
            label = "Select Course",
            placeholder = "Select Course"
        )

        Spacer(Modifier.height(25.dp))

        InputField(
            value = subject,
            onValueChange = { subject = it },
            label = "Select Subject",
            placeholder = "Select Subject"
        )

        Spacer(Modifier.height(25.dp))

        InputField(
            value = topics,
            onValueChange = { topics = it },
            label = "Select Topics",
            placeholder = "Select Topics"
        )

        Spacer(Modifier.height(25.dp))

        DropdownMenu(
            selectedValue = difficulty,
            onValueChange = { difficulty = it },
            label = "Select Difficulty Level",
            options = listOf("Easy", "Medium", "Hard")
        )

        Spacer(Modifier.height(25.dp))

        InputField(
            value = questions,
            onValueChange = { questions = it },
            label = "No. Of Questions",
            placeholder = "Number Of Questions",
            keyboardType = KeyboardType.Number
        )

        Spacer(Modifier.height(25.dp))

        InputField(
            value = format,
            onValueChange = { format = it },
            label = "Format of the Mock paper",
            placeholder = "Eg:- Section A (4 marks each) \n and Section B (10 marks each )"
        )

        Spacer(Modifier.height(25.dp))

        InputField(
            value = info,
            onValueChange = { info = it },
            label = "*Additional Info",
            placeholder = ""
        )

        Spacer(Modifier.height(25.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(16.dp),
            onClick = {
                Toast.makeText(context, "Mock Paper Generated", Toast.LENGTH_SHORT).show()
            },
            shape = RoundedCornerShape(10.dp),
            enabled = if (course == ""|| subject == ""|| topics == ""|| difficulty == ""|| questions == ""|| format == "") false else true
        )
        {
            Text(text = "Generate Mock Paper")
        }
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        shape = RoundedCornerShape(15.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    selectedValue: String,
    onValueChange: (String) -> Unit,
    label: String,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .menuAnchor(),
            value = selectedValue,
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            readOnly = true,
            shape = RoundedCornerShape(15.dp),
            label = { Text(label) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}