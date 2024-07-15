package com.example.sharedflowcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sharedflowcompose.ui.theme.SharedFlowComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharedFlowComposeTheme {
                MainScreen()
            }
        }
    }
}
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()){
    val data by viewModel.data.collectAsState()
    var sharedFlowData by remember { mutableStateOf("") }
    
    LaunchedEffect(viewModel.sharedFlow) {
        viewModel.sharedFlow.collect{values ->
        sharedFlowData = values
        }
    }
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text = data)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = sharedFlowData)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.updateSharedFlow("New data at ${getcurrentTime()}") })
            {
                Text(text = "Update shared data")
            }
        }
    }
}
private fun getcurrentTime() : String{
    val sdf1 = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf1.format(Date())
}

