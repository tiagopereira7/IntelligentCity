package com.example.intelligentcity.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import retrofit2.Call


interface EndPoints {

    @POST("api/loginUser")
    fun userLogin(@Body loginRequest: LoginRequest): Call<OutputPost>

    @Multipart
    @POST("api/reports/registoReport")
    fun addReport(
            @Part file: MultipartBody.Part,
            @Part("utilizador_id") id_user: RequestBody,
            @Part("titulo") titulo: RequestBody,
            @Part("descricao") descricao: RequestBody,
            @Part("localizacao") localizacao: RequestBody,
            @Part("latitude") latitude: RequestBody,
            @Part("longitude") longitude: RequestBody

    ) : Call<ReportOutPutPost>

    @GET("api/reports")
    fun getReports(): Call<List<ReportRequest>>

    @GET("/api/reports/{id}")
    fun getReportById(@Path("id") id:Int?): Call<ReportRequest>

}