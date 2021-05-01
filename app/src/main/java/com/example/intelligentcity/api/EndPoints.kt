package com.example.intelligentcity.api

import retrofit2.http.*
import retrofit2.Call


interface EndPoints {

    @POST("api/loginUser/")
    fun UserLogin(@Body loginRequest: LoginRequest):Call<OutputPost>
}