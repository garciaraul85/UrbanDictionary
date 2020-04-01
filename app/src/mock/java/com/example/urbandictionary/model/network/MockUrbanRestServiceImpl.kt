package com.example.urbandictionary.model.network

import com.example.urbandictionary.model.network.remote.UrbanRestService
import com.example.urbandictionary.model.response.UrbanResponse
import com.example.urbandictionary.model.response.Word
import io.reactivex.Single

class MockUrbanRestServiceImpl(): UrbanRestService {
    override fun getDefinitions(term: String): Single<UrbanResponse> {
        val word1 = Word("word1", 1, 1)
        val word2 = Word("word2", 2, 2)
        val word3 = Word("word3", 3, 3)
        val words = mutableListOf(word1, word2, word3)
        val response = UrbanResponse(words)
        return Single.just(response)
    }
}