package edu.iesam.marvelapp.core.cache

import android.content.Context
import edu.iesam.marvelapp.R

class CacheTTL(
    private val context: Context
) {

    private val keyCache: String = "cache"

    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.local_source_superhero), Context.MODE_PRIVATE)

    fun save(obj: String) {
        sharedPref.edit().apply(){
            putLong("$keyCache-$obj", System.currentTimeMillis())
            apply()
        }
    }

    fun isExpired(obj: String): Boolean{
        val pastTime: Long = sharedPref.getLong("$keyCache-$obj", 0)
        val currentTime = System.currentTimeMillis()

        return currentTime-pastTime >= 300000
    }

}