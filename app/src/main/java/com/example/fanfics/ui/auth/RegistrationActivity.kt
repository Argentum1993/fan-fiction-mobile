package com.example.fanfics.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fanfics.App
import com.example.fanfics.R
import com.example.fanfics.data.ApiService
import com.example.fanfics.data.AuthService
import com.example.fanfics.data.requests.RegistrationRequest
import com.example.fanfics.data.response.RegistrationResponse
import com.example.fanfics.di.DaggerAuthComponent
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RegistrationActivity: AppCompatActivity() {
    @Inject lateinit var authService: AuthService
    private lateinit var dialog: AlertDialog.Builder

    private lateinit var email: EditText
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var registr: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.getAuthComponent().inject(activity = this@RegistrationActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        username =  findViewById(R.id.registrUsername)
        email = findViewById(R.id.registrEmail)
        password = findViewById(R.id.registrPassword)
        registr = findViewById(R.id.buttonRegistr)

        dialog = AlertDialog.Builder(this@RegistrationActivity)
        dialog.setPositiveButton("ok", ) { _,_ ->}
    }

    fun redirectLogin(view: View){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    
    fun onRegistration(view: View){
        lifecycleScope.launch {
            try {
                val response = authService.registration(
                        RegistrationRequest(
                                username = username.text.toString(),
                                email = email.text.toString(),
                                password = password.text.toString()
                        )
                )
                dialog.setMessage(response.username + " registration success!")
                dialog.setPositiveButton("ok", ) { _,_ -> redirectLogin(view)}
            } catch (e: IOException){
                e.message?.let { Log.i("onRegistration", it) }
                dialog.setMessage("Connection problems.")
            }catch (e: HttpException){
                var msg: String = "Registration failure!"
                if (!e.response()?.errorBody()?.string().isNullOrEmpty())
                    msg += "\n" +e.response()?.errorBody()?.string()
                dialog.setMessage(msg)
            }
            dialog.create().show()
        }
    }
}