package com.example.fanfics.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.fanfics.App
import com.example.fanfics.R
import com.example.fanfics.data.ApiService
import com.example.fanfics.data.AuthService
import com.example.fanfics.data.models.User
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import retrofit2.HttpException
import java.io.IOException

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_ID  = "user_id"
    }

    fun login(token: String, userId: Long){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putLong(USER_ID, userId)
        editor.apply()
        setTokenToInterceptor(token)
    }

    suspend fun authenticate(): Boolean {
        var token: String? = fetchAuthToken()
        var user: User? = null

        if (token != null){
            setTokenToInterceptor(token)
            App.token = token
            user = getUser(App.appComponent.getApiService())
            if (user == null) {
                logout()
            } else
                App.setUser(user)
        }
        return user != null
    }

    fun logout(){
        val editor = prefs.edit()
        editor.remove(USER_TOKEN)
        editor.remove(USER_ID)
        editor.apply()
        App.setUser(null)
        setTokenToInterceptor(null)
    }

    private fun setTokenToInterceptor(token: String?){
        val interceptor = App.appComponent.getAuthInterceptor()

        if (token == null)
            interceptor.setAccessToken(null)
        else
           interceptor.setAccessToken("Bearer $token")
    }

    private suspend fun getUser(apiService: ApiService): User?{
        val userId: Long? = fetchUserId()
        var user: User? = null

        if (userId != null) {
            try {
                user = apiService.getUser(userId)
            } catch (e: IOException){
                return null
            } catch (e: HttpException){
                return null
            }
        }
        return user
    }

    private fun fetchUserId(): Long?{
        val id = prefs.getLong(USER_ID, -1)
        return if (id == -1L) null else id
    }

    private fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}