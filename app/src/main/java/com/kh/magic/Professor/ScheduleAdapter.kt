package com.kh.magic.Professor

import ProfLectureTimeTable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kh.magic.R


// 여기서 items의 형식은 본인이 서버에서 데이터를 GET해서 오는 것이라면 reponsedata를, 직접 만든 데이터클래스를 사용하고 싶으면 dataclass의 이름을 넣으면 된다.
class ScheduleAdapter(val items: MutableList<ProfLectureTimeTable>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    // 뷰홀더를 만들고 뷰를 초기화하는 함수이다. 아직 바인딩이 안되었기 때문에 뷰에 내용이 직접적으로 담기는 과정은 아니다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false)

        return ViewHolder(view).apply {  }
    }

    // 여기서 뷰와 데이터의 결합이 이루어진다.
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
            layoutManager.initialPrefetchItemCount = day.lecture1.size

            //자식 어댑터 설정
            val dayAdapter = DayAdapter(day.lecture1)
            schedule_RV.layoutManager = layoutManager
            schedule_RV.adapter = dayAdapter
            schedule_RV.setRecycledViewPool(viewPool)

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
