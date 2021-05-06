package com.example.intelligentcity

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.intelligentcity.api.EndPoints
import com.example.intelligentcity.api.ReportOutPutPost
import com.example.intelligentcity.api.ServiceBuilder
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

class NotasActivity : AppCompatActivity() {

    private lateinit var editcoordenadas: EditText
    private lateinit var imageView: ImageView
    private lateinit var button_image: Button
    private lateinit var id: String

    var latitude = "0"
    var longitude = "0"
    var image_uri: Uri? = null

    val REQUEST_IMAGE_CAPTURE = 1

    private val PERMISSION_REQUEST_CODE: Int = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        editcoordenadas = findViewById(R.id.editcoordenadas)
        id = intent.getStringExtra("id")
        latitude = intent.getStringExtra("latitude")
        longitude = intent.getStringExtra("longitude")

        val coordinates = "Latitude: $latitude Longitude:$longitude"
        editcoordenadas.setText(coordinates)

        imageView = findViewById(R.id.imageView)
        button_image = findViewById(R.id.button_image)
        button_image.setOnClickListener(View.OnClickListener {
            if (checkPersmission()) takePicture() else requestPermission()
        })


    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePicture()
                } else {
                    Toast.makeText(this, "Permissão Bloqueada", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> {
                Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            imageView.setImageURI(image_uri)
        }
    }

    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA), PERMISSION_REQUEST_CODE)
    }

    private fun convertBitmapToFile(fileName: String, bitmap: Bitmap): File {
        //create a file to write bitmap data
        val file = File(this@NotasActivity.cacheDir, fileName)
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos)
        val bitMapData = bos.toByteArray()

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitMapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    fun guardar(view: View){

        var titulo = findViewById<EditText>(R.id.edittitulo)
        var descricao = findViewById<EditText>(R.id.editdescricao)
        var localizacao = findViewById<EditText>(R.id.editlocalizacao)

        if(!titulo.text.isEmpty()&&!descricao.text.isEmpty()&&!localizacao.text.isEmpty()){
            val imgBitmap: Bitmap = findViewById<ImageView>(R.id.imageView).drawable.toBitmap()
            val imageFile: File = convertBitmapToFile("file", imgBitmap)

            val imgFileRequest: RequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile)
            val foto: MultipartBody.Part = MultipartBody.Part.createFormData("file", imageFile.name, imgFileRequest)

            val utilizador_id: RequestBody = RequestBody.create(MediaType.parse("text/plain"), id)
            val titulo: RequestBody = RequestBody.create(MediaType.parse("text/plain"), titulo.text.toString())
            val descricao: RequestBody = RequestBody.create(MediaType.parse("text/plain"), descricao.text.toString())
            val localizacao: RequestBody = RequestBody.create(MediaType.parse("text/plain"), localizacao.text.toString())
            val latitude: RequestBody = RequestBody.create(MediaType.parse("text/plain"), latitude)
            val longitude: RequestBody = RequestBody.create(MediaType.parse("text/plain"), longitude)

            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.addReport(foto, utilizador_id, titulo, descricao, localizacao, latitude, longitude)
            call.enqueue(object : Callback<ReportOutPutPost> {

                override fun onResponse(call: Call<ReportOutPutPost>, response: Response<ReportOutPutPost>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@NotasActivity, getString(R.string.report_sucess), Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<ReportOutPutPost>, t: Throwable) {
                    Toast.makeText(this@NotasActivity, getString(R.string.report_erro), Toast.LENGTH_SHORT).show()
                    Log.d("XX", t.toString())
                }
            })
        }else{
            Toast.makeText(this@NotasActivity, getString(R.string.report_vazio), Toast.LENGTH_SHORT).show()
        }
    }

    fun backToMap(v: View?) {
        val i = Intent(this@NotasActivity, MapActivity::class.java)
        startActivity(i)
    }


}