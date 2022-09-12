package com.kh.magic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kh.magic.Professor.NoticeActivity
import com.kh.magic.Professor.ProfessorActivity
import com.kh.magic.Professor.ScheduleActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val profBtn = findViewById<Button>(R.id.ProfBtn)
        profBtn.setOnClickListener {
            val intent = Intent(this, ProfessorActivity::class.java)
            startActivity(intent)
        }

        val scheduleBtn = findViewById<Button>(R.id.ScheduleBtn)
        scheduleBtn.setOnClickListener {
            val intent = Intent(this, ScheduleActivity::class.java)
            startActivity(intent)
        }

        val noticeBtn = findViewById<Button>(R.id.NoticeBtn)
        noticeBtn.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java)
            startActivity(intent)
        }
        }

    }
