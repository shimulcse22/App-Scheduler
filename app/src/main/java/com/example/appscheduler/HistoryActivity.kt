package com.example.appscheduler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appscheduler.databinding.ActivityHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyAdapter: ScheduleHistoryAdapter
    private lateinit var binding: ActivityHistoryBinding

    private val viewModel : MainViewModel  by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.history)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getAllSchedule()

        setUpRecyclerView()
        observeData()

        binding.buttonAppList.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            try {
                finishAffinity()
            } catch (ex : Exception){
                ex.printStackTrace()
            }
        }
    }

    private fun setUpRecyclerView(){
        historyAdapter = ScheduleHistoryAdapter()
        binding.installedAppInfoRecyclerview.adapter = historyAdapter
    }


    private fun observeData() {
        viewModel.onGetAppSchedule().observe(this){ data ->
            Log.e("TAG", "observeData: $data", )
            historyAdapter.setItems(data)
        }
    }

}