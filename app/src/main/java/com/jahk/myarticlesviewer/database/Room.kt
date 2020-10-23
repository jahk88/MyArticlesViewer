package com.jahk.myarticlesviewer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface HitDao {
    @Query("select * from HitDb")
    fun getArticles(): LiveData<List<HitDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( hit: List<HitDb>)

    @Query("DELETE FROM HitDb")
    fun deleteAll()
}

@Database(entities = [HitDb::class], version = 1)
abstract class ArticlesDB: RoomDatabase() {
    abstract val hitDao: HitDao
}

private lateinit var INSTANCE: ArticlesDB

fun getDatabase(context: Context): ArticlesDB {
    synchronized(ArticlesDB::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ArticlesDB::class.java,
                "articles_db").build()
        }
    }
    return INSTANCE
}