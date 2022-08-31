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
    private val schedule = mutableListOf<ProfLectureTimeTable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        SDAdapter = ScheduleAdapter(this,schedule)
        val rv = findViewById<RecyclerView>(R.id.dayRV)
        rv.adapter = SDAdapter
        rv.layoutManager = LinearLayoutManager(this)

//        schedule.add(ProfLectureTimeTable("d",Lecture1("d",1,2,"a","s","m")))
//                tv_result!!.setText(parent.getItemAtPosition(position).toString())


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
            /*    val daySpinner = alertDialog.findViewById<Spinner>(R.id.day_spinner)
                val startSpinner = alertDialog.findViewById<Spinner>(R.id.timeStart_spinner)
                val endSpinner = alertDialog.findViewById<Spinner>(R.id.timeEnd_spinner)
                val text1 : String = daySpinner?.selectedItem.toString()
                val text2 : String = startSpinner?.selectedItem.toString()
                val text3 : String = endSpinner?.selectedItem.toString()//파베 setvalue에 넣어보기


                val subject = alertDialog.findViewById<EditText>(R.id.subjectEdit)?.text.toString()
                val room = alertDialog.findViewById<EditText>(R.id.roomEdit)?.text.toString()
                val partClass = alertDialog.findViewById<EditText>(R.id.partClassEdit)?.text.toString()

                val model = ProfLectureTimeTable("월",Lecture1(text1,text2,text3,partClass,subject,room))
                val key = FBRef.LectureRef.push().key.toString()

                FBRef.LectureRef.child(key).setValue(model)
                SDAdapter.notifyDataSetChanged()
               alertDialog.dismiss() */
            }
        }

        getData()

        SDAdapter.notifyDataSetChanged()
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
