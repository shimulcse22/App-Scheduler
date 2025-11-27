package com.example.appscheduler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appscheduler.databinding.ItemScheduleHistoryBinding
import com.example.appscheduler.datasource.database.AppScheduleTable
import com.example.appscheduler.utility.DiffUtilCallback
import com.example.appscheduler.utility.compatColor
import com.example.appscheduler.utility.parseColor

class ScheduleHistoryAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val scheduleHistoryList = ArrayList<AppScheduleTable>()

    fun setItems(data: List<AppScheduleTable>) {
        val oldData = ArrayList(scheduleHistoryList)
        scheduleHistoryList.clear()
        scheduleHistoryList.addAll(data)
        val diffUtilCallback = DiffUtilCallback(
            oldData = oldData,
            newData = data,
            areItemsTheSame = { oldItem, newItem ->
                return@DiffUtilCallback oldItem.id == newItem.id
            },
            areContentsTheSame = { oldItem, newItem ->
                return@DiffUtilCallback oldItem == newItem
            }
        )
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemScheduleHistoryBinding.inflate(inflater, parent, false)
        return ScheduleHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as ScheduleHistoryViewHolder).bind(scheduleHistoryList.getOrNull(position))
    }

    override fun getItemCount(): Int = scheduleHistoryList.size

    inner class ScheduleHistoryViewHolder(
        private val binding: ItemScheduleHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: AppScheduleTable?) {
            if (bindingAdapterPosition % 2 == 0) {
                binding.root.setBackgroundColor(itemView.context.compatColor(R.color.white))
            } else {
                binding.root.setBackgroundColor(itemView.context.parseColor("#FBFBFB"))
            }
            binding.tvDate.text = data?.date
            binding.tvTime.text = itemView.context.getString(R.string.date_and_time, data?.hour,data?.min)
            binding.tvAppName.text = data?.appName
            binding.tvInfo.text = data?.executionInformation
        }

    }

}