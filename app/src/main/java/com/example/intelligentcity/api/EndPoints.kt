package com.example.intelligentcity.api

import retrofit2.http.*
import retrofit2.Call


interface EndPoints {

    @POST("api/loginUser")
    fun userLogin(@Body loginRequest: LoginRequest): Call<OutputPost>
}