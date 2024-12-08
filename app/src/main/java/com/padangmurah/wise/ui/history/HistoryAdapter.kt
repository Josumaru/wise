package com.padangmurah.wise.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padangmurah.wise.data.source.local.entity.history.HistoryEntity
import com.padangmurah.wise.databinding.ItemHistoryBinding
import com.padangmurah.wise.util.DateFormatter

class HistoryAdapter :
    ListAdapter<HistoryEntity, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)
    }


    class MyViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(history: HistoryEntity) {
            val readableDate = DateFormatter.formatReadableDate(history.createdAt)
            binding.tvTitle.text = history.id
            binding.tvDate.text = readableDate
            binding.tvHospital.text = history.treatment.trimIndent()

            Glide.with(itemView.context).apply {
                load(history.photo).into(binding.ivImage)
            }
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