package com.example.intelligentcity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.intelligentcity.entities.Note
import com.example.intelligentcity.viewModel.NoteViewModel

class EditNote : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var editTitle2: EditText
    private lateinit var editText2: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        editTitle2 = findViewById(R.id.editTitle2)
        editText2 = findViewById(R.id.editText2)

        var id = 0
        var title3 = ""
        var text3 = ""


        id = intent.getIntExtra("id", 0)
        title3 = intent.getStringExtra("title").toString()
        text3 = intent.getStringExtra("text").toString()

        editTitle2.setText(title3)
        editText2.setText(text3)


        // view model
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)


        val button = findViewById<Button>(R.id.button_save2)
        button.setOnClickListener {
            Editar(id)
        }
        
        val button2 = findViewById<Button>(R.id.button_delete)
        button2.setOnClickListener{
            eliminar(id)
        }
    }


    fun Editar(id: Int) {

        editTitle2 = findViewById(R.id.editTitle2)
        editText2 = findViewById(R.id.editText2)

        val title = editTitle2.text.toString()
        val text = editText2.text.toString()

        if (title.isNotBlank()  && text.isNotBlank()) {
            noteViewModel.updateById(title, text, id)

            Toast.makeText(applicationContext, R.string.empty_edit, Toast.LENGTH_LONG).show()
            finish()

        } else {

        Toast.makeText(applicationContext, R.string.empty_not_edit, Toast.LENGTH_LONG).show()
        }

    }

    fun eliminar(id :Int){
        noteViewModel.deleteById(id)
        Toast.makeText(applicationContext, R.string.empty_delete, Toast.LENGTH_LONG).show()
        finish()
    }
}