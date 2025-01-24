package com.dev.jikanapp.network

import com.dev.jikanapp.model.AnimeDetailResponse
import com.dev.jikanapp.model.AnimeResponse
import com.dev.jikanapp.model.Data
import com.dev.jikanapp.uttil.Constants.ANIME
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(ANIME)
    suspend fun getAllPosts(): Response<AnimeResponse>

    @GET("v4/anime/{anime_id}")
    suspend fun getAnimeDetails( @Path("anime_id") animeId:Int):Response<AnimeDetailResponse>

}