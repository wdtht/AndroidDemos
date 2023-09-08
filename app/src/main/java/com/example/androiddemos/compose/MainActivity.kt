package com.example.androiddemos.compose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.didi.drouter.api.DRouter
import com.example.androiddemos.compose.ui.theme.AndroidDemosTheme
import com.example.androiddemos.customview.activity.DragListActivity
import com.example.androiddemos.jetpack.activity.LivedataMainActivity
import com.example.androiddemos.network.activity.NetworkActivity
import com.example.androiddemos.serialport.SerialportActivity
import com.example.androiddemos.compose.chatdemo.chatActivity

class MainActivity : ComponentActivity() {
    private val itemDataList = initData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidDemosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    private fun initData(): List<ViewData> {
        val adapterData = arrayOf(
            "custom",
            "network",
            "livedata",
            "drag list",
            "serial port",
            "card-view",
            "chat",
            "返回"
        )
        val onClickListeners = arrayOf(
            { CustomViewActivity.start(this) },
            { NetworkActivity.start(this) },
            { LivedataMainActivity.start(this) },
            { DragListActivity.start(this) },
            { SerialportActivity.start(this) },
            { DRouter.build("/super/cardView/activity/cardView").start(this) },
            {chatActivity.start(this)},
            { finish() }
        )
        val viewData: MutableList<ViewData> = ArrayList()
        for (i in adapterData.indices) {
            viewData.add(ViewData(adapterData[i], onClickListeners[i]))
        }
        return viewData
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun MainScreen() {
        FlowRow {
            for (i in itemDataList.indices) {
                Button(
                    onClick = itemDataList[i].onClickListener,
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize()
                ) {
                    Text(text = itemDataList[i].text, modifier = Modifier.wrapContentSize())
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreen()
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}


data class ViewData(val text: String, val onClickListener: () -> Unit)