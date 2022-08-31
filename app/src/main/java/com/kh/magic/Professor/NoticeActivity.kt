package com.kh.magic.Professor

import ProfBulletIn
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kh.magic.FBRef
import com.kh.magic.R


class NoticeActivity : AppCompatActivity() {


    lateinit var NTAdapter : NoticeAdapter
    val notice = mutableListOf<ProfBulletIn>()

    private val keyList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        NTAdapter = NoticeAdapter(notice)
        val lv = findViewById<ListView>(R.id.noticeLV)
        lv.adapter = NTAdapter

        getData()

        //아이템클릭시 삭제 다이얼로그 띄움
        lv.onItemClickListener = OnItemClickListener { parent, v, position, id ->
            val mDialogView = layoutInflater.inflate(R.layout.remove_dialog, null)
            val mBuilder = AlertDialog.Builder(this@NoticeActivity)
                .setView(mDialogView)
                .setTitle("삭제")
                .setMessage("내용을 삭제하시겠습니까?")
            val alertDialog = mBuilder.show()

            alertDialog.findViewById<Button>(R.id.okBtn)?.setOnClickListener {
                FBRef.noticeRef.child(keyList[position]).removeValue() //notice로는 삭제가 안됨 keyList에 담아서 삭제됨
                alertDialog.dismiss()
            }

            alertDialog.findViewById<Button>(R.id.noBtn)?.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        val addBtn = findViewById<Button>(R.id.addNT)
        addBtn.setOnClickListener {

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("공지 사항")

            val mAlertDialog = mBuilder.show()

            val saveBtn = mAlertDialog.findViewById<Button>(R.id.saveBtn)
            saveBtn?.setOnClickListener{

                val noticeWrite = mAlertDialog.findViewById<EditText>(R.id.noticeWrite)?.text.toString()

                val model = ProfBulletIn(noticeWrite)
                val key = FBRef.noticeRef.push().key.toString()

                FBRef.noticeRef.child(key).
                setValue(model)
                NTAdapter.notifyDataSetChanged()
                mAlertDialog.dismiss()
            }
        }

    }

    private fun getData() {

        FBRef.noticeRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                notice.clear()
                // Get Post object and use the values to update the UI
                for (dataModel in dataSnapshot.children) {
                    notice.add(dataModel.getValue(ProfBulletIn::class.java)!!)
                    keyList.add(dataModel.key.toString())
                }
                NTAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException())
            }
        })

    }

}