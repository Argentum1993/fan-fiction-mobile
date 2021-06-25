package com.example.fanfics.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.fanfics.App
import com.example.fanfics.R
import com.example.fanfics.data.ApiService
import com.example.fanfics.data.requests.RegistrationRequest
import com.example.fanfics.data.response.RegistrationResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RegistrationActivity: AppCompatActivity() {
    @Inject lateinit var apiService: ApiService
    private lateinit var dialog: AlertDialog.Builder

    private lateinit var email: EditText
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var registr: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(activity = this@RegistrationActivity)
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
        apiService.registration(RegistrationRequest(
            username = username.text.toString(),
            email = email.text.toString(),
            password = password.text.toString()
        )).enqueue(object : Callback<RegistrationResponse> {
                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    Log.i("REG", "auth error can't connect to server")
                    Log.i("REG", t.toString())
                    dialog.setMessage("Connection problems.").create().show()
                }

                override fun onResponse(
                    call: Call<RegistrationResponse>,
                    response: Response<RegistrationResponse>
                ) {
                    val registerResponse = response.body()
                    var gson = Gson()
                    var msg: String
                    if (response.code() == 200 && registerResponse != null) {
                        msg = registerResponse.username + " registration success!"
                        dialog.setPositiveButton("ok", ) { _,_ -> redirectLogin(view)}
                    } else {
                        msg = "Registration failure!"
                        if (response.errorBody() != null &&
                            !response.errorBody()?.string().isNullOrEmpty())
                            msg += "\n" + response.errorBody()?.string()
                        Log.i("REG", "auth error can't read response")
                    }
                    dialog.setMessage(msg).create().show()
                    Log.i("REG", gson.toJson(registerResponse))
                }
        })
    }
}