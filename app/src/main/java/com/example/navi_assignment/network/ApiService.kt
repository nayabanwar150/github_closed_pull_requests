package com.example.navi_assignment.network

import com.example.navi_assignment.model.PullRequestModel
import com.example.navi_assignment.model.RepoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://api.github.com/users/"

interface ApiService {

    @GET("/users/nayabanwar150/repos")
    suspend fun getAllRepos() : Response<RepoModel>

    @GET("/repos/nayabanwar150/{repo}/pulls?state=closed")
    suspend fun getClosedPullList(@Path("repo") repo: String) : Response<PullRequestModel>

}