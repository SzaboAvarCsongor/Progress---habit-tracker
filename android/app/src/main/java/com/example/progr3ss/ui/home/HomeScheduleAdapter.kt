package com.example.progr3ss.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.progr3ss.R
import com.example.progr3ss.databinding.ItemHomeScheduleBinding
import com.example.progr3ss.model.ScheduleResponse
import java.time.format.DateTimeFormatter

class HomeScheduleAdapter :
    ListAdapter<ScheduleResponse, HomeScheduleAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeScheduleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemHomeScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

        fun bind(item: ScheduleResponse) {
            binding.tvTitle.text = item.habit?.name ?: "Unknown Habit"

            // Idő
            val start = item.startTime?.format(timeFormat)
            val end = item.endTime?.format(timeFormat)
            binding.tvTime.text = when {
                start != null && end != null -> "$start - $end"
                start != null -> start
                else -> ""
            }

            // Notes
            if (item.notes.isNullOrBlank()) {
                binding.tvNotes.visibility = View.GONE
            } else {
                binding.tvNotes.text = item.notes
                binding.tvNotes.visibility = View.VISIBLE
            }

            // Ikon kategória alapján (egyelőre mind ugyanaz, később cserélhető)
            binding.ivIcon.setImageResource(iconFor(item.habit?.category?.id))
        }

        private fun iconFor(categoryId: Long?): Int = when (categoryId) {
            // 1L -> R.drawable.ic_gym, 6L -> R.drawable.ic_run, ...
            else -> R.drawable.ic_habit
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ScheduleResponse>() {
        override fun areItemsTheSame(old: ScheduleResponse, new: ScheduleResponse) =
            old.id == new.id

        override fun areContentsTheSame(old: ScheduleResponse, new: ScheduleResponse) =
            old == new
    }
}