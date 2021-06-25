package com.example.fanfics.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.example.fanfics.App
import com.example.fanfics.MainActivity
import com.example.fanfics.R
import com.example.fanfics.data.ApiService
import com.example.fanfics.data.requests.LoginRequest
import com.example.fanfics.data.response.LoginResponse
import com.example.fanfics.utils.SessionManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var loginProgressDialog: LoginProgressDialog
    @Inject lateinit var apiService: ApiService

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var registration: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(activity = this@LoginActivity)

        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(this)
        if (sessionManager.authenticated()){
            setContentView(R.layout.activity_login_placeholder)
            redirectMain()
        } else {
            setContentView(R.layout.activity_login)
            loginProgressDialog = LoginProgressDialog()
            email = findViewById(R.id.loginEmail)
            password = findViewById(R.id.loginPassword)
            login = findViewById(R.id.buttonLogin)
            registration = findViewById(R.id.buttonRedirectToRegistr)
        }
    }

    fun redirectRegistration(view: View){
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun redirectMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun login(view: View){
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view.applicationWindowToken, 0)
        loginProgressDialog.show(supportFragmentManager, "Loading")
        apiService.login(LoginRequest(email = email.text.toString(), password = password.text.toString()))
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.i("TEST", "auth error can't connect to server")
                    Log.i("throw", t.toString())
                    loginProgressDialog.dismiss()
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()
                    var gson = Gson()
                    if (response.code() == 200 && loginResponse != null) {
                        sessionManager.saveAuthToken(loginResponse.accessToken)
                        redirectMain()
                    } else {
                        Log.i("TEST", "auth error can't read response")
                    }
                    Thread.sleep(5000)
                    loginProgressDialog.dismiss()
                    Log.i("DEBUG", gson.toJson(loginResponse))
                }
            })
    }
}