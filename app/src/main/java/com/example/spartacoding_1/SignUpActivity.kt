package com.example.spartacoding_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener

class SignUpActivity : AppCompatActivity() {

    private var spinnerData = listOf<String>("MBTI를 선택해주세요", "ISTJ", "ISFJ", "INFJ", "INTJ", "ISTP", "ISFP", "INFP", "INTP", "ESTP", "ESFP", "ENFP", "ENTP", "ESTJ", "ESFJ", "ENFJ", "ENTJ")
    private var spinnerCheck = false
    private var editCheck = false

    private val editName: EditText by lazy {
        findViewById(R.id.et_name)
    }
    private val editNameError: TextView by lazy {
        findViewById(R.id.tv_name_error)
    }
    private val editAge: EditText by lazy {
        findViewById(R.id.et_age)
    }
    private val editAgeError: TextView by lazy {
        findViewById(R.id.tv_age_error)
    }
    private val editId: EditText by lazy {
        findViewById(R.id.et_id)
    }
    private val editIdError: TextView by lazy {
        findViewById(R.id.tv_id_error)
    }
    private val editPw: EditText by lazy {
        findViewById(R.id.et_pw)
    }
    private val editPwError: TextView by lazy {
        findViewById(R.id.tv_pw_error)
    }
    private val spinnerMbti: Spinner by lazy {
        findViewById(R.id.spinner_mbti)
    }
    private val btnJoin: Button by lazy {
        findViewById(R.id.btn_join)
    }

    private val editList
        get() =  listOf(
            editName,
            editAge,
            editId,
            editPw
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        spinnerInit()
        errorCheck()

        btnJoin.setOnClickListener {
            checkJoin()
            if (editCheck && spinnerCheck) {
                Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                // 로그인 화면으로는 두개만 전달
                intent.putExtra("id", editId.text.toString())
                intent.putExtra("pw", editPw.text.toString())
                // setResult에 Result코드와 데이터를 넣은 intent를 같이 전달해줘야한다.!
                setResult(RESULT_OK, intent)
                finish()
                val user = User(editName.text.toString(), editAge.text.toString().toInt(), editId.text.toString(), editPw.text.toString(), spinnerMbti.selectedItem.toString())
                MyApplication.prefs.setJson(user)
            } else Toast.makeText(this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun errorCheck() {
        editList.forEach { edit ->
            edit.addTextChangedListener {
                edit.serErrorMessage()
//                setButtonEnable()
            }

            edit.setOnFocusChangeListener { _, focus ->
                if (focus.not()) {
                    // 포커스를 잃었을 때?
                    edit.serErrorMessage()
//                    setButtonEnable()
                }
            }
        }
    }

    // EditText에 사용가능한 확장함수
    private fun EditText.serErrorMessage() {
        when(this) {
            editName -> editNameError.text = checkValidName()
            editAge -> editAgeError.text = checkValidAge()
            editId -> editIdError.text = checkValidId()
            editPw -> editPwError.text = checkValidPw()
        }
    }

    private fun checkValidName(): String = if (editName.text.toString().isBlank()) "이름을 입력해주세요"
        else ""

    private fun checkValidAge(): String = if (editAge.text.toString().isBlank()) "나이를 입력해주세요"
        else ""

    private fun checkValidId(): String {
        val text = editId.text.toString()
        return when {
            text.isBlank() -> "아이디를 입력해주세요"
            text.contains("@") -> "@를 포함할 수 없습니다"
            text.length < 6 -> "아이디는 6자리 이상 입력해주세요"
            else -> ""
        }
    }

    private fun checkValidPw(): String {
        val text = editPw.text.toString()
        // 특수문자가 포함되어 있는 정규표현식
        val specialCharacterRegex = Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+")
        // 대문자가 포함된 정규식
        val upperCaseRegex = Regex("[A-Z]")
        return when {
            text.isBlank() -> "비밀번호를 입력해주세요"
            text.length < 10 -> "비밀번호 10자리 이상 입력해주세요"
            specialCharacterRegex.containsMatchIn(text).not() -> "특수문자를 포함시켜야합니다"
            upperCaseRegex.containsMatchIn(text).not() -> "대문자를 포함시켜야합니다"
            else -> ""
        }
    }

    // 버튼 활성화 함수
    private fun setButtonEnable() {
        btnJoin.isEnabled = checkValidName().isBlank()
                && checkValidAge().isBlank()
                && checkValidId().isBlank()
                && checkValidPw().isBlank()
    }


    private fun checkJoin() {
        // TODO: 여기서 아이디 중복검사 
        editCheck = !(editName.text.isNullOrEmpty() || editId.text.isNullOrEmpty() || editPw.text.isNullOrEmpty())
    }

    private fun spinnerInit() {
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinnerData)
        spinnerMbti.adapter = adapter
        spinnerMbti.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                spinnerCheck = position != 0
            }
            // override 함수중에 쓰지 않는 함수를 Unit으로 지정
            override fun onNothingSelected(p0: AdapterView<*>?) = Unit
        }
    }
}