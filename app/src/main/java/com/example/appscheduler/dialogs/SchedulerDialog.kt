package com.example.appscheduler.dialogs

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.example.appscheduler.R
import com.example.appscheduler.databinding.DialogSetScheduleLayoutBinding
import com.example.appscheduler.datasource.model.Timer
import com.example.appscheduler.listeners.OnIItemClickListener
import com.example.appscheduler.utility.orZero
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class SchedulerDialog(
    private val context: Context,
) : AlertDialog(context, R.style.CommonDialog) {

    private lateinit var binding: DialogSetScheduleLayoutBinding

    private var selectedHour : Int? = null
    private var selectedMinute : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSetScheduleLayoutBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        setCancelable(true)
        binding.dateInput.setOnClickListener {
            showDatePicker()
        }

        binding.timeInput.setOnClickListener {
            showTimePicker()
        }

        binding.buttonSetSchedule.setOnClickListener {
            val time = Timer(
                date = binding.dateInput.text.toString(),
                hour = selectedHour.orZero(),
                min = selectedMinute.orZero()
            )
            (context as? OnIItemClickListener)?.setSchedule(time)
            setEmpty()
        }
    }

    private fun setEmpty(){
        binding.dateInput.setText("")
        binding.timeInput.setText("")
    }

    private fun showDatePicker() {
        var calendar =  Calendar.getInstance().apply {
            add(Calendar.YEAR,0)
        }

        val datePickerDialog = DatePickerDialog(
            context,
            R.style.DatePickerDialog,
            { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                calendar = Calendar.getInstance().apply {
                    set(year, monthOfYear, dayOfMonth)
                }
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(calendar.time?:0)
                binding.dateInput.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }
    private fun showTimePicker() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setTitleText("Select alarm time")
            .build()

        picker.addOnPositiveButtonClickListener {
            selectedHour = picker.hour
            selectedMinute = picker.minute
            val time =String.format(Locale.getDefault(), "Selected: %02d:%02d", selectedHour, selectedMinute)
            binding.timeInput.setText(time)
        }

        picker.show((context as FragmentActivity).supportFragmentManager, "time_picker")
    }

}