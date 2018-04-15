package com.example.anpu.circles

import android.animation.*
import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.transition.Slide
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import com.bumptech.glide.Glide
import com.example.anpu.circles.model.User
import com.example.anpu.circles.model.UserData
import com.example.anpu.circles.model.UserResponseStatus
import com.example.anpu.circles.utilities.JellyInterpolator
import com.example.anpu.circles.utilities.MD5Util
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.input_layout_login.*
import okhttp3.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import java.io.IOException
import java.util.*
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private var email : String? = ""
    private var pwd : String? = ""
    private val urlLogin = "http://steins.xin:8001/auth/login"
    private val urlForget = "http://steins.xin:8001/auth/forgetpwd"
    private var sprefLogin : SharedPreferences? = null
    private var editorLogin : SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)


        val bar = supportActionBar
        bar?.hide()

        val slide = Slide()
        slide.duration = 1000
        window.exitTransition = slide

        sprefLogin = PreferenceManager.getDefaultSharedPreferences(this)
        editorLogin = sprefLogin?.edit()
        editorLogin?.apply()

        Glide.with(applicationContext).load(R.drawable.paw_code).into(login_avatar)

        //listener
        edit_email_login.setTextChangeListener {
            email = it
        }

        edit_pwd_login.setTextChangeListener {
            pwd = it
        }

        main_btn_login.onClick {
            val emailLength = email?.length
            val pwdLength = pwd?.length

            if (emailLength == null || emailLength == 0) {
                toast("Email is empty")
            } else if (pwdLength == null || pwdLength == 0) {
                toast("Password is empty")
            } else {
                checkEmail()
            }
        }

        signup_textview.onClick {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            intent.putExtra("transition", "slide")
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@LoginActivity).toBundle())
        }

        forgetpwd_textview.onClick {
            val emailLength = email?.length
            if (emailLength != null && emailLength > 0) {
                val gsonForget = GsonBuilder().enableComplexMapKeySerialization().create()
                val forget = HashMap<String, String>()
                forget.put("email", email!!)
                val client = OkHttpClient()
                val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gsonForget.toJson(forget))
                val request = Request.Builder()
                        .post(body)
                        .url(urlForget)
                        .build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread { toast("Failure to connect to the server") }
                    }
                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        val resp = GsonBuilder().enableComplexMapKeySerialization().create()
                        val type = object : TypeToken<HashMap<String, String>>() {}.type
                        val resHash = resp.fromJson<HashMap<String, String>>(response.body().string(), type)
                        if (resHash["status"] == "1") {
                            runOnUiThread { toast("An email has sent to reset the password.")}
                        } else {
                            runOnUiThread { toast("Please check your email it doesn't seem to be right.")}
                        }
                    }
                })

            }
        }


    }

    private fun checkEmail() {
        val pattern = "@nyu.edu"
        val r = Pattern.compile(pattern)
        val m = r.matcher(email)
        if (!m.find()) {
            toast("Email should end with @nyu.edu")
        } else {
            val mWidth = main_btn_login.measuredWidth.toFloat()
            val mHeight = main_btn_login.measuredHeight.toFloat()

            login_layout_email.visibility = View.INVISIBLE
            login_layout_pwd.visibility = View.INVISIBLE

            inputAnimator(input_layout_login, mWidth, mHeight)
        }
    }

    private fun inputAnimator(view: View, w: Float, h: Float) {

        val set = AnimatorSet()

        val animator = ValueAnimator.ofFloat(0f, w)
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            params.leftMargin = value.toInt()
            params.rightMargin = value.toInt()
            view.layoutParams = params
        }

        val animator2 = ObjectAnimator.ofFloat(input_layout_login, "scaleX", 1f, 0.5f)
        set.duration = 500
        set.interpolator = AccelerateDecelerateInterpolator()
        set.playTogether(animator, animator2)
        set.start()
        set.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {

            }

            override fun onAnimationEnd(animator: Animator) {

                layout_progress_login.visibility = View.VISIBLE
                progressAnimator(layout_progress_login)
                input_layout_login.visibility = View.INVISIBLE

            }

            override fun onAnimationCancel(animator: Animator) {

            }

            override fun onAnimationRepeat(animator: Animator) {

            }
        })
    }

    private fun recovery() {
        layout_progress_login.visibility = View.GONE
        input_layout_login.visibility = View.VISIBLE
        login_layout_email.visibility = View.VISIBLE
        login_layout_pwd.visibility = View.VISIBLE

        val params = input_layout_login.layoutParams as ViewGroup.MarginLayoutParams
        params.leftMargin = 0
        params.rightMargin = 0
        input_layout_login.layoutParams = params

        val animator2 = ObjectAnimator.ofFloat(input_layout_login, "scaleX", 0.5f, 1f)
        animator2.duration = 500
        animator2.interpolator = AccelerateDecelerateInterpolator()
        animator2.start()
    }

    private fun progressAnimator(view: View) {
        val animator = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f)
        val animator2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f)
        val animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2)
        animator3.duration = 1000
        animator3.interpolator = JellyInterpolator()
        animator3.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {

            }

            override fun onAnimationEnd(animator: Animator) {
                validLogin()
            }

            override fun onAnimationCancel(animator: Animator) {

            }

            override fun onAnimationRepeat(animator: Animator) {

            }
        })
        animator3.start()
    }

    private fun validLogin() {
        val gson = Gson()
        val user = User(email, MD5Util.getMD5Str(pwd))
        val jsonUser = gson.toJson(user)
        // post to the server
        val client = OkHttpClient()
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonUser)
        val request = Request.Builder()
                .post(body)
                .url(urlLogin)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    toast("Failure to connect to the server")
                    recovery()
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                val userResponseStatus = gson.fromJson(response.body().string(), UserResponseStatus::class.java)
                // failure
                if (userResponseStatus.status == 0) {
                    if (userResponseStatus.type == 0) {
                        runOnUiThread {
                            toast("Account is not activated.")
                            recovery()
                        }
                    } else {
                        runOnUiThread {
                            toast("Account does not exist.")
                            recovery()
                        }
                    }
                } else {
                    UserData.setEmail(MD5Util.getMD5(email))
                    UserData.setNickname(userResponseStatus.nickname)
                    UserData.setAvatar(userResponseStatus.avatar)
                    UserData.setUncypheredEmail(email)

                    editorLogin?.putString("email", email)
                    editorLogin?.commit()

                    val intent = Intent(this@LoginActivity, HomePageFragmentActivity::class.java)
                    startActivity(intent)
                    this@LoginActivity.finish()
                }
            }
        })
    }


    private fun EditText.setTextChangeListener(body: (key: String) -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                body(s.toString())
            }
        })
    }
}