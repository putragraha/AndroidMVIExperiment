package com.nsystem.androidmviexperiment.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nsystem.androidmviexperiment.R


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version ItemAdapter, v 0.0.1 11/10/20 15.17 by Putra Nugraha
 */
class ItemAdapter(val onClick: (String) -> Unit) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private val items = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false) as TextView
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun replaceWith(newItems: List<String>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: TextView) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener { onClick(view.text.toString()) }
        }

        fun bind(item: String) {
            view.text = item
        }
    }
}