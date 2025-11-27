package com.example.appscheduler.dialogs

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.appscheduler.R
import com.example.appscheduler.databinding.BottomSheetCancelScheduleBinding
import com.example.appscheduler.datasource.model.Timer
import com.example.appscheduler.listeners.ScheduleListener
import com.example.appscheduler.utility.TIME
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UpdateAppScheduleBottomSheet: BottomSheetDialogFragment() {

    private var viewBinding: BottomSheetCancelScheduleBinding? = null
    private var listener: ScheduleListener? = null

    private var timer: Timer? = null


    companion object {
        val TAG = UpdateAppScheduleBottomSheet::class.simpleName
        fun show(fragmentManager: FragmentManager, timer : Timer) {
            return UpdateAppScheduleBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(TIME, timer)
                }
            }.show(fragmentManager, TAG)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener =  parentFragment as? ScheduleListener ?: context as? ScheduleListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = BottomSheetCancelScheduleBinding.inflate(inflater,container,false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(TIME, Timer::class.java)
        } else {
            arguments?.getParcelable(TIME)
        }

        val time = timer.toString()

        viewBinding?.informationTextView?.text = context?.getString(R.string.you_already_schedule,time)

        setOnClickListener()
    }

    private fun setOnClickListener(){
        viewBinding?.cancelBtn?.setOnClickListener {
            dismiss()
            listener?.onCancelButtonClick(timer)
        }

        viewBinding?.btnClose?.setOnClickListener {
            dismiss()
        }
    }
}