package kg.geeks.hw.taskapp

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kg.geeks.hw.taskapp.data.local.Pref
import kg.geeks.hw.taskapp.databinding.ActivityMainBinding

//д.3 1) После ввода чего либо в ET в ProfileFragment без нажатия текст должен сохранится
// 2) При нажатии на IMG в ProfileFragment, загружаем фото из галереи, аналогично далее с ET
//Осталось доделать сохранение пути к фото и загрузке при запуске приложения
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pref: Pref


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = Pref(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        if (!pref.isUserSeen())
            navController.navigate(R.id.navigation_on_board)

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