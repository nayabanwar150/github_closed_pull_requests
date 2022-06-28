package com.example.navi_assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.navi_assignment.model.PullRequestModel
import com.example.navi_assignment.model.RepoModel
import com.example.navi_assignment.repository.MainRepo
import com.example.navi_assignment.network.Result

class MainViewModel:ViewModel() {
    private val repo = MainRepo()

    val repoList: LiveData<Result<RepoModel>>
    get() = repo.repoList

    val closedPullRequestData : LiveData<Result<PullRequestModel>>
    get() = repo.closedPullRequestData

    fun getAllRepos() = repo.getAllRepos()
    fun getClosedPullRequestData(repoName:String) = repo.getClosedPullRequestData(repoName)

}