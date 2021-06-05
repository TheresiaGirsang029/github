package com.dicoding.github.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.github.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var rvUser: RecyclerView
    private lateinit var listUserAdapter: AdapterUser
    private lateinit var showProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showProgressBar = findViewById(R.id.progressbar)
        rvUser = findViewById(R.id.rlv_user)
        showRecyclerList()
    }

    private fun showRecyclerList(){
        rvUser.layoutManager = LinearLayoutManager(this)
        listUserAdapter = AdapterUser()
        rvUser.adapter = listUserAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                showProgressBar.visibility = View.VISIBLE
                onSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun onSearch(query: String) {
        Retrofit.userRepositories().getSearchUsers(query).enqueue(object: Callback<DataUserAPI> {
            override fun onResponse(call: Call<DataUserAPI>, response: Response<DataUserAPI>) {
                val dataResponse = response.body()
                showProgressBar.visibility = View.GONE
                listUserAdapter.setData(dataResponse!!.items)
            }

            override fun onFailure(call: Call<DataUserAPI>, t: Throwable) {
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.change_settings){
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}