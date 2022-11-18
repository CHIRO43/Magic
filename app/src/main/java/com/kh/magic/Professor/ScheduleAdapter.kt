package com.kh.magic.Professor

import Lecture1
import ProfLectureTimeTable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kh.magic.FBRef
import com.kh.magic.R


class ScheduleAdapter(var items: MutableList<ProfLectureTimeTable>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    private fun getData(){
        FBRef.LectureRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                items.clear()

                for (dataModel in dataSnapshot.children) {
                    dataModel.getValue(ProfLectureTimeTable::class.java)?.let { items.add(it) }
                }
                notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleAdapter.ViewHolder, position: Int) {

        val day: ProfLectureTimeTable = items[position]

        with(holder) {
            day_Text.text = day.days

            //자식 레이아웃 매니저 설정
            val layoutManager = LinearLayoutManager(
                schedule_RV.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            //자식 어댑터 설정
            val dayAdapter = DayAdapter(day.lecture1)
            schedule_RV.layoutManager = layoutManager
            schedule_RV.adapter = dayAdapter

            dayAdapter.setItemClickListener(object: DayAdapter.ItemClick{
                override fun onItemClick(view: View, position: Int) {
                    val mDialogView = LayoutInflater.from(view.context).inflate(R.layout.remove_dialog, null)
                    val mBuilder = AlertDialog.Builder(view.context)
                        .setView(mDialogView)
                        .setTitle("삭제")
                        .setMessage("내용을 삭제하시겠습니까?")
                    val alertDialog = mBuilder.show()
                    alertDialog.findViewById<Button>(R.id.okBtn)?.setOnClickListener {

                        day.lecture1?.removeAt(position)
                        val beforeItem = items //해당 day아이템만 제거한 스케줄

                        //요일 스케줄 파베에서 삭제
                        when(day.lecture1?.get(0)?.day){
                            "월요일" -> {
                                FBRef.LectureRef.child("A").removeValue()
                            }
                            "화요일" -> {
                                FBRef.LectureRef.child("B").removeValue()
                            }
                            "수요일" -> {
                                FBRef.LectureRef.child("C").removeValue()
                            }
                            "목요일" -> {
                                FBRef.LectureRef.child("D").removeValue()
                            }
                            "금요일" -> {
                                FBRef.LectureRef.child("E").removeValue()
                            }
                        }
                        //해당 day아이템만 삭제했기에 그 아이템과 같은 요일, 다른 아이템+다른 요일 아이템을 넘겨줌
                        items=beforeItem

                        //다시 파베에 추가
                        val monList = day.lecture1?.filter { it?.day == "월요일" }
                        val tueList = day.lecture1?.filter { it?.day == "화요일" }
                        val wedList = day.lecture1?.filter { it?.day == "수요일" }
                        val thuList = day.lecture1?.filter { it?.day == "목요일" }
                        val friList = day.lecture1?.filter { it?.day == "금요일" }
                        val model1 = ProfLectureTimeTable("월요일", monList as MutableList<Lecture1>)
                        val model2 = ProfLectureTimeTable("화요일", tueList as MutableList<Lecture1>)
                        val model3 = ProfLectureTimeTable("수요일", wedList as MutableList<Lecture1>)
                        val model4 = ProfLectureTimeTable("목요일", thuList as MutableList<Lecture1>)
                        val model5 = ProfLectureTimeTable("금요일", friList as MutableList<Lecture1>)
                        when (day.lecture1?.get(0)?.day) {
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

                        notifyItemRemoved(position)
                        alertDialog.dismiss()
                    }

                    alertDialog.findViewById<Button>(R.id.noBtn)?.setOnClickListener {
                        alertDialog.dismiss()
                    }

                }
            })
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

            val day_Text = itemView.findViewById<TextView>(R.id.dayText)!!
            var schedule_RV = itemView.findViewById<RecyclerView>(R.id.scheduleRV)!!

    }
}


