package com.example.spartacoding_1

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {
    val images = arrayListOf<Int>(R.drawable.profile1, R.drawable.profile2, R.drawable.profile3, R.drawable.profile4, R.drawable.profile5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        val id = intent.getStringExtra("id")
//        val name = intent.getBooleanArrayExtra("name")
        val user = intent.getSerializableExtra("User") as User

        val ivProfile = findViewById<ImageView>(R.id.iv_profile)
        val tvId = findViewById<TextView>(R.id.tv_id)
        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvAge = findViewById<TextView>(R.id.tv_age)
        val tvMbti = findViewById<TextView>(R.id.tv_mbti)
        val btnFinish = findViewById<Button>(R.id.btn_finish)

        tvId.text = "아이디: ${user.id}"
        tvName.text = "이름: ${user.name}"
        tvAge.text = "나이: ${user.age}"
        tvMbti.text = "MBTI: ${user.mbti}"


        ivProfile.setImageResource(images[Random.nextInt(5)])

        btnFinish.setOnClickListener {
            finish()
        }
    }
}