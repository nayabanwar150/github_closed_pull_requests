package com.example.navi_assignment.network

sealed class Result<T>(val data:T? = null, val errMsg: String? = null) {
    class Loading<T> : Result<T>()
    class Success<T>(data: T? = null) : Result<T>(data = data)
    class ErrMsg<T>(errMsg: String? = null) : Result<T>(errMsg = errMsg)
}