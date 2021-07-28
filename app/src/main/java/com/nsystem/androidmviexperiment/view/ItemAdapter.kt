package com.nsystem.androidmviexperiment.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nsystem.androidmviexperiment.databinding.ItemChampionNameBinding


/**
 * @author Putra Nugraha (putra.nugraha@dana.id)
 * @version ItemAdapter, v 0.0.1 11/10/20 15.17 by Putra Nugraha
 */
class ItemAdapter(
    val onClick: (String) -> Unit
): ListAdapter<String, ItemAdapter.ViewHolder>(ChampionNameDiffCallback()) {

    private val items = arrayListOf<String>()

    private lateinit var binding: ItemChampionNameBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemChampionNameBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun replaceWith(newItems: List<String>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    private class ChampionNameDiffCallback : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }

    inner class ViewHolder(
        private val binding: ItemChampionNameBinding
     ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tvChampionName.setOnClickListener {
                onClick(binding.tvChampionName.text.toString())
            }
        }

        fun bind(championNames: String) {
            binding.run {
                name = championNames
                executePendingBindings()
            }
        }
    }
}