package com.learn.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.learn.foodrunner.R

class ForgotPassword : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val etPhone2 : EditText = findViewById(R.id.etPhone2)
        val etEmail2 : EditText = findViewById(R.id.etEmailAddress2)
        val buttonForgot : Button = findViewById(R.id.btnForgot)

        buttonForgot.setOnClickListener {
            val intent = Intent(this , MainSpot::class.java)
            val bundle = Bundle()

            bundle.putString("data" , "forgot")
            bundle.putString("phone" , etPhone2.text.toString())
            bundle.putString("email" , etEmail2.text.toString())

            intent.putExtra("details" , bundle)
            startActivity(intent)
        }
    }
}