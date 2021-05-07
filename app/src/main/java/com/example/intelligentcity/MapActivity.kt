package com.example.intelligentcity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intelligentcity.api.EndPoints
import com.example.intelligentcity.api.ReportOutPutPost
import com.example.intelligentcity.api.ReportRequest
import com.example.intelligentcity.api.ServiceBuilder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MapActivity : AppCompatActivity(), OnMapReadyCallback, OnMapClickListener, OnMapLongClickListener {

    private lateinit var mMap: GoogleMap
    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        id = intent.getStringExtra("id")

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val home = LatLng(41.554329, -8.351042)
        mMap.addMarker(MarkerOptions().position(home).title("Marker in my home"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 12F))

        googleMap.setOnMapClickListener(this)
        googleMap.setOnMapLongClickListener(this)


    }


    override fun onBackPressed() {
        moveTaskToBack(false)
    }

    override fun onMapClick(latLng: LatLng) {
        Toast.makeText(this, "Latitude: " + latLng.latitude.toString() + " longitude: " + latLng.longitude.toString(),
                Toast.LENGTH_SHORT).show()
    }

    override fun onMapLongClick(latLng: LatLng) {

        mMap.addMarker(MarkerOptions().position(latLng))
        val l = Location("")
        l.latitude = latLng.latitude
        l.longitude = latLng.longitude

        val i = Intent(this@MapActivity, NotasActivity::class.java)
        i.putExtra("latitude", latLng.latitude.toString())
        i.putExtra("longitude", latLng.longitude.toString())
        i.putExtra("id", id)
        startActivity(i)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_ativ, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.opcao1 -> {
                Toast.makeText(this@MapActivity, getString(R.string.logout), Toast.LENGTH_SHORT).show()
                val i = Intent(this@MapActivity, MainActivity::class.java)
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {

        super.onResume()
        getMarker()
    }

    fun getMarker() {



        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReports()
        call.enqueue(object : Callback<List<ReportRequest>>{

            override fun onResponse(call: Call<List<ReportRequest>>, response: Response<List<ReportRequest>>) {
                if (response.isSuccessful) {
                    var reports = response.body()
                    reports?.forEach{
                        val position = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                        val url = "http://intelligentcity.000webhostapp.com/myslim/report_photos/" + it.fotografia
                        Picasso.get().load(url)
                         mMap.addMarker(
                                MarkerOptions()
                                        .position(position).title(it.titulo + " - " + it.data + " - " + Picasso.get().load(url))
                        )
                    }

                }
            }

            override fun onFailure(call: Call<List<ReportRequest>>, t: Throwable) {
                Toast.makeText(this@MapActivity, getString(R.string.report_erro), Toast.LENGTH_SHORT).show()
                Log.d("XXX", t.toString())
            }
        })


    }


}

