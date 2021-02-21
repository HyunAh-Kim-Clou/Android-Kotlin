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

        // 현재 시각을 startAt에 저장
        startAt = LocalDateTime.now().format(timeformat)
        Thread(Runnable {
            kotlin.run {
                while(true) {
                    // 화면에서 증가하는 period 값 확인
                    takingtime.text = period.toString()
                    Thread.sleep(1000)
                    period += 1
                }
            }
        }).start()

        endbtn.setOnClickListener {
            // 현재 시각을 endAt에 저장, EditText의 값을 donetask에 저장
            endAt = LocalDateTime.now().format(timeformat)
            donetask = doingtask.text.toString()

            // DB에 해당 timerobj를 저장
            db?.TimerObjDao()?.insert(TimerObj(startAt, endAt, donetask, getPeriod(period)))

            // Log를 통해 삽입된 timerobj 확인
            Log.d("TA10001", " - inserted timer obj: ${startAt}, ${endAt}, ${doingtask}, ${takingtime}")

            // MainActivity로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // sec값을 "$hour H $min M $sec S" 형태로 반환
    fun getPeriod(delayedsec: Int): String {
        var tmp = delayedsec
        var sec = tmp%60
        if (delayedsec < 60) return "${sec}S"

        tmp -= sec
        var min = tmp/60
        if (delayedsec < 3600) return "${min}M ${sec}S"

        tmp -= 60*min
        var hour = tmp/24
        return "${hour}H ${min}M ${sec}S"
    }
}
