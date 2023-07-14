package com.mareq.ispitmealapp.presentation.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mareq.ispitmealapp.R
import com.mareq.ispitmealapp.presentation.contract.UserContract
import com.mareq.ispitmealapp.presentation.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var editUsernameText : EditText
    private lateinit var editPasswordText : EditText
    private lateinit var loginButton : Button

    private val userViewModel : UserContract.UserViewModel by viewModel<UserViewModel>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        init()
    }

    private fun init(){
        initView()
        initObservers()
        initListeners()
    }

    private fun initView(){
        editUsernameText = findViewById(R.id.editUsernameTextView)
        editPasswordText = findViewById(R.id.editPasswordTextView)
        loginButton = findViewById(R.id.loginButton)
    }

    private fun initListeners(){
        loginButton.setOnClickListener {
            val username = editUsernameText.text.toString()
            val password = editPasswordText.text.toString()

            if(checkPasswordLength(password)){
                getUserByLogin(username, password)
            }else{
                Toast.makeText(this, "Short password", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkPasswordLength(password : String?) : Boolean{
        return (password?.length ?: 0) > 4
    }

    private fun getUserByLogin(username : String?, password: String?) = with(userViewModel) {
        userViewModel.getById(username!!, password!!)
    }

    private fun initObservers() = with(userViewModel) {
        fetchedUser.observe(this@LoginActivity) { user ->
            val editor = sharedPreferences.edit()
            editor?.putString("userID", user.username)
            editor?.apply()

            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}