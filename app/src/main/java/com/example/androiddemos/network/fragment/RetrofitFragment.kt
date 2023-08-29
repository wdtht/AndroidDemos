package com.example.androiddemos.network.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.androiddemos.R
import com.example.androiddemos.databinding.FragmentRetrofitBinding
import com.example.androiddemos.network.retrofit.GitHubService
import com.example.androiddemos.network.retrofit.api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RetrofitFragment : Fragment() {
    private lateinit var binding: FragmentRetrofitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_retrofit, container, false)

        binding.button.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                api<GitHubService>().listRepos("octocat")
                    .flowOn(Dispatchers.IO)
                    .map { it[0].toString() }
                    .flowOn(Dispatchers.Default)
                    .catch {
                        Log.e(
                            TAG,
                            "onCreateView: binding.button.setOnClickListener api<GitHubService>().listRepos error message",
                            it
                        )
                    }
                    .collect { binding.textView.text = it }
            }
        }
        return binding.root
    }

    companion object {
        private const val TAG = "RetrofitFragment"
    }
}