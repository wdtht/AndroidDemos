package com.example.androiddemos

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.example.androiddemos.fragmenttest.FragmentTestActivity
import com.example.androiddemos.ui.theme.AndroidDemosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidDemosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DemoList()
                }
            }
        }
    }
}


@OptIn(ExperimentalUnitApi::class)
@Preview(showBackground = true)
@Composable
fun DemoList() {
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        item {
            Button(onClick = {
                context.startActivity(Intent(context, FragmentTestActivity::class.java))
            }, modifier = Modifier.wrapContentSize()) {
                Text(text = "fragment", fontSize = TextUnit(11F, TextUnitType.Sp))
            }
        }
    }
}