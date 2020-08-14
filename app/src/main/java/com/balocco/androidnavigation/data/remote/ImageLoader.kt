package com.balocco.androidnavigation.data.remote

import android.widget.ImageView

interface ImageLoader {

    fun loadImage(target: ImageView, url: String)

}