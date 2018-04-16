package com.example.anpu.circles

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.example.anpu.circles.model.User
import com.example.anpu.circles.model.UserData
import com.example.anpu.circles.model.UserResponseStatus
import com.example.anpu.circles.utilities.MD5Util.getMD5
import com.google.gson.Gson
import okhttp3.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.io.IOException

class WelcomeActivity : AppCompatActivity() {

    private val checkSessionUrl = "http://steins.xin:8001/auth/checksession"
    private var sprefWelcome: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        sprefWelcome = PreferenceManager.getDefaultSharedPreferences(this)

        val email = sprefWelcome?.getString("email", "n/a")
        checkSession(email!!)
        this.finish()
    }

    fun checkSession(email : String) {
        val gson = Gson()
        val user = User(getMD5(email))
        val jsonUser = gson.toJson(user)

        val client = OkHttpClient()
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonUser)
        val request = Request.Builder()
                .post(body)
                .url(checkSessionUrl)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                toast("Failure to connect to the server")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val userResponseStatus = gson.fromJson(response.body().string(), UserResponseStatus::class.java)
                if (userResponseStatus.status == 0) {
                    startActivity<LoginActivity>()
                } else {
                    UserData.setEmail(getMD5(email))
                    UserData.setNickname(userResponseStatus.nickname)
                    UserData.setAvatar(userResponseStatus.avatar)
                    UserData.setUncypheredEmail(email)

                    startActivity<HomePageFragmentActivity>()

                }
            }
        })
    }
}