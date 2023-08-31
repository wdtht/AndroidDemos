package com.example.cardviewlibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingActivity<T: ViewBinding> : AppCompatActivity() {
    protected val binding by lazy {
        getViewBinding()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }
    protected abstract fun initView()

    protected abstract fun getViewBinding(): T
}