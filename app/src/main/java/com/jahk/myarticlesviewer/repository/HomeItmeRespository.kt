package com.jahk.myarticlesviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.jahk.myarticlesviewer.database.ArticlesDB
import com.jahk.myarticlesviewer.database.asDomainModel
import com.jahk.myarticlesviewer.domain.HomeModel
import com.jahk.myarticlesviewer.network.ArticlesNetwork
import com.jahk.myarticlesviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticlesRepository(private val database: ArticlesDB) {

    suspend fun refreshArticles() {
        withContext(Dispatchers.IO) {
            val articles = ArticlesNetwork.articles.getArticles("android").await()
            articles.let {art ->
                database.hitDao.deleteAll()
                database.hitDao.insertAll(art.asDatabaseModel())
            }
        }
    }

    val articles: LiveData<List<HomeModel>> = Transformations.map(database.hitDao.getArticles()) {
        it.asDomainModel()
    }
}