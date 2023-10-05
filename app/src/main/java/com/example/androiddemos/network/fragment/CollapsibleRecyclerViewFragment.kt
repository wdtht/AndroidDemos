package com.example.androiddemos.network.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androiddemos.R
import com.example.androiddemos.databinding.FragmentCollapsibleRecyclerViewBinding
import com.example.androiddemos.network.adapter.ItemData
import com.example.androiddemos.network.adapter.MyCollapsibleRecyclerViewAdapter
import com.example.androiddemos.network.adapter.SubItemData
import com.example.androiddemos.network.adapter.SubItemType
import com.example.androiddemos.view.CollapsibleRecyclerView
import com.example.androiddemos.view.adpter.CollapsibleRecyclerViewAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [CollapsibleRecyclerViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CollapsibleRecyclerViewFragment : Fragment() {
    private lateinit var binding: FragmentCollapsibleRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_collapsible_recycler_view, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val collapsibleRecyclerView = binding.collapsibleRecyclerView
        val adapter = MyCollapsibleRecyclerViewAdapter()
        adapter.itemDataList = listOf(
            ItemData(
                "title1",
                "expandText1",
                listOf(
                    SubItemData(
                        "subItemTitle1",
                        SubItemType.Upgrade,
                    ),
                    SubItemData(
                        "subItemTitle2",
                        SubItemType.Upgrade,
                    ),
                    SubItemData(
                        "subItemTitle3",
                        SubItemType.Upgrade,
                    ),
                )
            ),
            ItemData(
                "title2",
                "expandText2",
                listOf(
                    SubItemData(
                        "subItemTitle1",
                        SubItemType.Upgrade,
                    ),
                    SubItemData(
                        "subItemTitle2",
                        SubItemType.Upgrade,
                    ),
                    SubItemData(
                        "subItemTitle3",
                        SubItemType.Upgrade,
                    ),
                )
            ),

            )
        collapsibleRecyclerView.adapter = adapter
    }
}

