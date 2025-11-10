package com.whyadnanshah.mockly.SavedScreen.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TestEntity::class], version = 1, exportSchema = false)
abstract class TestsDatabase : RoomDatabase() {
    abstract fun testsDao(): TestsDAO
}