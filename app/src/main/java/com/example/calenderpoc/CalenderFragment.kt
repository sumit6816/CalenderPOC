package com.example.calenderpoc

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calenderpoc.databinding.FragmentCalenderBinding
import java.util.*


class CalenderFragment : Fragment() {

    var startDate = ""
    var startTime = ""
    var endDate = ""
    var endTime = ""
    var eventTitle = ""

    lateinit var binding: FragmentCalenderBinding
    lateinit var sendDataListener : SendData

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SendData) {
            sendDataListener = context
        } else {
            throw ClassCastException(
                context.toString() + " must implement SendData.")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var fragmentView = inflater.inflate(R.layout.fragment_calender, container, false)

        binding = FragmentCalenderBinding.bind(fragmentView)
        binding.btnStartDateTime.setOnClickListener {
            pickDateTime(it)
        }
        binding.btnEndDateTime.setOnClickListener {
            pickDateTime(it)
        }

        binding.selectTimezone.setOnClickListener {

        }

        binding.okButton.setOnClickListener {
            eventTitle = binding.etTitle.text.toString()
            sendDataListener.sendData(eventTitle,startDate, startTime, endDate, endTime)
        }
        return fragmentView
    }


    private fun pickDateTime(view: View) {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
            TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { _, hour, minute ->

                if (view == binding.btnStartDateTime) {
                    startDate = "$day-$month-$year"
                    startTime = "$hour:$minute"
                    setStartTimeDate(startDate, startTime)
                } else if (view == binding.btnEndDateTime) {
                    endDate = "$day-$month-$year"
                    endTime = "$hour:$minute"
                    setEndTimeDate(endDate, endTime)
                }
                /*val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                doSomethingWith(pickedDateTime)*/
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }

    private fun setStartTimeDate(startDate: String, startTime: String) {
        binding.tvStartDate.text = startDate
        binding.tvStartTime.text = startTime
    }

    private fun setEndTimeDate(endDate: String, endTime: String) {
        binding.tvEndDate.text = endDate
        binding.tvEndTime.text = endTime
    }

    interface SendData {
        fun sendData(eventTitle: String, startDate: String, startTime: String, endDate:String, endTime: String)
    }

}