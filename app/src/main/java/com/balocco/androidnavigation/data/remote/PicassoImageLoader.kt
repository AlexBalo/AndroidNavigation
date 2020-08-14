package com.balocco.androidnavigation.data.remote

import android.widget.ImageView
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PicassoImageLoader @Inject constructor(
    private val picasso: Picasso
) : ImageLoader {

    override fun loadImage(target: ImageView, url: String) {
        if (url.isNotEmpty()) {
            picasso.load(url).into(target)
        }
    }
}