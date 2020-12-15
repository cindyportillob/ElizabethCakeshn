package com.example.elizabethcakeshn.utils

import android.content.Context
import android.media.Image
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.elizabethcakeshn.R
import java.io.IOException
import java.net.URI

class GlideLoader(val context: Context) {

    fun loadUserPicture(imageURI: Uri, imageView: ImageView){
        try {
            Glide
                .with(context).load(imageURI.toString())
                .centerCrop()
                .placeholder(R.drawable.ic_user_placeholder)
                .into(imageView)
        }catch (e:IOException){
            e.printStackTrace()
        }
    }

}