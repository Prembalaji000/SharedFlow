package com.example.sharedflowcompose

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RepositoryData @Inject constructor() {
    private val _Sharedflow = MutableSharedFlow<String>()
    val sharedFlow = _Sharedflow.asSharedFlow()

    suspend fun updateData(newData: String) {
        _Sharedflow.emit(newData)
    }

    fun fetchdata() = flow {
        while (true){
            emit("Current time : ${getCurrentTime()}")
            kotlinx.coroutines.delay(1000)
        }
    }
    private fun getCurrentTime(): String{
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}