package com.dz.workmanagerexample.webauth

import com.dz.workmanagerexample.models.JsonHolderModel
import com.dz.workmanagerexample.models.JsonHolderModelItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiResponse {

    @GET("posts/{id}")
    fun getPost(@Path("id") postId : Int) : Call<JsonHolderModel>
}