package com.example.cardviewlibrary

import android.util.Log
import android.view.View
import com.example.cardviewlibrary.databinding.ActivityCardMainBinding
import com.didi.drouter.annotation.Router
import com.didi.drouter.api.DRouter
const val TAG = "superdemo/CardMainActivity"

@Router(path = "/super/cardView/activity/cardView")
class CardMainActivity : BaseViewBindingActivity<ActivityCardMainBinding>(), View.OnClickListener {

    override fun getViewBinding(): ActivityCardMainBinding {
        return ActivityCardMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        Log.d(TAG, "initView !")
    }


    override fun onClick(v: View?) {
        Log.d(TAG, "onClick !")
    }
}