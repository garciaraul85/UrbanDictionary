package com.example.urbandictionary.model.network

import com.example.urbandictionary.model.response.UrbanResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UrbanAPi {
    @GET("/define")
    fun getNews(@Query("term") term: String): Single<UrbanResponse>
}