package com.kh.magic.Professor

import Lecture1
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kh.magic.R


open class DayAdapter(val items: MutableList<Lecture1>?) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {

    interface ItemClick {
        fun onItemClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    // 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: ItemClick) {
        this.itemClick = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.schedule_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayAdapter.ViewHolder, position: Int) {

        val item = items?.get(position)

        if (itemClick != null) {
            holder.itemView.setOnClickListener { v ->
                itemClick?.onItemClick(v, position)
            }
        }

        with(holder){
            timeStartText.text = item?.startTime
            timeEndText.text= item?.endTime
            subjectText.text = item?.lectureName
            roomText.text= item?.lectureRoom
            partClassText.text = item?.lectureClass
        }
    }

    override fun getItemCount(): Int {
        return items?.size!!
    }

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

        val timeStartText = itemView.findViewById<TextView>(R.id.timeStartText)
        val timeEndText = itemView.findViewById<TextView>(R.id.timeEndText)
        val subjectText = itemView.findViewById<TextView>(R.id.subjectText)
        val roomText = itemView.findViewById<TextView>(R.id.roomText)
        val partClassText = itemView.findViewById<TextView>(R.id.partClassText)

    }
}