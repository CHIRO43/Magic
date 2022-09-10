 package com.kh.magic.Professor

import Lecture1
import ProfLectureTimeTable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kh.magic.FBRef
import com.kh.magic.R
import java.util.ArrayList

class ScheduleActivity : AppCompatActivity() {

    lateinit var SDAdapter : ScheduleAdapter
    private val schedule : MutableList<ProfLectureTimeTable> = mutableListOf()
    private val subItemList: MutableList<Lecture1> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        val rv = findViewById<RecyclerView>(R.id.dayRV)
        val layoutManager = LinearLayoutManager(this@ScheduleActivity)
        rv.layoutManager = layoutManager

        SDAdapter = ScheduleAdapter(schedule)
        rv.adapter = SDAdapter


        val addBtn = findViewById<Button>(R.id.scheduleAddBtn)
        addBtn.setOnClickListener{
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.schedule_add_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("시간표")

            val alertDialog = mBuilder.show()

            alertDialog.findViewById<Button>(R.id.noneBtn)?.setOnClickListener {
                alertDialog.dismiss()
            }

            alertDialog.findViewById<Button>(R.id.saveBtn)?.setOnClickListener {
                val daySpinner = alertDialog.findViewById<Spinner>(R.id.day_spinner)
                val startSpinner = alertDialog.findViewById<Spinner>(R.id.timeStart_spinner)
                val endSpinner = alertDialog.findViewById<Spinner>(R.id.timeEnd_spinner)
                val text1 : String = daySpinner?.selectedItem.toString()
                val text2 : String = startSpinner?.selectedItem.toString()
                val text3 : String = endSpinner?.selectedItem.toString()

                val partClass = alertDialog.findViewById<EditText>(R.id.partClassEdit)?.text.toString()
                val subject = alertDialog.findViewById<EditText>(R.id.subjectEdit)?.text.toString()
                val room = alertDialog.findViewById<EditText>(R.id.roomEdit)?.text.toString()

                val subItem= Lecture1(text1,text2,text3,partClass,subject,room)
                val model = ProfLectureTimeTable(text1,subItemList)

                subItemList.add(subItem)//Lecture1 리스트에 추가
                when (text1) {
                    "월요일" -> {
                        FBRef.LectureRef.child("A").setValue(model)
                    }
                    "화요일" -> {
                        FBRef.LectureRef.child("B").setValue(model)
                    }
                    "수요일" -> {
                        FBRef.LectureRef.child("C").setValue(model)
                    }
                    "목요일" -> {
                        FBRef.LectureRef.child("D").setValue(model)
                    }
                    "금요일" -> {
                        FBRef.LectureRef.child("E").setValue(model)
                    }
                }
                alertDialog.dismiss()
            }
        }

        getData()
    }

    private fun getData() {
        FBRef.LectureRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                schedule.clear()
                // Get Post object and use the values to update the UI
                for (dataModel in dataSnapshot.children) {
                    schedule.add(dataModel.getValue(ProfLectureTimeTable::class.java)!!)
                }
                SDAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }
}
