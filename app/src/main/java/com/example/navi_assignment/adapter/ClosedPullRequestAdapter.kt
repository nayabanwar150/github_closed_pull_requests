package com.example.navi_assignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navi_assignment.R
import com.example.navi_assignment.model.PullRequestModel
import java.text.SimpleDateFormat

class ClosedPullRequestAdapter(private val list: PullRequestModel, private val context: Context) :
    RecyclerView.Adapter<ClosedPullRequestAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.users_cards, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val data = list[position]


        holder.userName.text = data.user.login
        holder.pullRequest.text = data.title
        holder.createdOn.text = getFormattedDate(data.created_at.toString())
        holder.closedOn.text = getFormattedDate(data.closed_at.toString())
        Glide.with(context)
            .load(data.user.avatar_url)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun getFormattedDate(date: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val displaySdf = SimpleDateFormat("dd MMM yyyy")
        val temp = sdf.parse(date.substring(0, 10))
        return displaySdf.format(temp)
    }


    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userName: TextView = view.findViewById(R.id.user_name)
        val pullRequest: TextView = view.findViewById(R.id.pull_request_title)
        val createdOn: TextView = view.findViewById(R.id.created_on)
        val closedOn: TextView = view.findViewById(R.id.closed_on)
        val imageView: ImageView = view.findViewById(R.id.user_profile_pic)
    }
}