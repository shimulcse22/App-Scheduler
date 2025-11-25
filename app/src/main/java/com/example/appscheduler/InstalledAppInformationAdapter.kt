package com.example.appscheduler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appscheduler.databinding.SingleItemInstalledAppBinding
import com.example.appscheduler.datasource.model.InstalledAppInformation
import com.example.appscheduler.listeners.OnIItemClickListener
import com.example.appscheduler.utility.DiffUtilCallback

class InstalledAppInformationAdapter(val onItemClickListener: OnIItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val installAppInformationList = ArrayList<InstalledAppInformation>()

    fun setData(installedApp: List<InstalledAppInformation>) {
        val oldData = ArrayList(installedApp)
        installAppInformationList.clear()
        installAppInformationList.addAll(installedApp)
        val diffUtilCallback = DiffUtilCallback(
            oldData = oldData,
            newData = installedApp,
            areItemsTheSame = { oldItem, newItem ->
                return@DiffUtilCallback oldItem == newItem
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
        return SingleItemInstalledAppInformationViewHolder.create(parent,onItemClickListener)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as? SingleItemInstalledAppInformationViewHolder)?.bind(installAppInformationList[position])
    }

    override fun getItemCount(): Int = installAppInformationList.size

    class SingleItemInstalledAppInformationViewHolder(
        private val binding: SingleItemInstalledAppBinding,
        private val onIItemClickListener: OnIItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup, onIItemClickListener: OnIItemClickListener): SingleItemInstalledAppInformationViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = SingleItemInstalledAppBinding.inflate(inflater, parent, false)
                return SingleItemInstalledAppInformationViewHolder(binding, onIItemClickListener)
            }
        }

        init {
            itemView.setOnClickListener {
                val installedInformation = itemView.tag as? InstalledAppInformation
                installedInformation?.let {
                    onIItemClickListener.onItemClick(installedInformation)
                } ?: run {
                    Toast.makeText(itemView.context, "No app found", Toast.LENGTH_LONG).show()
                }
            }
        }

        fun bind(information : InstalledAppInformation?){
            itemView.tag = information
            binding.apply {
                appIcon.setImageDrawable(information?.icon)
                appName.text = information?.appName
            }
        }
    }
}

