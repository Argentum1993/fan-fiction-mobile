package com.example.fanfics.utils

object Constants {

    // Api main config
    const val           BASE_URL = "http://192.168.1.7:3000/"
    private const val   API_PREFIX = "api/v1"

    // Endpoints
    const val           LOGIN_URL = "${API_PREFIX}/auth/login"
    const val           REGISTR_URL = "${API_PREFIX}/auth/sign_up"
    const val           FANFIC_URL = "${API_PREFIX}/fanfic"

    const val           TEST_URL = "${API_PREFIX}/test"

    const val USERS_URL = "${API_PREFIX}/users"

    // Header
    const val AUTH_HEADER = "Authorization"
}