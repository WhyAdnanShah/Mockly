package com.whyadnanshah.mockly.SavedScreen.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TestEntity::class], version = 1)
abstract class TestsDatabase : RoomDatabase() {
    abstract fun testsDao(): TestsDAO
}