package kg.geeks.hw.taskapp

import android.app.Application
import androidx.room.Room
import kg.geeks.hw.taskapp.data.local.db.AppDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "database-name")
            .allowMainThreadQueries().build()
    }

    companion object {
        lateinit var db: AppDataBase
    }
}