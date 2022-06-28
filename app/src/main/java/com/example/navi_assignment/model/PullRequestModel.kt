package com.example.navi_assignment.model

class PullRequestModel : ArrayList<PullRequestDataItem>()

class PullRequestDataItem(val title:String, val created_at:String?, val closed_at:String?, val user: User)

class User(val login:String, val avatar_url:String)