package com.example.spartacoding_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class SignInActivity : AppCompatActivity() {
    // lateinit -> var 추후에 바뀔 수 있는 값
    // lazy -> val 바뀔 수 없는 값
    private val editId: EditText by lazy {
        findViewById(R.id.et_id)
    }
    private val editPw: EditText by lazy {
        findViewById(R.id.et_pw)
    }
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnJoin = findViewById<Button>(R.id.btn_join)

        getResultData()

        btnLogin.setOnClickListener {
            val check = checkLogin()
            if (check) {
                Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("User",user)
                startActivity(intent)
            } else Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }

        btnJoin.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun checkLogin(): Boolean {
        val editId = editId.text
        val editPw = editPw.text
        if (user == null) return false
        else {
            if (!editId.isNullOrEmpty() && !editPw.isNullOrEmpty()) {
                if (editId.toString() == (user?.id) && editPw.toString() == (user?.pw)) return true
            }
        }
        return false
    }

    private fun getResultData() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val id = result.data?.getStringExtra("id")
                val pw = result.data?.getStringExtra("pw")
                editId.setText("$id")
                editPw.setText("$pw")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // 여기서 sp에서 정보를 얻어와야겠다
        Log.d("SignInActivity: OnStart","OnStart")
        user = MyApplication.prefs.getJson()
        Log.d("Pref:", "$user")
    }
}



