package com.example.navi_assignment.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.navi_assignment.model.PullRequestModel
import com.example.navi_assignment.model.RepoModel
import com.example.navi_assignment.network.ApiClient
import com.example.navi_assignment.network.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class MainRepo {
    private val service = ApiClient.getInstance()

    private val _closedPullRequestData = MutableLiveData<Result<PullRequestModel>>()
    private val _repoList = MutableLiveData<Result<RepoModel>>()

    val repoList: MutableLiveData<Result<RepoModel>> = _repoList
    val closedPullRequestData: MutableLiveData<Result<PullRequestModel>> = _closedPullRequestData


    fun getAllRepos(){
        CoroutineScope(Dispatchers.IO).async {
            try{
                var response = service?.getAllRepos()
                if(response != null){
                    _repoList.postValue(Result.Success(response.body()))
                }else{
                    _repoList.postValue(Result.ErrMsg(errMsg = "Oops! Unable to fetch Data."))
                }
            }catch (e: java.lang.Exception){
                _repoList.postValue(Result.ErrMsg(errMsg = e.message))
            }
        }
    }

    fun getClosedPullRequestData(repoName: String) {
        CoroutineScope(Dispatchers.IO).async {
            try {
                var response = service?.getClosedPullList(repoName)
                if (response != null) {
                    _closedPullRequestData.postValue(Result.Success(response.body()))
                }else{
                    _closedPullRequestData.postValue(Result.ErrMsg(errMsg = "Oops! Unable to fetch Data."))
                }
            } catch (e: Exception) {
                _closedPullRequestData.postValue(Result.ErrMsg(errMsg = e.message))
            }
        }
    }
}