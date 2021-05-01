package com.example.intelligentcity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.intelligentcity.api.EndPoints
import com.example.intelligentcity.api.ServiceBuilder

private lateinit var botaoregistar: Button
private lateinit var editnome: EditText
private lateinit var editemail: EditText
private lateinit var editpassword: EditText


class RegistarActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registar)

        botaoregistar.setOnClickListener{

            val nome = editnome.text.toString().trim()
            val email = editemail.text.toString().trim()
            val password = editpassword.text.toString().trim()

            if(nome.isEmpty()){
                editnome.error =" Tem de preencher o nome"
                editnome.requestFocus()
                return@setOnClickListener
            }
            if(email.isEmpty()){
                editemail.error =" Tem de preencher o email"
                editemail.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                editpassword.error =" Tem de preencher a password"
                editpassword.requestFocus()
                return@setOnClickListener
            }

            ServiceBuilder.buildService(EndPoints::class.java)


        }


    }

fun registar(view: View) {}

}


