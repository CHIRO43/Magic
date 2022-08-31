package com.kh.magic.Professor

import ProfBulletIn
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.kh.magic.R

class NoticeAdapter (val List: MutableList<ProfBulletIn>) : BaseAdapter(){


    override fun getCount(): Int {
        return List.count()
    }

    override fun getItem(position: Int): Any {
        return List[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if (view == null){
            view = LayoutInflater.from(parent?.context).inflate(R.layout.notice_item, parent, false)
        }


        val notice =  view?.findViewById<TextView>(R.id.noticeItem)
        notice!!.text = List[position].notice
        return view!!
    }
}