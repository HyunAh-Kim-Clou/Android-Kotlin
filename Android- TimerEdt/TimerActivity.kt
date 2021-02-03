package com.rndns.timeredt

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TimerActivity : Activity() {
    lateinit var doingtask: EditText
    lateinit var takingtime: TextView
    lateinit var endbtn: Button
    var db: TimerDatabase? = null
    lateinit var timeformat: DateTimeFormatter

    lateinit var startAt: String
    lateinit var endAt: String
    lateinit var donetask: String
    var period: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        doingtask = findViewById(R.id.doingtask)
        takingtime = findViewById(R.id.takingtime)
        endbtn = findViewById(R.id.endbtn)
        db = TimerDatabase.getInstance(this)
        timeformat = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")

        // Start timer
        startAt = LocalDateTime.now().format(timeformat)
        Thread(Runnable {
            kotlin.run {
                while(true) {
                    // print current time
                    var currenttime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"))
                    takingtime.text = currenttime
                    Thread.sleep(1000)
                    period += 1
                }
            }
        }).start()

        endbtn.setOnClickListener {
            endAt = LocalDateTime.now().format(timeformat)
            donetask = doingtask.text.toString()
            db?.TimerObjDao()?.insert(TimerObj(startAt, endAt, donetask, getPeriod(period)))

            // verify recorded timer data
            var logstr: String = "  [End Btn Clicked] >"+
                    "       - startat: "+startAt+
                    "       - endat: "+endAt+
                    "       - doingtask: "+doingtask.text+
                    "       - takingtime: "+takingtime.text
            System.out.println(logstr)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun getPeriod(delayedsec: Int): String {
        var tmp = delayedsec
        var sec = tmp%60
        if (delayedsec < 60) return "$sec S"

        tmp -= sec
        var min = tmp/60
        if (delayedsec < 3600) return "$min M $sec S"

        tmp -= 60*min
        var hour = tmp/24
        return "$hour H $min M $sec S"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertFormatOfTime(time: String, beforeFormat: String, afterFormat: String): String {
        var bFormat = DateTimeFormatter.ofPattern(beforeFormat)
        var timeobj = bFormat.parse(time) as LocalDateTime
        var aFormat = timeobj.format(DateTimeFormatter.ofPattern(afterFormat))
        return aFormat
    }
}