package kg.geeks.hw.taskapp

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import kg.geeks.hw.taskapp.data.local.Pref
import kg.geeks.hw.taskapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pref: Pref
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        pref = Pref(this)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.e("kamino", "onCreate: " + it)
        }

        if (!pref.isUserSeen())
            navController.navigate(R.id.navigation_on_board)

        if (auth.currentUser?.uid == null)
            navController.navigate(R.id.navigation_auth)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_profile
            )
        )

        val bottomNavFragments = setOf(
            R.id.navigation_home,
            R.id.navigation_dashboard,
            R.id.navigation_notifications,
            R.id.navigation_profile
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            navView.isVisible = bottomNavFragments.contains(destination.id)
            if (destination.id == R.id.navigation_on_board) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
        }
    }
}