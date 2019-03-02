package com.gmail.mostafa.ma.saleh.poormanslauncher.recycler_adapters

import android.content.Context
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.facebook.drawee.view.SimpleDraweeView
import com.gmail.mostafa.ma.saleh.poormanslauncher.R
import com.gmail.mostafa.ma.saleh.poormanslauncher.models.Launchable

class LaunchablesAdapter(context: Context, private val rowCount: Int, private val columnCount: Int) :
    RecyclerView.Adapter<LaunchablesAdapter.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)!!
    private val displayMetrics = context.resources.displayMetrics
    private val cacheDir = context.cacheDir!!
    private var onLaunchableClick: ((Launchable) -> Unit)? = null
    private val launchables =
        SortedList<Launchable>(Launchable::class.java, object : SortedListAdapterCallback<Launchable>(this) {
            override fun areItemsTheSame(item1: Launchable, item2: Launchable) = item1 == item2
            override fun compare(o1: Launchable, o2: Launchable) = o1.compareTo(o2)
            override fun areContentsTheSame(oldItem: Launchable, newItem: Launchable) = oldItem hasSameContentAs newItem
        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        layoutInflater.inflate(R.layout.list_item_launchable, parent, false),
        Size(
            (displayMetrics.widthPixels.toFloat() / columnCount).toInt(),
            (displayMetrics.heightPixels.toFloat() / rowCount).toInt()
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.run {
        if (position < launchables.size()) {
            val launchable = launchables[position]
            itemView.isInvisible = false
            labelTextView.text = launchable.label
            iconDraweeView.setImageURI("file://$cacheDir/${launchable.icon}")
        } else {
            itemView.isInvisible = true
        }
    }

    override fun getItemCount() : Int {
        val pageSize = rowCount * columnCount
        return if (launchables.size() % pageSize == 0) {
            launchables.size()
        } else {
            launchables.size() + (pageSize - launchables.size() % pageSize)
        }
    }

    fun setOnLaunchableClickListener(l: ((Launchable) -> Unit)?) {
        onLaunchableClick = l
    }

    fun updateLauchables(launchables: List<Launchable>) = this.launchables.replaceAll(launchables)

    inner class ViewHolder(itemView: View, size: Size) : RecyclerView.ViewHolder(itemView) {
        val labelTextView: TextView = itemView.findViewById(R.id.labelTextView)
        val iconDraweeView: SimpleDraweeView = itemView.findViewById(R.id.iconDraweeView)

        init {
            itemView.layoutParams = itemView.layoutParams?.apply {
                height = size.height
                width = size.width
            } ?: ViewGroup.LayoutParams(size.width, size.height)
            itemView.setOnClickListener { onLaunchableClick?.invoke(launchables[adapterPosition]) }
        }
    }
}