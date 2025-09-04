package com.whyadnanshah.mockly.navigation

import com.whyadnanshah.mockly.R

sealed class BottomNavItems (
    val route: String,
    val icon : Int,
    val label : String
    ){
    object Home : BottomNavItems("Home", R.drawable.ai_icon, "Home")
    object Saved : BottomNavItems("Saved", R.drawable.saved_icon, "Saved")
    object PYQs : BottomNavItems("PYQs", R.drawable.pyq_icon, "PYQs")
}