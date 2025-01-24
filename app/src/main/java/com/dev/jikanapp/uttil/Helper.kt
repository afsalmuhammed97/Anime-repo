package com.dev.jikanapp.uttil

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dev.jikanapp.R
import com.dev.jikanapp.network.Resource
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message:String, action:(()->Unit)?=null){

    val snackbar= Snackbar.make(this,message, Snackbar.LENGTH_LONG)

    action?.let {
        snackbar.setAction("Retry"){
            it()
        }
    }

    snackbar.show()
}

fun Fragment.handleApiError(failure: Resource.Failure, retry:(()->Unit)?= null ){

    when{
        failure.isNetworkError ==true ->requireView().showSnackBar("Pleas check your Internet connection !!",retry)


        else ->{
            val error= failure.errorBody?.toString().toString()

            requireView().showSnackBar(error)
        }
    }
}

 fun ImageView.loadImage(imgUrl:String, ){

    Glide.with(this.context)
        .load(imgUrl)
        //.centerCrop()
        .placeholder(R.drawable.ic_launcher_background)
        .fitCenter()
        .into(this)


}