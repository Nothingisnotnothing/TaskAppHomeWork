package kg.geeks.hw.taskapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

fun ImageView.loadImagePicasso(url: String?) {
    Picasso.get().load(url).into(this)
}

fun ImageView.loadImageGlide(url: String?){
    Glide.with(this).load(url).into(this)
}
