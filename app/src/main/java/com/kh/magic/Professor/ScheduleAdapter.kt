package com.kh.magic.Professor

import Lecture1
import ProfLectureTimeTable
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kh.magic.R


// 여기서 items의 형식은 본인이 서버에서 데이터를 GET해서 오는 것이라면 reponsedata를, 직접 만든 데이터클래스를 사용하고 싶으면 dataclass의 이름을 넣으면 된다.
class ScheduleAdapter(private val context: Context,val items : MutableList<ProfLectureTimeTable>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

//    val adapter: DayAdapter? = null
    private var DayList = items
    private val mContext: Context = context
    // 뷰홀더를 만들고 뷰를 초기화하는 함수이다. 아직 바인딩이 안되었기 때문에 뷰에 내용이 직접적으로 담기는 과정은 아니다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false)

        return ViewHolder(view).apply {  }
    }

    // 여기서 뷰와 데이터의 결합이 이루어진다. 만약 이미지 url을 가져오면 이 함수에서 url을 뷰에 넣어서 사진을 출력할 수 있다.(단, 이미지는 Glide와 같은 외부 라이브러리 사용을 추천)
    override fun onBindViewHolder(holder: ScheduleAdapter.ViewHolder, position: Int) {
        //holder.day_Text.text = items[position].toString()

        val day: ProfLectureTimeTable = items[position]
        holder.setItem(day)

        holder.schedule_RV.adapter = DayAdapter(context,
            DayList[position].lecture1
        )
        holder.schedule_RV.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        holder.schedule_RV.setHasFixedSize(true)

        holder.day_Text.text = DayList.get(position).day

//        val daysArryList: ArrayList<String> = ArrayList()
//        daysArryList.clear()
//        holder.schedule_RV.adapter = adapter
//        adapter?.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

            var day_Text = itemView.findViewById<TextView>(R.id.dayText)!!
            var schedule_RV = itemView.findViewById<RecyclerView>(R.id.scheduleRV)!!

        fun setItem(day: ProfLectureTimeTable){
            day_Text.text = day.day
        }

    }
}
