package com.example.fanfics.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.lifecycleScope
import com.example.fanfics.App
import com.example.fanfics.MainActivity
import com.example.fanfics.R
import com.example.fanfics.data.AuthService
import com.example.fanfics.data.requests.LoginRequest
import com.example.fanfics.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var loginProgressDialog: LoginProgressDialog
    @Inject lateinit var authService: AuthService

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var registration: Button


    override fun onCreate(savedInstanceState: Bundle?) {

        App.instance.getAuthComponent().inject(activity = this@LoginActivity)
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(this)

        setContentView(R.layout.activity_login_placeholder)
        lifecycleScope.launch {
            if (sessionManager.authenticate()) {
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
        lifecycleScope.launch {
            try {
                val response = authService.login(
                        LoginRequest(
                                email = email.text.toString(),
                                password = password.text.toString()
                        )
                )
                sessionManager.login(response.accessToken, response.id)
                if (sessionManager.authenticate())
                    redirectMain()
                else
                    sessionManager.logout()
            } catch (e: IOException){
                e.message?.let { Log.i("login", it) }
                Toast.makeText(this@LoginActivity, "Can't connect.", LENGTH_SHORT)
                        .show()
                sessionManager.logout()
            } catch (e: HttpException){
                var msg = ""
                if (!e.response()?.errorBody()?.string().isNullOrEmpty())
                    msg = "\n" + e.response()?.errorBody()?.string()
                Toast.makeText(this@LoginActivity, "Can't connect.$msg", LENGTH_SHORT)
                        .show()
                Log.i("login", e.message())
                sessionManager.logout()
            }
            loginProgressDialog.dismiss()
        }
    }
}