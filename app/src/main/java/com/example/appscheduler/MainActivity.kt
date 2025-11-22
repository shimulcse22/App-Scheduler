package com.example.appscheduler

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appscheduler.databinding.ActivityMainBinding
import com.example.appscheduler.datasource.model.InstalledAppInformation
import com.example.appscheduler.dialogs.SchedulerDialog
import com.example.appscheduler.listeners.OnIItemClickListener

class MainActivity : AppCompatActivity(), OnIItemClickListener {

    private lateinit var installedAppInformationAdapter: InstalledAppInformationAdapter
    private lateinit var binding: ActivityMainBinding

    private lateinit var schedulerDialog: SchedulerDialog


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

        setUpDialog()

        installedAppInformationAdapter.setData(getLaunchableApp())
    }

    private fun setUpSchedulerRecyclerView(){
        installedAppInformationAdapter = InstalledAppInformationAdapter(this)
        binding.installedAppInfoRecyclerview.adapter = installedAppInformationAdapter
    }

    private fun setUpDialog(){
        schedulerDialog = SchedulerDialog(this)
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

    override fun onItemClick(appPackageName: String?) {
        schedulerDialog.show()
    }
}