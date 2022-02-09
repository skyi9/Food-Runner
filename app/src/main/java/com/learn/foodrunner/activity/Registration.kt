package com.learn.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.learn.foodrunner.R

class Registration : AppCompatActivity() {

    lateinit var etPersonName : EditText
    lateinit var etEmailAddress : EditText
    lateinit var etPhone : EditText
    lateinit var etAddress : EditText
    lateinit var etPassword: EditText
    lateinit var etPassword2 : EditText
    lateinit var btnRegister : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        title = "Register Yourself"

        etPersonName = findViewById(R.id.etPersonName)
        etEmailAddress = findViewById(R.id.etEmailAddress)
        etPhone = findViewById(R.id.etPhone)
        etAddress = findViewById(R.id.etAddress)
        etPassword = findViewById(R.id.etPassword)
        etPassword2 = findViewById(R.id.etPassword2)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val intent = Intent(this , MainSpot::class.java)
            val bundle = Bundle()
            bundle.putString("data" , "register")
            bundle.putString("name" , etPersonName.text.toString())
            bundle.putString("email" , etEmailAddress.text.toString())
            bundle.putString("phone" , etPhone.text.toString())
            bundle.putString("address" , etAddress.text.toString())
            bundle.putString("password" , etPassword.text.toString())

            intent.putExtra("details" , bundle)
            startActivity(intent)

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}