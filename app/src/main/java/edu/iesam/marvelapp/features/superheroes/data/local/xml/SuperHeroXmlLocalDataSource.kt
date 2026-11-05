package edu.iesam.marvelapp.features.superheroes.data.local.xml

import android.content.Context
import edu.iesam.marvelapp.R
import edu.iesam.marvelapp.core.cache.AppCache
import edu.iesam.marvelapp.core.serialization.JsonSerializer
import edu.iesam.marvelapp.features.superheroes.domain.SuperHero

/**
 * Conector para gestionar datos en un fichero XML a través de SharedPreferences.
 *
 */

class SuperHeroXmlLocalDataSource(
    private val context: Context,
    private val serializer: JsonSerializer<List<SuperHero>>,
    private val appCache: AppCache) {

    private val keySuperHero: String = "superhero"
    private val ttl: Long = 5*100*60

    /**
     * Me creo la fuente de datos xml: SharedPreferences
     */
    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.local_source_superhero), Context.MODE_PRIVATE)


    /**
     * Metodo para guardar un listado de superheroe
     */
    fun save(superHero: List<SuperHero>) {
        //apply { } Scope Function
        sharedPref.edit().apply(){
            putString(keySuperHero, serializer.toJson(superHero))
            apply()
            appCache.updateTime(keySuperHero)
        }
    }

    fun findAll(): List<SuperHero>{
        if (!appCache.isExpired(keySuperHero,ttl)){
            val jsonList: String? = sharedPref.getString(keySuperHero, null)
            jsonList?.let {
                return serializer.fromJson(it)
            }
        }
        return emptyList()
    }

    fun findById(id: String): SuperHero?{
        return findAll().find { it.id == id }
    }

    fun deleteById(id: String){
        val nuevaLista = findAll().filterNot { it.id == id }
        save(nuevaLista)
    }

    fun deleteAll(){
        sharedPref.edit().apply(){
            remove(keySuperHero)
            apply()
        }
    }
}