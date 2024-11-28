package com.padangmurah.wise.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.padangmurah.wise.data.source.local.entity.history.HistoryEntity
import com.padangmurah.wise.databinding.ItemHistoryBinding

class HistoryAdapter :
    ListAdapter<HistoryEntity, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val anime = getItem(position)
        holder.bind(anime)
    }


    class MyViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(history: HistoryEntity) {
            binding.tvTitle.text = history.title
            binding.tvSubtitle.text = history.injury
            binding.tvDate.text = history.date
            binding.tvHospital.text = history.hospital
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<HistoryEntity> =
            object : DiffUtil.ItemCallback<HistoryEntity>() {
                override fun areItemsTheSame(
                    oldItem: HistoryEntity,
                    newItem: HistoryEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: HistoryEntity,
                    newItem: HistoryEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}