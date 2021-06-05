package com.dicoding.github.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.github.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentFollowing : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var rvUser: RecyclerView
    private lateinit var adapterUser: AdapterUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.followingfragment, container, false)
        rvUser = view!!.findViewById(R.id.rv_following)
        showRecyclerList()

        val username = (activity as DetailUser).username
        Log.e("username", username)
        onGetData(username)

        return view
    }

    private fun onGetData(username: String) {
        Retrofit.userRepositories().getUsersFollowing(username).enqueue(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val data = response.body()
                adapterUser.setData(data as MutableList<User>)
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
            }
        })
    }

    private fun showRecyclerList(){
        rvUser.layoutManager = LinearLayoutManager (context)
        adapterUser = AdapterUser()
        rvUser.adapter = adapterUser
    }

    companion object{
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FragmentFollowing().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}