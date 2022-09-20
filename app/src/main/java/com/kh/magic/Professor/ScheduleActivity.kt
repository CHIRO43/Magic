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

 class ScheduleActivity : AppCompatActivity() {

    lateinit var SDAdapter : ScheduleAdapter
    private val schedule : MutableList<ProfLectureTimeTable> = mutableListOf()
    private var subItemList: MutableList<Lecture1> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        val rv = findViewById<RecyclerView>(R.id.dayRV)
        val layoutManager = LinearLayoutManager(this@ScheduleActivity)
        rv.layoutManager = layoutManager

        SDAdapter = ScheduleAdapter(schedule)
        rv.adapter = SDAdapter

        getData()

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
                subItemList.add(subItem)//Lecture1 리스트에 추가

                val monList = subItemList.filter { it.day == "월요일" }
                val tueList = subItemList.filter { it.day == "화요일" }
                val wedList = subItemList.filter { it.day == "수요일" }
                val thuList = subItemList.filter { it.day == "목요일" }
                val friList = subItemList.filter { it.day == "금요일" }
                val model1 = ProfLectureTimeTable(text1, monList as MutableList<Lecture1>)
                val model2 = ProfLectureTimeTable(text1, tueList as MutableList<Lecture1>)
                val model3 = ProfLectureTimeTable(text1, wedList as MutableList<Lecture1>)
                val model4 = ProfLectureTimeTable(text1, thuList as MutableList<Lecture1>)
                val model5 = ProfLectureTimeTable(text1, friList as MutableList<Lecture1>)

                when (subItem.day) {
                    "월요일" -> {
                        FBRef.LectureRef.child("A").setValue(model1)
                    }
                    "화요일" -> {
                        FBRef.LectureRef.child("B").setValue(model2)
                    }
                    "수요일" -> {
                        FBRef.LectureRef.child("C").setValue(model3)
                    }
                    "목요일" -> {
                        FBRef.LectureRef.child("D").setValue(model4)
                    }
                    "금요일" -> {
                        FBRef.LectureRef.child("E").setValue(model5)
                    }
                }
                alertDialog.dismiss()
            }
        }


    }

    private fun getData() {
        FBRef.LectureRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val returnSubList :  MutableList<Lecture1> = mutableListOf()
                schedule.clear()

                for (dataModel in dataSnapshot.children) {
                    schedule.add(dataModel.getValue(ProfLectureTimeTable::class.java)!!)
                    val value1 = dataModel.getValue(ProfLectureTimeTable::class.java)
                    subItemList=value1!!.lecture1

                    for ( i in 0 until subItemList.count()){
                        returnSubList.add(subItemList[i])
                    }
                }
                subItemList = returnSubList
                SDAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }
}
