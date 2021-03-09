package com.example.calenderpoc

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.calenderpoc.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity(), CalenderFragment.SendData {

    lateinit var calenderButton: Button
    lateinit var addeventButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calenderButton = findViewById(R.id.calenderButton)
        addeventButton = findViewById(R.id.add_event_button)
        calenderButton.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, CalenderFragment())
                .commit()
        }

    }

    override fun sendData(eventTitle: String, startDate: String, startTime: String, endDate: String, endTime: String) {
        Log.d("Main", "Data received")
        addEvent(eventTitle, startDate, startTime, endDate, endTime)

    }

    private fun addEvent(eventTitle: String, startDate: String, startTime: String, endDate: String, endTime: String) {
        var fragment = supportFragmentManager.fragments
        Log.d("Main", "addEvent")
        supportFragmentManager.beginTransaction().remove(fragment[0]).commit()
        addeventButton.visibility = View.VISIBLE
        addeventButton.setOnClickListener {
            Log.d("MAIN", "inside add event")
            addCalenderEvent(eventTitle, startDate, startTime, endDate, endTime)
        }
    }

    private fun addCalenderEvent (eventTitle: String, startDate: String, startTime: String, endDate: String, endTime: String) {
        //TODO check if permission is garanted or not then add to calender
     var intent = Intent(Intent.ACTION_INSERT)

        var startDateArray = startDate.split("-").map { it.toInt() }
        var startTimeArray = startTime.split(":").map { it.toInt() }

        var endDateArray = endDate.split("-").map { it.toInt() }
        var endTimeArray = endTime.split(":").map { it.toInt() }

        val startMillis: Long = Calendar.getInstance().run {
            set(startDateArray[2],startDateArray[1],startDateArray[0],startTimeArray[0],startTimeArray[1])
            timeInMillis
        }

        val endMillis: Long = Calendar.getInstance().run {
            set(endDateArray[2],endDateArray[1],endDateArray[0],endTimeArray[0],endTimeArray[1])
            timeInMillis
        }

        intent.run {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE,eventTitle)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endMillis)
        }
        startActivity(intent)

    }

}

