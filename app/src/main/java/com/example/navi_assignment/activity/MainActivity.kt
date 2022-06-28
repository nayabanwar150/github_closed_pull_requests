package com.example.navi_assignment.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navi_assignment.R
import com.example.navi_assignment.adapter.ClosedPullRequestAdapter
import com.example.navi_assignment.databinding.ActivityMainBinding
import com.example.navi_assignment.model.PullRequestModel
import com.example.navi_assignment.network.Result
import com.example.navi_assignment.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var adapter: ClosedPullRequestAdapter? = null
    private lateinit var data: PullRequestModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getClosedPullRequestData("Sample_Repo")
        viewModel.getAllRepos()

        viewModel.repoList.observe(this){
            when{
                (it is Result.Success && it.data?.size != 0) ->{
                    val reposOptions = ArrayList<String>()
                    for (item in it.data!!){
                        reposOptions.add(item.name)
                    }
                    val dropDownAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, reposOptions)
                    binding.reposDropdown.adapter = dropDownAdapter

                    binding.reposDropdown.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            Toast.makeText(this@MainActivity,
                                        "Selected ---> " + reposOptions[p2], Toast.LENGTH_SHORT).show()
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }
                }
             }
        }

        viewModel.closedPullRequestData.observe(this){
            when {
                (it is Result.Success && it.data?.size != 0) -> {
                    Log.i("Response -> ", "${it.data?.get(0)?.user?.login}")
                    if(adapter == null){
                        data = it.data!!
                        adapter = ClosedPullRequestAdapter(data,applicationContext)
                        binding.rcUsersList.layoutManager = LinearLayoutManager(this@MainActivity)
                        binding.rcUsersList.adapter = adapter
                    }else{
                        data = it.data!!
                        adapter?.notifyDataSetChanged()
                    }
                }
                it is Result.ErrMsg -> {
                    Toast.makeText(applicationContext, it.errMsg, Toast.LENGTH_SHORT).show()
                    Log.i("Response -> ", "${it.errMsg}")
                }
                else -> {
                    Toast.makeText(applicationContext, "Error Loading !", Toast.LENGTH_SHORT).show()
                    Log.i("Response -> ", "Error")
                }
            }
        }
    }


}