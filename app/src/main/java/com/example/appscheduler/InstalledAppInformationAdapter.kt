package com.example.appscheduler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appscheduler.databinding.SingleItemInstalledAppBinding
import com.example.appscheduler.datasource.model.InstalledAppInformation
import com.example.appscheduler.utility.DiffUtilCallback

class InstalledAppInformationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
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
        return SingleItemInstalledAppInformationViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as? SingleItemInstalledAppInformationViewHolder)?.bind(installAppInformationList[position])
    }

    override fun getItemCount(): Int = installAppInformationList.size

    class SingleItemInstalledAppInformationViewHolder(
        private val binding: SingleItemInstalledAppBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun create(parent: ViewGroup): SingleItemInstalledAppInformationViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = SingleItemInstalledAppBinding.inflate(inflater, parent, false)
                return SingleItemInstalledAppInformationViewHolder(binding)
            }
        }

        fun bind(information : InstalledAppInformation?){
            binding.apply {
                appIcon.setImageDrawable(information?.icon)
                appName.text = information?.appName
            }
        }
    }
}

