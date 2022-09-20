package com.kh.magic.Professor

import Lecture1
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.kh.magic.FBRef
import com.kh.magic.R


class DayAdapter(val items: MutableList<Lecture1>) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {

    interface ItemClick {
        fun onItemClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.schedule_item, parent, false)
        return ViewHolder(view).apply {  }
    }

    override fun onBindViewHolder(holder: DayAdapter.ViewHolder, position: Int) {
        val item =  items[position]

        if(itemClick != null){
            holder.itemView.setOnClickListener{v ->
                itemClick?.onItemClick(v, position)
            }
        }
        with(holder){
            timeStartText.text = item.startTime
            timeEndText.text= item.endTime
            subjectText.text = item.lectureName
            roomText.text= item.lectureRoom
            partClassText.text = item.lectureClass

            itemView.setOnClickListener {
                val mDialogView = LayoutInflater.from(it.context).inflate(R.layout.remove_dialog, null)
                val mBuilder = AlertDialog.Builder(it.context)
                    .setView(mDialogView)
                    .setTitle("삭제")
                    .setMessage("내용을 삭제하시겠습니까?")
                val alertDialog = mBuilder.show()
                alertDialog.findViewById<Button>(R.id.okBtn)?.setOnClickListener {
                    when(item.day){
                        "월요일" -> {
//                            FBRef.LectureRef.child("A").child("lecture1").child(position.toString()).removeValue()
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
                    alertDialog.dismiss()
                }

                alertDialog.findViewById<Button>(R.id.noBtn)?.setOnClickListener {
                    alertDialog.dismiss()
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

        val timeStartText = itemView.findViewById<TextView>(R.id.timeStartText)!!
        val timeEndText = itemView.findViewById<TextView>(R.id.timeEndText)!!
        val subjectText = itemView.findViewById<TextView>(R.id.subjectText)!!
        val roomText = itemView.findViewById<TextView>(R.id.roomText)!!
        val partClassText = itemView.findViewById<TextView>(R.id.partClassText)!!
    }
}