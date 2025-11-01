package com.whyadnanshah.mockly.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.whyadnanshah.mockly.SavedScreen.TestEntity
import com.whyadnanshah.mockly.SavedScreen.TestsDatabase
import kotlinx.coroutines.launch

class SavedTestViewModel(application: Application) : AndroidViewModel(application) {
    val db = Room.databaseBuilder(
        application.applicationContext,
        TestsDatabase::class.java,
        "database-name"
    ).fallbackToDestructiveMigration(false).build()

    var allTests = db.testsDao().getAllTests()

    fun addTest(testEntity: TestEntity){
        viewModelScope.launch {
            db.testsDao().insert(testEntity)
        }
    }

    fun deleteTest(testEntity: TestEntity){
        viewModelScope.launch {
            db.testsDao().delete(testEntity)
        }
    }
}

class SavedTestViewModelFactory (private val application: Application) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SavedTestViewModel(application) as T
    }
}