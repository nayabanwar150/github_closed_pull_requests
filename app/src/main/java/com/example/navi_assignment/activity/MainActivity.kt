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
    private lateinit var closedPullRequestData: PullRequestModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getClosedPullRequestData("Sample_Repo")
        viewModel.getAllRepos()

        viewModel.repoList.observe(this){
            when{
                (it is Result.Success && it.data != null && it.data?.size != 0) ->{
                    val reposOptions = ArrayList<String>()
                    reposOptions.add("Sample_Repo")
                    for (item in it.data!!){
                        reposOptions.add(item.name)
                    }
                    val dropDownAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, reposOptions)
                    binding.reposDropdown.adapter = dropDownAdapter

                    dropDownAdapter.setDropDownViewResource(
                        android.R.layout
                            .simple_spinner_dropdown_item)

                    binding.reposDropdown.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
//                            viewModel.getClosedPullRequestData(reposOptions[p2])
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }

                    }
                }
             }
        }

        viewModel.closedPullRequestData.observe(this) {
            when {
                it is Result.Loading -> {
                    binding.mainProgressBar.visibility = View.VISIBLE
                    binding.noRecordFound.visibility = View.GONE
                }
                (it is Result.Success && it.data != null && it.data?.size != 0) -> {
                    Log.i("Response", "${it.data?.get(0)?.user?.login}")
                    if (adapter == null) {
                        Log.i("Adapter", "inside adapter")
                        closedPullRequestData = it.data!!
                        adapter = ClosedPullRequestAdapter(closedPullRequestData, this)
                        binding.rcUsersList.layoutManager = LinearLayoutManager(this@MainActivity)
                        binding.rcUsersList.adapter = adapter
                    } else {
                        closedPullRequestData = it.data!!
                        adapter?.notifyDataSetChanged()
                    }
                    binding.mainProgressBar.visibility = View.GONE
                    binding.noRecordFound.visibility = View.GONE
                }
                it is Result.ErrMsg -> {
                    Toast.makeText(this@MainActivity, it.errMsg, Toast.LENGTH_SHORT).show()
                    Log.i("Error", "${it.errMsg}")
                    binding.mainProgressBar.visibility = View.GONE
                    binding.noRecordFound.visibility = View.VISIBLE
                }
                else -> {
                    Toast.makeText(this@MainActivity, "No Records Found!", Toast.LENGTH_SHORT).show()
                    binding.mainProgressBar.visibility = View.GONE
                    binding.noRecordFound.visibility = View.VISIBLE
                }
            }
        }
    }


}