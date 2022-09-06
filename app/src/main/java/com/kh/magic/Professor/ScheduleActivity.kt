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

    private val schedule : MutableList<ProfLectureTimeTable> = ArrayList()
    private val subItemList: MutableList<Lecture1> = ArrayList()

    // 그안에 존재하는 하위 아이템 박스(3개씩 보이는 아이템들)
    private fun buildSubItemList(): MutableList<Lecture1> {
        return subItemList
    }

    // 상위아이템 큰박스 아이템을 5개 만듭니다.
    private fun buildItemList(): MutableList<ProfLectureTimeTable> {

        fun setItem(day: ProfLectureTimeTable){
            if(schedule.equals(day.days.equals("월"))) {

            }
        }
        schedule.add(ProfLectureTimeTable("월", buildSubItemList()))
        schedule.add(ProfLectureTimeTable("화", buildSubItemList()))
        schedule.add(ProfLectureTimeTable("수", buildSubItemList()))
        schedule.add(ProfLectureTimeTable("목", buildSubItemList()))
        schedule.add(ProfLectureTimeTable("금", buildSubItemList()))

        return schedule
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)


        val rv = findViewById<RecyclerView>(R.id.dayRV)
        val layoutManager = LinearLayoutManager(this@ScheduleActivity)
        val itemAdapter = ScheduleAdapter(buildItemList())

        rv.adapter = itemAdapter
        rv.layoutManager = layoutManager

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
                val text3 : String = endSpinner?.selectedItem.toString()//파베 setvalue에 넣어보기

                val partClass = alertDialog.findViewById<EditText>(R.id.partClassEdit)?.text.toString()
                val subject = alertDialog.findViewById<EditText>(R.id.subjectEdit)?.text.toString()
                val room = alertDialog.findViewById<EditText>(R.id.roomEdit)?.text.toString()

                val subItem= Lecture1(text1,text2,text3,partClass,subject,room)

                subItemList.add(subItem)
                val model = ProfLectureTimeTable(text1,buildSubItemList())
                val key = FBRef.LectureRef.push().key.toString()

                FBRef.LectureRef.child(key).setValue(model)
                itemAdapter.notifyDataSetChanged()
               alertDialog.dismiss()
            }
        }

        //getData()
//        SDAdapter.notifyDataSetChanged()
    }

    private fun getData() {
        FBRef.LectureRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                schedule.clear()
                // Get Post object and use the values to update the UI
                for (dataModel in dataSnapshot.children) {
                    schedule.add(dataModel.getValue(ProfLectureTimeTable::class.java)!!)

                }
//                SDAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }
}
