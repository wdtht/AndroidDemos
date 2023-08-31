package com.example.cardviewlibrary

import android.os.Bundle
import android.view.View
import com.example.cardviewlibrary.databinding.ActivityCardMainBinding
import com.didi.drouter.annotation.Router
import com.didi.drouter.api.DRouter

const val TAG = "superdemo/CardMainActivity"

@Router(path = "/super/cardView/activity/cardView")
class CardMainActivity : BaseViewBindingActivity<ActivityCardMainBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_main)
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun getViewBinding(): ActivityCardMainBinding {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}