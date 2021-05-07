package com.example.intelligentcity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.intelligentcity.api.EndPoints
import com.example.intelligentcity.api.ReportRequest
import com.example.intelligentcity.api.ServiceBuilder
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var id : String? = null

class OthersReports : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others_reports)

        id = intent.getStringExtra("id")
        Log.d("xxx" , id)
        getReport(id?.toInt())
    }

    private fun getReport(id : Int?){
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReportById(id)

        var report : ReportRequest? = null

        call.enqueue(object : Callback<ReportRequest> {

            override fun onResponse(call: Call<ReportRequest>, response: Response<ReportRequest>) {
                if (response.isSuccessful){
                    report=response.body()
                    findViewById<EditText>(R.id.title_mydialog).setText(report?.titulo)
                    findViewById<EditText>(R.id.edittext_mydialog).setText(report?.descricao)
                    val imageView : ImageView = findViewById(R.id.photo_mydialog)
                    Log.d("xxx", report?.fotografia.toString())
                    val url = "http://intelligentcity.000webhostapp.com/myslim/report_photos/"+ report?.fotografia
                    Picasso.get().load(url).into(imageView)
                    findViewById<EditText>(R.id.localizacao_mydialog).setText(report?.localizacao)
                    findViewById<EditText>(R.id.morada_mydialog).setText(report?.latitude + report?.longitude)
                }
            }

            override fun onFailure(call: Call<ReportRequest>, t: Throwable) {
                Toast.makeText(this@OthersReports, "Erro", Toast.LENGTH_SHORT).show()
                Log.d("xxx", t.toString())
            }
        })
    }
}