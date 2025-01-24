package com.dev.jikanapp.repository

import com.dev.jikanapp.network.ApiService
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiService: ApiService):BaseRepository() {


    suspend fun getAllPosts()=safApiCall {
        apiService.getAllPosts()
    }

suspend fun  getAnimeDetails(id:Int)=safApiCall {
    apiService.getAnimeDetails(id)
}

}