package com.dicoding.github.main

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.github.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class DetailUser : AppCompatActivity() {
    private lateinit var name: TextView
    private lateinit var login: TextView
    private lateinit var location: TextView
    private lateinit var repository: TextView
    private lateinit var company: TextView
    private lateinit var followers: TextView
    private lateinit var following: TextView
    private lateinit var photo: CircleImageView
    private lateinit var showProgressBar: ProgressBar
    lateinit var username: String

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_1,R.string.tab_2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_detail)

        showProgressBar = findViewById(R.id.progressbar)

        username = intent.getStringExtra("login")!!

        name = findViewById(R.id.name)
        login = findViewById(R.id.username)
        location = findViewById(R.id.location)
        repository = findViewById(R.id.repository)
        company = findViewById(R.id.company)
        followers = findViewById(R.id.followers)
        following = findViewById(R.id.following)
        photo = findViewById(R.id.avatar)

        onGetData(username)
        val adapterPagerDetails = AdapterPagerDetails(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = adapterPagerDetails
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    private fun onGetData(username: String?) {
        showProgressBar.visibility = View.VISIBLE
        Retrofit.userRepositories().getDetailUser(username.toString()).enqueue(object : Callback<DataUser>{
            override fun onResponse (call: Call<DataUser>, response: Response<DataUser>){
                val data = response.body()
                showProgressBar.visibility = View.GONE
                Glide.with(this@DetailUser)
                        .load(data?.avatar_url)
                        .apply(RequestOptions().override(55, 55))
                        .into(photo)


                name.text = data?.name
                login.text = data?.login
                company.text = data?.company
                location.text = data?.location
                repository.text = data?.public_repos
                followers.text = data?.followers
                following.text = data?.following
            }

            override fun onFailure(call: Call<DataUser>, t: Throwable){

            }
        })

    }
}