package com.padangmurah.wise.ui.hospital

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.padangmurah.wise.R
import com.padangmurah.wise.data.source.local.entity.hospital.HospitalEntity
import com.padangmurah.wise.databinding.ItemHospitalBinding

class HospitalAdapter :
    ListAdapter<HospitalEntity, HospitalAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemHospitalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hospital = getItem(position)
        holder.bind(hospital)
    }


    class MyViewHolder(private val binding: ItemHospitalBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        @SuppressLint("SetTextI18n")
        fun bind(hospital: HospitalEntity) {
            binding.tvHospitalName.text = hospital.name
            binding.tvHospitalDistance.text = "${hospital.distance.toString()} KM"
            binding.tvStatus.text = if((hospital.distance ?: 0.0) <= 5.0) "Nearby" else "Far"
        }

    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<HospitalEntity> =
            object : DiffUtil.ItemCallback<HospitalEntity>() {
                override fun areItemsTheSame(
                    oldItem: HospitalEntity,
                    newItem: HospitalEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: HospitalEntity,
                    newItem: HospitalEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}