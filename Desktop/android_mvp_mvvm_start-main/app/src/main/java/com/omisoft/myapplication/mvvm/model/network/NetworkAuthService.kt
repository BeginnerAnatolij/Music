package com.omisoft.myapplication.mvvm.model.network

interface NetworkAuthService {
    fun onLoginClicked(name: String,email: String, password: String, conifirmpassword: String): String?
    fun updateUserData(data: Any)
}