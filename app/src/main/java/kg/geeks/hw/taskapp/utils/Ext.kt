package kg.geeks.hw.taskapp.utils

import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import kg.geeks.hw.taskapp.model.OnBoard

fun ImageView.loadImage(url: String?) {
    Picasso.get().load(url).into(this)
}
