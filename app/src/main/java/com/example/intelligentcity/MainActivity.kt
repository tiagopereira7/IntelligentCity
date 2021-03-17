package com.example.intelligentcity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun registar(view: View) {
        val intent = Intent(this, RegistarActivity::class.java)
        startActivity(intent)
    }

    fun login(view: View) {}

    fun consultar(view: View) {
        val intent = Intent(this,SecondActivity::class.java)
        startActivity(intent)
    }
}