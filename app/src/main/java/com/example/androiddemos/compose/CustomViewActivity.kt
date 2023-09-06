package com.example.androiddemos.compose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.androiddemos.compose.ui.theme.AndroidDemosTheme
import com.google.android.material.color.MaterialColors
import java.time.format.TextStyle

class CustomViewActivity : ComponentActivity() {

    private val viewModel = CustomViewViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidDemosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val progress by viewModel.progress.collectAsState(0f)
                    CustomViewScreen(progress) { viewModel.reset() }
                }
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CustomViewActivity::class.java)
            startActivity(context, intent, null)
        }
    }
}

@Composable
fun CustomViewScreen(progress: Float, onResetClick: () -> Unit = {}) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(20.dp))
        Box(
            Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight()) {
            LinearProgressIndicator(
                progress = progress,
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .fillMaxWidth()
                    .height(20.dp)
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onResetClick,
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(text = "Rest")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(contentAlignment = Alignment.Center,
            modifier=Modifier
                .wrapContentSize()
        ) {
            CircularProgressIndicator(
                progress = progress,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .width(60.dp)
                    .aspectRatio(1f)
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        CustomViewScreen(0.5f)
    }
}