package com.dev.jikanapp.ui.detailPreview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.jikanapp.model.AnimeDetailResponse
import com.dev.jikanapp.model.AnimeResponse
import com.dev.jikanapp.model.Data
import com.dev.jikanapp.network.Resource
import com.dev.jikanapp.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel  @Inject constructor(private val repository: AppRepository) : ViewModel(){

    private val _animeData= MutableLiveData<Resource<Response<AnimeDetailResponse>>>()
    val animeData: LiveData<Resource<Response<AnimeDetailResponse>>> get()= _animeData



     fun getAnimDetails(id:Int){


        viewModelScope.launch (Dispatchers.IO){
            Log.d("TTT", "data id in repo ${id}")
            val result =repository.getAnimeDetails(id)


            _animeData.postValue(result)




        }
    }
}