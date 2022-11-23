package com.kh.magic.Professor

import ProfInfo
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kh.magic.FBRef
import com.kh.magic.R

class ProfessorActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professor)

        val email = findViewById<TextView>(R.id.profEmail)
        val major = findViewById<TextView>(R.id.profMajor)
        val name = findViewById<TextView>(R.id.profName)
        val position = findViewById<TextView>(R.id.profPosition)
        val telNum = findViewById<TextView>(R.id.profTelNum)

        val profile = findViewById<TextView>(R.id.profileBtn)
        profile.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.profile_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("프로필 수정")

            val mAlertDialog = mBuilder.show()

            mAlertDialog.findViewById<Button>(R.id.noBtn)?.setOnClickListener {
                mAlertDialog.dismiss()
            }
            mAlertDialog.findViewById<Button>(R.id.profileOkBtn)?.setOnClickListener {
                val profileEmail = mAlertDialog.findViewById<EditText>(R.id.email)?.text.toString()
                val profileMajor = mAlertDialog.findViewById<EditText>(R.id.major)?.text.toString()
                val profileName = mAlertDialog.findViewById<EditText>(R.id.name)?.text.toString()
                val profilePositon = mAlertDialog.findViewById<EditText>(R.id.profilePosition)?.text.toString()
                val profileTelnum = mAlertDialog.findViewById<EditText>(R.id.telNum)?.text.toString()

                FBRef.InfoRef.child("profEmail").setValue(profileEmail)
                FBRef.InfoRef.child("profMajor").setValue(profileMajor)
                FBRef.InfoRef.child("profName").setValue(profileName)
                FBRef.InfoRef.child("profPosition").setValue(profilePositon)
                FBRef.InfoRef.child("profTelNum").setValue(profileTelnum)

                mAlertDialog.dismiss()
            }
        }
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