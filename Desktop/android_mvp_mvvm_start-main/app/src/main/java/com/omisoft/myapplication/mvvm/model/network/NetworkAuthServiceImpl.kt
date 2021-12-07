package com.omisoft.myapplication.mvvm.model.network

class NetworkAuthServiceImpl : NetworkAuthService {
    override fun onLoginClicked(name: String,email: String, password: String, conifirmpassword: String): String? {
        val isEmailValid = email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordValid = password.isNotBlank() && password.length > 5
        val isNameValid = name.isNotBlank()
        val conifirmpasswordValid = conifirmpassword.isNotBlank() && conifirmpassword == password

        return if (isNameValid && isEmailValid && passwordValid && conifirmpasswordValid) {
            onLogin(name, email, password, conifirmpassword)
        } else {
            null
        }
    }

    override fun updateUserData(data: Any) {
        TODO("Not yet implemented")
    }

    fun removeUser() {
        TODO("Not yet implemented")
    }

    private fun onLogin(name: String,email: String, password: String, conifirmpassword: String): String {
        return "token"
    }
}