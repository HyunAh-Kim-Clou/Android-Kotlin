package com.rndns.timeredt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    lateinit var recordlist: ListView
    lateinit var startbtn: Button
    lateinit var showRecord: Button
    var db: TimerDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recordlist = findViewById(R.id.recordlist)
        startbtn = findViewById(R.id.startbtn)
        showRecord = findViewById(R.id.showRecord)
        startbtn.setOnClickListener {
            // Go next page
            val intent = Intent(this, TimerActivity::class.java)
            startActivity(intent)
        }
        showRecord.setOnClickListener {
            db = TimerDatabase.getInstance(this)
            var timerlist: List<TimerObj>? = db?.TimerObjDao()?.getAll()

            System.out.println("  [showRecord clicked] > ")
            System.out.println("       - timerlist: "+timerlist.toString())

            // recordlist.adapter // Hmmm..
        }
    }
}