package com.rndns.timeredt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


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

        // 클릭하면, TimerActivity로 이동
        startbtn.setOnClickListener {
            val intent = Intent(this, TimerActivity::class.java)
            startActivity(intent)
        }
        // 클릭하면, DB를 연결하여 저장된 List<TimerObj> 출력
        showRecord.setOnClickListener {
            db = TimerDatabase.getInstance(this)
            var timerlist: List<TimerObj>? = db?.TimerObjDao()?.getAll()

            // Log를 통해 확인
            Log.d("MA10001", " - is DB open: "+db?.isOpen)
            Log.d("MA10001", " - timer list: "+timerlist.toString())

            // 화면 상단의 리스트뷰에서 확인
            if (!timerlist?.isEmpty()!!) {
                val adapter = ArrayAdapter<TimerObj>(this, android.R.layout.simple_list_item_1, timerlist!!)
                recordlist.setAdapter(adapter)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "There's no timer data!", Toast.LENGTH_LONG)
            }
        }
    }
}
