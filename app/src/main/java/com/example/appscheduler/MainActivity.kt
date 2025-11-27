package com.example.appscheduler

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appscheduler.databinding.ActivityMainBinding
import com.example.appscheduler.datasource.model.InstalledAppInformation
import com.example.appscheduler.datasource.model.Timer
import com.example.appscheduler.dialogs.SchedulerDialog
import com.example.appscheduler.listeners.OnIItemClickListener
import com.example.appscheduler.listeners.ScheduleListener
import com.example.appscheduler.utility.CANCELED
import com.example.appscheduler.utility.IT_THE_TIME
import com.example.appscheduler.utility.cancelAlarm
import com.example.appscheduler.utility.orEmpty
import com.example.appscheduler.utility.orZero
import com.example.appscheduler.utility.scheduleAlarm
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnIItemClickListener, ScheduleListener {

    private lateinit var installedAppInformationAdapter: InstalledAppInformationAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var schedulerDialog: SchedulerDialog

    private val viewModel : MainViewModel  by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpSchedulerRecyclerView()
        observeData()
        setUpDialog()

        installedAppInformationAdapter.setData(getLaunchableApp())

        binding.buttonShowHistory.setOnClickListener {
            val historyIntent = Intent(this, HistoryActivity::class.java)
            startActivity(historyIntent)
        }
    }

    private fun setUpSchedulerRecyclerView(){
        installedAppInformationAdapter = InstalledAppInformationAdapter(this)
        binding.installedAppInfoRecyclerview.adapter = installedAppInformationAdapter
    }

    private fun setUpDialog(){
        schedulerDialog = SchedulerDialog(this)
    }

    private fun observeData() {
        viewModel.onGetAppScheduleByPackage().observe(this){
            if(it == null || it.executionInformation == CANCELED){
                schedulerDialog.show()
            } else {
                val timer = Timer(
                    date = it.date.orEmpty(),
                    hour = it.hour.orZero(),
                    min = it.min.orZero()
                )
                UpdateAppScheduleBottomSheet.show(supportFragmentManager,timer)
            }
        }
    }

    private fun getLaunchableApp() : List<InstalledAppInformation>{
        val pm = this.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val resolveInfos = pm.queryIntentActivities(intent, 0)

        val launchableApps = resolveInfos.map {
            InstalledAppInformation(
                appName = it.loadLabel(pm).toString(),
                packageNameOfApp = it.activityInfo.packageName,
                icon = it.loadIcon(pm)
            )
        }

        return launchableApps
    }

    override fun onItemClick(installedAppInformation: InstalledAppInformation?) {
        viewModel.getAppScheduleByPackageName(installedAppInformation?.packageNameOfApp)
        viewModel.installedAppInformation.value = installedAppInformation
    }

    override fun setSchedule(time: Timer) {
        schedulerDialog.dismiss()
        scheduleAlarm(
            this,
            time,
            IT_THE_TIME,
            "Open your scheduled ${viewModel.installedAppInformation.value?.appName} app",
            viewModel.installedAppInformation.value?.packageNameOfApp ?: packageName,
        )
        viewModel.saveAlarmInformation(time)
    }

    override fun onCancelButtonClick(timer: Timer?) {
        cancelAlarm(this,timer)
        viewModel.cancelSchedule()
    }
}