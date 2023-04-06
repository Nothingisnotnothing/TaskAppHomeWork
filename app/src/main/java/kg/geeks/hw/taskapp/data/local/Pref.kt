package kg.geeks.hw.taskapp.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Pref(context: Context) {

    private val pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)

    companion object {
        const val PREF_NAME = "taskApp"
        const val IS_USER_SEEN_ET = "isUserSeen"
        const val SAVE_ET_TEXT = "saveEtText"
        const val SAVE_IMAGE_PATH = "imagePath"
    }

    fun isUserSeen(): Boolean {
        return pref.getBoolean(IS_USER_SEEN_ET, false)
    }

    fun saveUserSeen() {
        pref.edit().putBoolean(IS_USER_SEEN_ET, true).apply()
    }

    fun saveEtText(text: String) {
        pref.edit().putString(SAVE_ET_TEXT, text).apply()
    }

    fun loadEtText() = pref.getString(SAVE_ET_TEXT, "")

    fun saveImagePath(path: String) {
        pref.edit().putString(SAVE_IMAGE_PATH, path).apply()
    }

    fun loadImagePath() = pref.getString(SAVE_IMAGE_PATH, "")
}