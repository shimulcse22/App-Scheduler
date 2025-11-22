package com.example.appscheduler.dialogs

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.example.appscheduler.R

import com.example.appscheduler.databinding.DialogSetScheduleLayoutBinding


class SchedulerDialog(context: Context) : AlertDialog(context, R.style.CommonDialog) {

    private lateinit var binding: DialogSetScheduleLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSetScheduleLayoutBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        setCancelable(true)

        with(context){
            
        }

    }

}