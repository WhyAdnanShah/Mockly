package com.whyadnanshah.mockly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.whyadnanshah.mockly.navigation.AppNavigation
import com.whyadnanshah.mockly.ui.theme.MocklyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MocklyTheme {
//                var showSplash by rememberSaveable { mutableStateOf(true) }
//                if (showSplash) {
//                    SplashScreen(
//                        onSplashComplete = { showSplash = false }
//                    )
//                } else {
                    AppNavigation()
//                }
            }
        }
    }
}

//@Composable
//fun SplashScreen(
//    onSplashComplete: () -> Unit
//) {
//    var animationPlayed by remember { mutableStateOf(false) }
//
//    val alpha by animateFloatAsState(
//        targetValue = if (animationPlayed) 1f else 0f,
//        animationSpec = tween(1),
//        label = "splash_alpha"
//    )
//
//    val scale by animateFloatAsState(
//        targetValue = if (animationPlayed) 1f else 0.8f,
//        animationSpec = spring(dampingRatio = 0.6f, stiffness = 200f),
//        label = "splash_scale"
//    )
//
//    LaunchedEffect(Unit) {
//        animationPlayed = true
//        delay(0)
//        onSplashComplete()
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.primaryContainer),
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .alpha(alpha)
//                .scale(scale)
//        ) {
//            Icon(
//                imageVector = Icons.Filled.Create,
//                contentDescription = "App Logo",
//                tint = MaterialTheme.colorScheme.primary,
//                modifier = Modifier.size(120.dp)
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Text(
//                text = "Mockly",
//                style = MaterialTheme.typography.displayMedium,
//                color = MaterialTheme.colorScheme.onPrimaryContainer,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = "Generate Test papers with ease",
//                style = MaterialTheme.typography.bodyLarge,
//                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
//            )
//        }
//
//        CircularProgressIndicator(
//            color = MaterialTheme.colorScheme.primary,
//            strokeWidth = 2.dp,
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(bottom = 48.dp)
//                .size(24.dp)
//        )
//    }
//}
