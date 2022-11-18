package com.kh.magic

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {
    companion object{
        private val database = Firebase.database

        val noticeRef = database.getReference("profBulletIn")
        val InfoRef = database.getReference("profInfo")
        val LectureRef = database.getReference("profLectureTimeTable")
    }
}
