package com.example.androiddemos.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.getIntOrThrow
import androidx.core.content.res.getStringOrThrow
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddemos.R
import kotlin.properties.Delegates

class CollapsibleRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var expandableItemId by Delegates.notNull<Int>()
    var subItemId by Delegates.notNull<Int>()


    init {
//        if(attrs == null && ( expandableItemId == 0 || subItemId == 0 )){
//            throw(Throwable("must set expandableItemId and subItemId in xml or set in constructor"))
//        }else if (expandableItemId != 0 && subItemId != 0){
//            this.expandableItemId = expandableItemId
//            this.subItemId = subItemId
//        }else attrs.let {
//            val a = context.obtainStyledAttributes(it, R.styleable.CollapsibleRecyclerView)
//
//            val path = a.getStringOrThrow(R.styleable.CollapsibleRecyclerView_expandableItemId)
//            this.expandableItemId =
//            this.subItemId = a.getIntOrThrow(R.styleable.CollapsibleRecyclerView_subItemId)
//            a.recycle()
//        }

    }

    companion object {
        @JvmStatic
        @BindingAdapter("expandableItemId", "subItemId", requireAll = true)
        fun initRecyclerView(recyclerView: CollapsibleRecyclerView, expandableItemId: Int, subItemId: Int) {
            recyclerView.expandableItemId = expandableItemId
            recyclerView.subItemId = subItemId
        }
    }

}
