package com.example.navi_assignment.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var INSTANCE:ApiService? = null

    fun getInstance(): ApiService?{
        if(INSTANCE == null ) {
            INSTANCE = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
        return INSTANCE
    }
}