package com.example.intelligentcity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class AddNote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
    }

    fun guardar(view: View) {}
}