package com.example.spartacoding_1

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

// 회원가입시 SP에 User 객체를 저장 - 1 회원가입시 저장된 ID와 비교?
// 마이페이지 화면으로 User 객체 자체를 넘겨줌

class MyApplication: Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    //여기 안에서 Gson을 이용?
    fun setJson(data: User) {
        val userJson = gson.toJson(data)
        setUser("User", userJson)
    }

    fun getJson(): User? {
        val jsonUser = getUser("User"," ")
//        TypeToken을 적용해서 데이터를 복원하는 부분
//        json으로 SharedPreferences에 저장하는 순간 데이터가 가지고 있는 타입정보가 모두 사라지기 때문에 데이터를 복원하면서 타입정보를 다시 부여하기 위해 타입토큰을 사용
        val typeToken = object: TypeToken<User>() {}.type
        return gson.fromJson(jsonUser, typeToken)
    }

    private fun getUser(key: String, defValue: String): String {
        return prefs.getString(key,defValue).toString()
    }

    private fun setUser(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
}

data class User(
    val name: String,
    val age: Int,
    val id: String,
    val pw: String,
    val mbti: String
):Serializable