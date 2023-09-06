package com.example.firstkmmapp.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.firstkmmapp.RealmRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repo = RealmRepo()
    val queries: LiveData<List<String>> = liveData {
        emitSource(repo.getAllData().flowOn(Dispatchers.IO).asLiveData(Dispatchers.Main))
    }

    fun saveQuery(query: String) {
        viewModelScope.launch {
            repo.saveInfo(query)
        }
    }
}
