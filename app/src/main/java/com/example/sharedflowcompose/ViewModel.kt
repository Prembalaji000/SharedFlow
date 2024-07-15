package com.example.sharedflowcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository : RepositoryData
): ViewModel() {
        private val _data = MutableStateFlow("Loading...")
        val data: StateFlow<String> = _data

        init {
            viewModelScope.launch {
                repository.fetchdata().collect {
                    _data.value = it
                }
            }
        }
        val sharedFlow = repository.sharedFlow

        fun updateSharedFlow(newData: String) {
            viewModelScope.launch {
                repository.updateData(newData)
            }
        }
}