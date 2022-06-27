package com.example.navi_assignment.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/repos/nayabanwar150/{repo}/pulls")
    suspend fun getClosedPullList(@Path("repo") repo: String) :

}