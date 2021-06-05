package com.dicoding.github.main

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataUser(
        var avatar_url : String = "",
        var login: String = "",
        var name: String = "",
        var location: String = "",
        var public_repos: String = "",
        var company : String = "",
        var followers: String = "",
        var following: String = ""
): Parcelable
