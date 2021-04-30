package com.example.intelligentcity.api

import android.provider.ContactsContract
import android.provider.SearchRecentSuggestions
import retrofit2.http.*
import retrofit2.Call


interface EndPoints {

    @POST("/createUser/")
    fun CreateUser(
            @Field("nome") nome:String,
            @Field("email") email:String,
            @Field("password") password:String

    ):Call<OutputPost>


    @POST("/userLogin/")
    fun UserLogin(
            @Field("email") email:String,
            @Field("password") password:String

    )
}