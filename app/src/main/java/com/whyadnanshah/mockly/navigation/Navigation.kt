package com.whyadnanshah.mockly.navigation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.whyadnanshah.mockly.SplashScreen
import com.whyadnanshah.mockly.destinations.HomeScreen
import com.whyadnanshah.mockly.destinations.PYQScreen
import com.whyadnanshah.mockly.destinations.SavedScreen
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import com.whyadnanshah.mockly.R
import com.whyadnanshah.mockly.destinations.SettingsScreen
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItems.Home,
        BottomNavItems.Saved,
        BottomNavItems.PYQs
    )
    var isSettings by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        when (navController.currentBackStackEntryAsState().value?.destination?.route) {
                            "Home" -> {
                                Text("Create Test Paper", fontSize = 25.sp)
                                Button(
                                    onClick = { isSettings = true },
                                    modifier = Modifier.wrapContentSize()
                                ) {
                                    Image(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Settings Button",
                                    )
                                }
                            }
                            "Saved" -> {
                                Text("Saved", fontSize = 25.sp)
                                Button(
                                    onClick = { isSettings = true },
                                    modifier = Modifier.wrapContentSize()
                                ) {
                                    Image(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Settings Button",
                                    )
                                }
                            }
                            "PYQs" -> {
                                Text("PYQs", fontSize = 25.sp)
                                Button(
                                    onClick = { isSettings = true },
                                    modifier = Modifier.wrapContentSize()
                                ) {
                                    Image(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Settings Button",
                                    )
                                }
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (navController.currentBackStackEntryAsState().value?.destination?.route != "Settings")
                BottomNavigationBar(navController, items)
        }

    ){ padding ->
        NavHost(navController = navController, startDestination = "Home") {
            composable("splash") {
                SplashScreen {
                    navController.navigate("main") {
                        popUpTo("splash") {
                            inclusive = true
                        }
                    }
                }
            }
            composable ("Home"){
                HomeScreen(padding)
            }
            composable ("Saved"){
                SavedScreen()
            }
            composable ("PYQs"){
                PYQScreen(padding)
            }
        }
        if (isSettings){
            Toast.makeText(navController.context, "Settings", Toast.LENGTH_SHORT).show()
            SettingsScreen(onDismiss = { isSettings = false })
        }
    }
}



@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<BottomNavItems>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar (
        containerColor = Color.Transparent,
    ){
        items.forEach {
                item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(BottomNavItems.Home.route){
                            inclusive = false
                            saveState = true
                        }
                    }

                },
                icon = {
                    Icon(painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Unspecified
                    )
                },
                label = {
                    Text(text = item.label)
                },
            )
        }
    }
}

@Composable
fun CenteredText(text: String, fontSize: TextUnit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text, fontSize = fontSize, modifier = Modifier)
    }
}