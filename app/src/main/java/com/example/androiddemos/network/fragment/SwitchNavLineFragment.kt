package com.example.androiddemos.network.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androiddemos.databinding.FragmentSwitchNavlineBinding
import com.example.androiddemos.network.Interface.ItemData
import com.example.androiddemos.view.NavLineRecycleViewManage


const val TAG = "AgNav5/SwitchNavLineFragment"

class SwitchNavLineFragment : Fragment() {
    private val navLineRecycleViewManage by lazy {
        NavLineRecycleViewManage(this.context, binding.navlineRecycleView)
    }

    private var _binding: FragmentSwitchNavlineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSwitchNavlineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemList = listOf(
            ItemData("标题1", "文本内容1", false),
            ItemData("标题2", "文本内容2", false),
            ItemData("标题3", "文本内容1", false),
            ItemData("标题4", "文本内容1", true),
            ItemData("标题5", "文本内容1", false),
            ItemData("标题6", "文本内容1", false),
            ItemData("标题7", "文本内容1", false),
        )
        navLineRecycleViewManage.initRecyclerView(itemList)
        navLineRecycleViewManage.initTouchMove()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
