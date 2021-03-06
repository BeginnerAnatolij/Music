package com.omisoft.myapplication.mvvm.ui.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.omisoft.myapplication.MainActivity
import com.omisoft.myapplication.R
import com.omisoft.myapplication.mvvm.ui.auth.AuthViewModel
import com.omisoft.myapplication.mvvm.ui.countries.fragment.CountriesFragment

class AuthFragment : Fragment() {

    private lateinit var progress: ProgressBar
    private lateinit var overlay: FrameLayout
    private lateinit var viewModel: AuthViewModel
    private lateinit var loginField: TextInputLayout
    private lateinit var passwordField: TextInputLayout
    private lateinit var nameField: TextInputLayout
    private lateinit var conifirmPassField: TextInputLayout
    private var titleText: AppCompatTextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_auth_mvvm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        val buttonLogin: AppCompatButton = view.findViewById(R.id.button_login)
        loginField = view.findViewById(R.id.input_layout_login)
        passwordField = view.findViewById(R.id.input_layout_password)
        nameField =  view.findViewById(R.id.input_layout_name)
        conifirmPassField =  view.findViewById(R.id.input_layout_confirmpassword)
        overlay = view.findViewById(R.id.overlay_container)
        progress = view.findViewById(R.id.progress)
        titleText = view.findViewById(R.id.title_text)

        restoreValues()

        nameField.editText?.addTextChangedListener {
            viewModel.nameLiveData.value = it.toString()
        }
        loginField.editText?.addTextChangedListener {
            viewModel.emailLiveData.value = it.toString()
        }
        passwordField.editText?.addTextChangedListener {
            viewModel.passwordLiveData.value = it.toString()
        }
        conifirmPassField.editText?.addTextChangedListener {
            viewModel.confirmPass.value = it.toString()
        }

        buttonLogin.setOnClickListener {
            val emailText = loginField.editText?.text.toString()
            val passwordText = passwordField.editText?.text.toString()
            val nameText = nameField.editText?.text.toString()
            val conifirmPassText = conifirmPassField.editText?.text.toString()
            viewModel.onLoginClicked(nameText, emailText, passwordText, conifirmPassText)
        }

        subscribeOnLiveData()
    }

    private fun restoreValues() {
        loginField.editText?.setText(viewModel.emailLiveData.value ?: "")
        passwordField.editText?.setText(viewModel.passwordLiveData.value ?: "")
    }

    private fun subscribeOnLiveData() {
        viewModel.isLoginSuccessLiveData.observe(viewLifecycleOwner, {
            (activity as MainActivity).openFragment(CountriesFragment())
        })
        viewModel.isLoginFailedLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, "Something went wrong. Please, retry!", Toast.LENGTH_LONG).show()
        })
        viewModel.showProgressLiveData.observe(viewLifecycleOwner, {
            showProgress()
        })
        viewModel.hideProgressLiveData.observe(viewLifecycleOwner, {
            hideProgress()
        })
        viewModel.titleLiveData.observe(viewLifecycleOwner, { title ->
            titleText?.text = title
        })
    }

    private fun hideProgress() {
        progress.isVisible = false
        overlay.isVisible = false
    }

    private fun showProgress() {
        progress.isVisible = true
        overlay.isVisible = true
    }
}