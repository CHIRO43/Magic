package com.kh.magic.Professor

import Lecture1
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kh.magic.R


class DayAdapter(val items: MutableList<Lecture1>) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {

    private var itemClick: ItemClick? = null

    interface ItemClick {
        fun onItemClick(view: View, position: Int) //뷰와 포지션값
    }

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