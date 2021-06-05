package com.dicoding.github.main


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface api {
    @GET("search/users")
    @Headers("Authorization:token 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")

    fun getSearchUsers(
            @Query("q") query: String
    ):Call<DataUserAPI>

    @GET("users/{id}")
    @Headers("Authorization:token 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")

    fun getDetailUser(
       @Path("id") id: String
    ):Call<DataUser>

    @GET("users/{id}/followers")
    @Headers("Authorization:token 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")

    fun getUsersFollowers(
        @Path("id") id: String
    ):Call<List<User>>

    @GET("users/{id}/following")
    @Headers("Authorization:token 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")

    fun getUsersFollowing(
        @Path("id") id: String
    ):Call<List<User>>
}