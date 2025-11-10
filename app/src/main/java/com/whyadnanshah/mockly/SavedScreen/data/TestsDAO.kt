package com.whyadnanshah.mockly.SavedScreen.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TestsDAO {
    @Query("SELECT * FROM tests")
    fun getAllTests(): Flow<List<TestEntity>>

    @Insert
    suspend fun insert(tests: TestEntity)

    @Delete
    suspend fun delete(tests: TestEntity)
}