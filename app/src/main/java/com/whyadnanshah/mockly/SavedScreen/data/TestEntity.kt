package com.whyadnanshah.mockly.SavedScreen.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tests")
data class TestEntity (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val course : String,
    val paperName: String,
    val questions : String,
    val difficulty : String,
    val response : String
)