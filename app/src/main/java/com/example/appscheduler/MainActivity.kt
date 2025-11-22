package com.example.appscheduler

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appscheduler.databinding.ActivityMainBinding
import com.example.appscheduler.datasource.model.InstalledAppInformation

class MainActivity : AppCompatActivity() {

    private lateinit var installedAppInformationAdapter: InstalledAppInformationAdapter
    private lateinit var binding: ActivityMainBinding


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

        installedAppInformationAdapter = InstalledAppInformationAdapter()

        binding.installedAppInfoRecyclerview.adapter = installedAppInformationAdapter


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

        installedAppInformationAdapter.setData(launchableApps)
    }
}