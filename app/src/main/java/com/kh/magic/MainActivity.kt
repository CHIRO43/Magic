package com.kh.magic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kh.magic.Professor.NoticeActivity
import com.kh.magic.Professor.ProfessorActivity
import com.kh.magic.Professor.ScheduleActivity

class MainActivity : AppCompatActivity() {

//    lateinit var LVAdapter : ListViewAdapter
//    val list = mutableListOf<Model>()

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



//        LVAdapter = ListViewAdapter(list)
//        val lv = findViewById<ListView>(R.id.lv)
//        lv.adapter = LVAdapter
//
//        getData()
//    }
//        fun getData(){
//
//            val database = Firebase.database
//            val myRef = database.getReference("board")
//
//            val postListener = object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    list.clear()
//                    // Get Post object and use the values to update the UI
//                    for (dataModel in dataSnapshot.children) {
//                        val item  =dataModel.getValue(Model::class.java)
//                        Log.d("MainActivity", item.toString())
//                        list.add(dataModel.getValue(Model::class.java)!!)
//
//                    }
//                    LVAdapter.notifyDataSetChanged()
//
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    // Getting Post failed, log a message
//                    Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException())
//                }
//            }
//            myRef.addValueEventListener(postListener)
        }

    }
