package com.example.intelligentcity

import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intelligentcity.api.EndPoints
import com.example.intelligentcity.api.LoginRequest
import com.example.intelligentcity.api.OutputPost
import com.example.intelligentcity.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var editTextEmail: EditText
private lateinit var editTextPassword: EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)


    }


    fun registar(view: View) {
        val intent = Intent(this, RegistarActivity::class.java)
        startActivity(intent)
    }

    fun login(view: View) {

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (email.isEmpty()||password.isEmpty()){
            editTextEmail.error = "-Os campos estao vazios"
        } else if (email.isEmpty()){
            editTextEmail.error = "Tem de preencher o campo Email"
        }else if(password.isEmpty()){
            editTextPassword.error = "Tem de preencher o campo password"
        }else{

            val user = LoginRequest(email, password)
            val call = request.UserLogin(user)

            call.enqueue(object : Callback<OutputPost> {

                override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@MainActivity, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show()

                    }
                }
                override fun onFailure(call:Call<OutputPost>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "ERRO NO LOGIN", Toast.LENGTH_SHORT).show()
                    Log.d("Erro", t.toString())
                }
            })
        }


        //val intent = Intent(this, MapActivity::class.java)
        //startActivity(intent)
    }

    fun consultar(view: View) {
        val intent = Intent(this,SecondActivity::class.java)
        startActivity(intent)
    }
}