package com.dev.jikanapp.ui.homelisting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.jikanapp.model.AnimeResponse
import com.dev.jikanapp.network.Resource
import com.dev.jikanapp.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(private val repository: AppRepository ) : ViewModel() {

    private val _animeList= MutableLiveData<Resource<Response<AnimeResponse>>>()
    val animeList: LiveData<Resource<Response<AnimeResponse>>> get()= _animeList

    init {
        getAllPosts()
    }

    private fun getAllPosts(){


        viewModelScope.launch (Dispatchers.IO){

            val result =repository.getAllPosts()


            _animeList.postValue(result)




        }
    }
}