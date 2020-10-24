package com.jahk.myarticlesviewer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface HitDao {
    @Query("SELECT * FROM HitDb ORDER BY created_at DESC")
    fun getArticles(): LiveData<List<HitDb>>

    @Query("SELECT * FROM HitDb WHERE objectID NOT IN (SELECT objectID FROM DeletedItem) ORDER BY created_at DESC")
    fun getFilteredArticles(): LiveData<List<HitDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( hit: List<HitDb>)

    @Query("DELETE FROM HitDb")
    fun deleteAll()

    @Delete
    fun deleteItem(hit: HitDb)
}

@Dao
interface DeletedItemDao {
    @Query("SELECT * FROM DeletedItem ORDER BY _id DESC")
    fun getDeletedItems(): LiveData<List<DeletedItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: DeletedItem)

    @Query("DELETE FROM DeletedItem")
    fun deleteAll()

    @Delete
    fun deleteItem(item: DeletedItem)
}

@Database(entities = [HitDb::class, DeletedItem::class], version = 1)
abstract class ArticlesDB: RoomDatabase() {
    abstract val hitDao: HitDao
    abstract val deletedItemDao: DeletedItemDao
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