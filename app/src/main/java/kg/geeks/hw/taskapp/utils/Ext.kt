package kg.geeks.hw.taskapp.utils

import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

fun ImageView.loadImagePicasso(url: String?) {
    Picasso.get().load(url).into(this)
}

fun ImageView.loadImageGlide(url: String?){
    Glide.with(this).load(url).into(this)
}

fun Fragment.showToast(msg : String){
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}