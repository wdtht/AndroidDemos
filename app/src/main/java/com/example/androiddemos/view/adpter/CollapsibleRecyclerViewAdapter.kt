package com.example.androiddemos.view.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddemos.R
import com.example.androiddemos.view.CollapsibleRecyclerView
import kotlin.properties.Delegates

abstract class CollapsibleRecyclerViewAdapter<EIVH: RecyclerView.ViewHolder, SIVH: RecyclerView.ViewHolder, ItemData: com.example.androiddemos.view.adpter.ItemData>: RecyclerView.Adapter<CollapsibleRecyclerViewAdapter.Companion.ExpandableItemViewHolder<EIVH>>() {

    abstract var itemDataList: List<ItemData>
    abstract fun onCreateSubItemViewHolder(itemView: View): SIVH

    abstract fun onBindSubItemViewHolder(holder: SIVH, expandableItemPosition: Int , subItemPosition: Int)

    abstract fun onCreateExpandableItemViewHolder(itemView: View): EIVH

    abstract fun onBindExpandableItemViewHolder(holder: EIVH, position: Int)

    abstract fun getExpandableItemCount(): Int

    abstract fun getSubItemCount(position: Int): Int

    private var expandableItemId by Delegates.notNull<Int>()
    var subItemId by Delegates.notNull<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandableItemViewHolder<EIVH> {
        val collapsibleRecyclerView = parent as CollapsibleRecyclerView
        expandableItemId = collapsibleRecyclerView.expandableItemId
        subItemId = collapsibleRecyclerView.subItemId
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.collapsible_recycler_view_item, parent, false)
        val expandableItemHeader = LayoutInflater.from(parent.context).inflate(expandableItemId, itemView as ViewGroup, false)
        itemView.addView(expandableItemHeader, 0)
        val headerViewHolder = onCreateExpandableItemViewHolder(expandableItemHeader)
        return ExpandableItemViewHolder(itemView, headerViewHolder)
    }

    override fun getItemCount(): Int {
        return getExpandableItemCount()
    }

    override fun onBindViewHolder(holder: ExpandableItemViewHolder<EIVH>, position: Int) {
        when (itemDataList[position].isExpanded) {
            true -> holder.subItemsRecyclerView.visibility = View.VISIBLE
            false -> holder.subItemsRecyclerView.visibility = View.GONE
        }
        holder.subItemsRecyclerView.adapter = SubItemsAdapter(this as CollapsibleRecyclerViewAdapter<EIVH, SIVH, com.example.androiddemos.view.adpter.ItemData>, position)
        onBindExpandableItemViewHolder(holder.headerViewHolder, position)
    }

    companion object {
        class ExpandableItemViewHolder<EIVH: RecyclerView.ViewHolder>(itemView: ViewGroup, public val headerViewHolder: EIVH) : RecyclerView.ViewHolder(itemView) {
            val header = itemView.getChildAt(0)
            val subItemsRecyclerView = itemView.getChildAt(1) as RecyclerView
        }
    }
}

class SubItemsAdapter<EIVH:RecyclerView.ViewHolder, SIVH: RecyclerView.ViewHolder>(private val collapsibleRecyclerViewAdapter: CollapsibleRecyclerViewAdapter<EIVH,SIVH,ItemData>, private val positionInCollapsibleRecyclerView: Int): RecyclerView.Adapter<SIVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SIVH {
        val subItemId = collapsibleRecyclerViewAdapter.subItemId
        val subItemView = LayoutInflater.from(parent.context).inflate(subItemId, parent, false)
        return collapsibleRecyclerViewAdapter.onCreateSubItemViewHolder(subItemView)
    }

    override fun onBindViewHolder(holder: SIVH, position: Int) {
        collapsibleRecyclerViewAdapter.onBindSubItemViewHolder(holder,positionInCollapsibleRecyclerView, position)
    }

    override fun getItemCount(): Int {
        return collapsibleRecyclerViewAdapter.getSubItemCount(positionInCollapsibleRecyclerView)
    }
}

abstract class ItemData{
    abstract var isExpanded: Boolean
    abstract val subItems: List<Any>
}
