package com.learn.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.learn.foodrunner.R

class Login : AppCompatActivity() {

    lateinit var etMobileNumber : EditText
    lateinit var etPassword : EditText
    lateinit var btnLogin : Button
    lateinit var txtForgotPassword : TextView
    lateinit var txtRegister : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        txtRegister = findViewById(R.id.txtRegister)

        btnLogin.setOnClickListener {
                val intent = Intent(this, MainSpot::class.java)
                val bundle = Bundle()

                bundle.putString("data" , "login")
                bundle.putString("number" , etMobileNumber.text.toString())
                bundle.putString("password" , etPassword.text.toString())

                intent.putExtra("details" , bundle)
                startActivity(intent)
        }
        txtForgotPassword.setOnClickListener {
            val intent2 = Intent(this , ForgotPassword::class.java)
            startActivity(intent2)
        }
        txtRegister.setOnClickListener {
            val intent3 = Intent(this , Registration::class.java)
            startActivity(intent3)
        }
    }

//    override fun onPause() {
//        super.onPause()
//        finish()
//    }

}

