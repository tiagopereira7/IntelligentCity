package com.example.intelligentcity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddNote : AppCompatActivity() {

    private lateinit var editTitle: EditText
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        editTitle = findViewById(R.id.title)
        editText = findViewById(R.id.text)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTitle.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
                 if (TextUtils.isEmpty(editText.text)) {
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                }
            } else {

                replyIntent.putExtra(EXTRA_REPLY_TITLE, editTitle.text.toString())
                replyIntent.putExtra(EXTRA_REPLY_TEXT, editText.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
    companion object {
        const val EXTRA_REPLY_TITLE = "com.example.android.title"
        const val EXTRA_REPLY_TEXT = "com.example.android.text"
    }
}