package com.example.fanfics.utils

object Constants {

    // Endpoints
    const val BASE_URL = "http://192.168.1.7:3000/"
    const val LOGIN_URL = "api/v1/auth/login"
    const val REGISTR_URL = "api/v1/auth/sign_up"
    const val TEST_URL = "api/v1/test"

    const val USERS_URL = "api/v1/users"
    const val USER_FANFIC_URL = "$USERS_URL/{id}/fanfic"
}