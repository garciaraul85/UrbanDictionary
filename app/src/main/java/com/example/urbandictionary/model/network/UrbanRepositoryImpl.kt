package com.example.urbandictionary.model.network

import com.example.urbandictionary.model.response.UrbanResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class UrbanRepositoryImpl(private val webService: WebService) : UrbanRepository {
    override fun getDefinitionList(term: String): Single<UrbanResponse> {
        return webService
            .getDefinitions(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}