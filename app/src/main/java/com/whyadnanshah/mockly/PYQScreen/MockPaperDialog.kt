package com.whyadnanshah.mockly.PYQScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.whyadnanshah.mockly.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MockPaperDialog(onDismiss: () -> Unit, year: String, branch: String) {
    val context = LocalContext.current
    var url by remember { mutableStateOf("") }
    var showWebView by remember { mutableStateOf(false) }

    val cseFirst = listOf("Applied Physics I","Applied Physics II", "Mathematics I","Mathematics II", "Basic Electrical Engineering", "Engineering Graphics and design", "Workshop", "Basic Engineering Mechanics", "Indian Traditional Knowledge", "Programming for Problem Solving", "Basic Mechanical Engineering")
    val cseSecond = listOf("Chemistry", "Analog Electronic Circuits", "Digital Electronics", "IT Workshop", "Mathematics III", "Discrete Mathematics", "Computer Organisation and Architecture", "Operating System", "DAA")
    val cseThird = listOf("Signals and Systems", "DBMS", "Formal Language and Automata Theory", "Object Oriented Programming", "Compiler Design", "Computer Networks")
    val cseFourth = listOf("Biology", "Advanced DBMS", "IPR and cyber laws")

    val eceFirst = listOf("Applied Physics I","Applied Physics II", "Mathematics", "BEE", "Engineering Graphics", "Programming", "Workshop")
    val eceSecond = listOf("Signals & Systems", "Electromagnetic Theory", "Digital Electronics", "Analog Circuits")
    val eceThird = listOf("Communication Systems", "Microprocessors", "VLSI Design", "Control Systems")
    val eceFourth = listOf("Wireless Communication", "Embedded Systems", "Project Work")

    val aiFirst = listOf("Mathematics", "Programming", "Physics", "Basic Electronics")
    val aiSecond = listOf("Data Structures", "Algorithms", "Probability & Statistics", "Linear Algebra")
    val aiThird = listOf("Machine Learning", "Neural Networks", "Computer Vision", "NLP")
    val aiFourth = listOf("Deep Learning", "AI Ethics", "Project Work")

    val bcaFirst = listOf("Programming Fundamentals", "Mathematics", "Digital Logic", "Communication Skills")
    val bcaSecond = listOf("Data Structures", "Database Systems", "Web Development", "Operating Systems")
    val bcaThird = listOf("Software Engineering", "Computer Networks", "Mobile App Development", "Project Work")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .padding(top = 72.dp)
    ){
        Surface (modifier = Modifier
            .fillMaxSize()
        ){
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ){
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    Text("$branch $year", fontSize = 24.sp)
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = Color.Black),
                    thickness = DividerDefaults.Thickness, color = DividerDefaults.color
                )

                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn {
                    items(
                        count = when {
                            branch == "B.Tech CSE" && year == "1st Year" -> cseFirst.size
                            branch == "B.Tech CSE" && year == "2nd Year" -> cseSecond.size
                            branch == "B.Tech CSE" && year == "3rd Year" -> cseThird.size
                            branch == "B.Tech CSE" && year == "4th Year" -> cseFourth.size
                            branch == "B.Tech ECE" && year == "1st Year" -> eceFirst.size
                            branch == "B.Tech ECE" && year == "2nd Year" -> eceSecond.size
                            branch == "B.Tech ECE" && year == "3rd Year" -> eceThird.size
                            branch == "B.Tech ECE" && year == "4th Year" -> eceFourth.size
                            branch == "B.Tech AI" && year == "1st Year" -> aiFirst.size
                            branch == "B.Tech AI" && year == "2nd Year" -> aiSecond.size
                            branch == "B.Tech AI" && year == "3rd Year" -> aiThird.size
                            branch == "B.Tech AI" && year == "4th Year" -> aiFourth.size
                            branch == "BCA" && year == "1st Year" -> bcaFirst.size
                            branch == "BCA" && year == "2nd Year" -> bcaSecond.size
                            branch == "BCA" && year == "3rd Year" -> bcaThird.size
                            else -> 0
                        }
                    ) { index ->
                        val subject = when {
                            branch == "B.Tech CSE" && year == "1st Year" -> cseFirst[index]
                            branch == "B.Tech CSE" && year == "2nd Year" -> cseSecond[index]
                            branch == "B.Tech CSE" && year == "3rd Year" -> cseThird[index]
                            branch == "B.Tech CSE" && year == "4th Year" -> cseFourth[index]
                            branch == "B.Tech ECE" && year == "1st Year" -> eceFirst[index]
                            branch == "B.Tech ECE" && year == "2nd Year" -> eceSecond[index]
                            branch == "B.Tech ECE" && year == "3rd Year" -> eceThird[index]
                            branch == "B.Tech ECE" && year == "4th Year" -> eceFourth[index]
                            branch == "B.Tech AI" && year == "1st Year" -> aiFirst[index]
                            branch == "B.Tech AI" && year == "2nd Year" -> aiSecond[index]
                            branch == "B.Tech AI" && year == "3rd Year" -> aiThird[index]
                            branch == "B.Tech AI" && year == "4th Year" -> aiFourth[index]
                            branch == "BCA" && year == "1st Year" -> bcaFirst[index]
                            branch == "BCA" && year == "2nd Year" -> bcaSecond[index]
                            branch == "BCA" && year == "3rd Year" -> bcaThird[index]
                            else -> "Coming Soon"
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clickable(
                                    onClick = {
                                        showWebView = true
                                        Toast.makeText(
                                            context,
                                            "$subject Selected",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        when (subject) {
                                            "Applied Physics I" -> url =
                                                "https://drive.google.com/file/d/1Lp-UVXNCHWLfC2iUfdJETC8WgNQ5S0qS/view?usp=drive_link"

                                            "Applied Physics II" -> url =
                                                "https://drive.google.com/file/d/1MBYSRAnizxDfo3AcRIl7QGUVLWUVw_vc/view?usp=drive_link"

                                            "Mathematics I" -> url =
                                                "https://drive.google.com/file/d/1TkiWtbr5CQmNJJnemgiRY6t6STjelcEO/view?usp=drive_link"

                                            "Mathematics II" -> url =
                                                "https://drive.google.com/file/d/1ywqGWlmuG6Ff1MWXhf9bxLUAWnuNf2v5/view?usp=drive_link"

                                            "Basic Electrical Engineering" -> url =
                                                "https://drive.google.com/file/d/1XIGotbiNuj9Z6iokpOoJZZiG7NnE2uwe/view?usp=drive_link"

                                            "Engineering Graphics and design" -> url =
                                                "https://drive.google.com/file/d/1qAPVDPsTt98oogX8MMumGLI0sDIez9l7/view?usp=drive_link"

                                            "Programming for Problem Solving" -> url =
                                                "https://drive.google.com/file/d/1QT_bhvTbXuwgu5J_FJMj-ahDsPkMkvfr/view?usp=drive_link"

                                            "Workshop" -> url =
                                                "https://drive.google.com/file/d/1VmJ2_AdzEebOJXSMR0ij82575JBhECYL/view?usp=drive_link"

                                            "Indian Traditional Knowledge" -> url =
                                                "https://drive.google.com/file/d/1dRP4a0eBKbr9C1gerNvtESaTgfU-tmPD/view?usp=drive_link"

                                            "Basic Mechanical Engineering" -> url = "https://drive.google.com/file/d/1pL6GbN12Z_S_Gric7g8lqQiyP9tzIXsK/view?usp=drive_link"
                                            else -> url = "https://www.google.com"
                                        }
                                    }
                                ),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.open_book),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp)
                                )
                                Spacer(Modifier.width(16.dp))
                                Text(text = subject, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                    }
                }
            }
        }
    }
    if (showWebView) {
        Dialog(
            onDismissRequest = { showWebView = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                WebViewComposable(
                    url = url,
                    onBackPressed = { showWebView = false }
                )
            }
        }
    }
}