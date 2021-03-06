package com.example.intelligentcity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    fun registar(view: View) {
        val intent = Intent(this, RegistarActivity::class.java)
        startActivity(intent)
    }

    fun login(view: View) {

        val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
        val password = findViewById<EditText>(R.id.editTextPassword).text.toString()
        Log.d("username", email)
        Log.d("password", password)


        if(email.isEmpty() && password.isEmpty()){
            Toast.makeText(this@MainActivity, getString(R.string.toast_vazio), Toast.LENGTH_SHORT).show()

        }else if (email.isEmpty()){
            findViewById<EditText>(R.id.editTextEmail).error = getString(R.string.erro_email)

        }else if(password.isEmpty()){
            findViewById<EditText>(R.id.editTextPassword).error = getString(R.string.erro_pass)

        }else{
            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val loginRequest = LoginRequest(email , password)
            val call = request.userLogin(loginRequest)

            call.enqueue(object : Callback<OutputPost> {

                override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@MainActivity, getString(R.string.login_sucess), Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@MainActivity, MapActivity::class.java)

                        intent.putExtra("id", response.body()?.id)
                        intent.putExtra("Email", response.body()?.Email)

                        startActivity(intent)
                    }
                }
                override fun onFailure(call:Call<OutputPost>, t: Throwable) {
                    Toast.makeText(this@MainActivity, getString(R.string.login_erro), Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    fun consultar(view: View) {
        val intent = Intent(this,SecondActivity::class.java)
        startActivity(intent)
    }
}