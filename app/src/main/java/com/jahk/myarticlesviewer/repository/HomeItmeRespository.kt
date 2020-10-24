package com.jahk.myarticlesviewer.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.jahk.myarticlesviewer.database.ArticlesDB
import com.jahk.myarticlesviewer.database.DeletedItem
import com.jahk.myarticlesviewer.database.asDomainModel
import com.jahk.myarticlesviewer.domain.HomeModel
import com.jahk.myarticlesviewer.domain.toHitDb
import com.jahk.myarticlesviewer.network.ArticlesNetwork
import com.jahk.myarticlesviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticlesRepository(private val database: ArticlesDB) {
    val TAG = ArticlesRepository::class.java.simpleName

    suspend fun refreshArticles() {
        withContext(Dispatchers.IO) {
            val articles = ArticlesNetwork.articles.getArticles("android").await()
            articles.let {art ->
                database.hitDao.deleteAll()
                database.hitDao.insertAll(art.asDatabaseModel())
            }
        }
    }

    suspend fun deleteItem(homeItem: HomeModel) {
        val objectID = homeItem.objectID
        database.hitDao.deleteItem(homeItem.toHitDb())
        objectID?.let {
            database.deletedItemDao.insert(DeletedItem(objectID = it))
        }
    }

    val articles: LiveData<List<HomeModel>> = Transformations.map(database.hitDao.getFilteredArticles()) {
        it.asDomainModel()
    }

}

