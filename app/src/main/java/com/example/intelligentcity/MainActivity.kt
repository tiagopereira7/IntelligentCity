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

    fun login(view: View) {}


    fun registar_Utilizador(view: View) {
        val newActivityIntent = Intent(this, RegistarActivity::class.java)
        startActivity(newActivityIntent)
    }


    fun consultar(view: View) {}
}