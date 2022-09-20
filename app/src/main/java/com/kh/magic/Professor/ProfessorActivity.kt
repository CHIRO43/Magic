package com.kh.magic.Professor

import ProfInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kh.magic.FBRef
import com.kh.magic.R

class ProfessorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professor)

        val email = findViewById<TextView>(R.id.profEmail)
        val major = findViewById<TextView>(R.id.profMajor)
        val name = findViewById<TextView>(R.id.profName)
        val position = findViewById<TextView>(R.id.profPosition)
        val telNum = findViewById<TextView>(R.id.profTelNum)

        val model = ProfInfo(email.toString(),major.toString(),name.toString(),position.toString(),telNum.toString())
        FBRef.InfoRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val info = dataSnapshot.getValue(model::class.java)
                email.text = info?.profEmail
                major.text= info?.profMajor
                name.text =info?.profName
                position.text = info?.profPosition
                telNum.text= info?.profTelNum

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }
}