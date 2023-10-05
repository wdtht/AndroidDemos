package com.example.androiddemos.network.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddemos.R
import com.example.androiddemos.view.adpter.CollapsibleRecyclerViewAdapter

class MyCollapsibleRecyclerViewAdapter: CollapsibleRecyclerViewAdapter<MyCollapsibleRecyclerViewAdapter.Companion.ExpandableItemHeaderViewHolder, MyCollapsibleRecyclerViewAdapter.Companion.SubItemViewHolder, ItemData>() {
    override var itemDataList: List<ItemData> = emptyList()

    companion object {
        class ExpandableItemHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val headerText = itemView.findViewById<TextView>(R.id.headerText)
            val toggleButton = itemView.findViewById<ImageView>(R.id.toggleButton)
            val headerTitle = itemView.findViewById<TextView>(R.id.headerTitle)
        }

        class SubItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemTitle = itemView.findViewById<TextView>(R.id.itemTitle)
            val itemTypeIcon = itemView.findViewById<ImageView>(R.id.typeIcon)
            val itemActionText = itemView.findViewById<TextView>(R.id.itemActionText)
        }
    }

    override fun onCreateSubItemViewHolder(itemView: View): SubItemViewHolder {
        return SubItemViewHolder(itemView)
    }

    override fun onCreateExpandableItemViewHolder(itemView: View): ExpandableItemHeaderViewHolder {
        return ExpandableItemHeaderViewHolder(itemView)
    }

    override fun getExpandableItemCount(): Int {
        return itemDataList.size
    }

    override fun getSubItemCount(position: Int): Int {
        return itemDataList[position].subItems.size
    }

    override fun onBindExpandableItemViewHolder(
        holder: ExpandableItemHeaderViewHolder,
        position: Int
    ) {
        val itemData = itemDataList[position]
        holder.headerTitle.text = itemData.title
        holder.toggleButton.setImageResource(if (itemData.isExpanded) R.drawable.arrow_up else R.drawable.arrow_down)
        holder.toggleButton.setOnClickListener {
            itemData.isExpanded = !itemData.isExpanded
            notifyItemChanged(position)
        }
        holder.headerText.text = itemData.expandText
    }

    override fun onBindSubItemViewHolder(
        holder: SubItemViewHolder,
        expandableItemPosition: Int,
        subItemPosition: Int
    ) {
        val subItemData = itemDataList[expandableItemPosition].subItems[subItemPosition]
        holder.itemTitle.text = subItemData.title
        holder.itemTypeIcon.setImageResource(
            when (subItemData.type) {
                SubItemType.Upgrade -> R.drawable.arrow_up
                SubItemType.Choose -> R.drawable.arrow_down
            }
        )
        holder.itemActionText.text = subItemData.type.type
    }
}

data class ItemData(val title: String, val expandText: String, override val subItems: List<SubItemData>, override var isExpanded: Boolean = true): com.example.androiddemos.view.adpter.ItemData()
data class SubItemData(val title: String, val type: SubItemType)

enum class SubItemType(val type: String) {
    Upgrade("Upgrade"),
    Choose("Choose")
}