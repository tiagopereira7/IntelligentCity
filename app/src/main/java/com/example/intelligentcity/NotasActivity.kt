package com.example.intelligentcity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NotasActivity : AppCompatActivity() {

    private lateinit var editcoordenadas: EditText
    private lateinit var edittitulo: EditText
    private lateinit var editdescricao: EditText
    private lateinit var editlocalizacao: EditText
    var latitude = "0"
    var longitude = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)
        editcoordenadas = findViewById(R.id.editcoordenadas)
        edittitulo = findViewById<View>(R.id.edittitulo) as EditText
        editdescricao = findViewById<View>(R.id.editdescricao) as EditText
        editlocalizacao = findViewById<View>(R.id.editlocalizacao) as EditText
        latitude = intent.getStringExtra("latitude")
        longitude = intent.getStringExtra("longitude")

        val coordinates = "Latitude: $latitude Longitude:$longitude"
        editcoordenadas.setText(coordinates)


    }

    fun guardar(v: View?) {

        val titulo = edittitulo.text.toString()
        val descricao = editdescricao.text.toString()
        val localizacao = editlocalizacao.text.toString()
        val fotografia = editcoordenadas.text.toString() //corrigir para foto


        if (titulo.isEmpty()) {
            findViewById<EditText>(R.id.edittitulo).error = getString(R.string.erro_titulo)
        } else if (descricao.isEmpty()) {
            findViewById<EditText>(R.id.editdescricao).error = getString(R.string.erro_desc)
        } else if (localizacao.isEmpty()) {
            findViewById<EditText>(R.id.editlocalizacao).error = getString(R.string.erro_local)
        } else if (fotografia.isEmpty()) {
            findViewById<EditText>(R.id.editcoordenadas).error = "Tem de tirar uma fotografia!"  // corrigir para foto
        } else {


        }
    }

    fun backToMap(v: View?) {

        val i = Intent(this@NotasActivity, MapActivity::class.java)
        startActivity(i)
    }


}