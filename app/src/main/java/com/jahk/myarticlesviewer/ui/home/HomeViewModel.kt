package com.jahk.myarticlesviewer.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jahk.myarticlesviewer.database.getDatabase
import com.jahk.myarticlesviewer.domain.HomeModel
import com.jahk.myarticlesviewer.network.ArticlesNetwork.articles
import com.jahk.myarticlesviewer.network.getHitsList
import com.jahk.myarticlesviewer.repository.ArticlesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _message = MutableLiveData<String?>()

    val message: LiveData<String?>
        get() = _message

    private val _homeItemSelected = MutableLiveData<HomeModel?>()

    val homeItemSelected: LiveData<HomeModel?>
        get() = _homeItemSelected

    private val _isHomeItemSelected = MutableLiveData<Boolean>(false)

    val isHomeItemSelected: LiveData<Boolean>
        get() = _isHomeItemSelected

    // private val _homeItems = MutableLiveData<List<HomeModel>>()

    val homeItems: LiveData<List<HomeModel>>
        get() = articlesRepository.articles

    lateinit var adapter: HomeItemsdapter

    private val articlesRepository = ArticlesRepository(getDatabase(application))

    fun getHomeItems() = viewModelScope.launch {
        try {
            val response = articles.getArticles("android").await()
            //_homeItems.postValue(response.getHitsList())
        } catch (ioException: IOException) {
            // Show a Toast error message and hide the progress bar.
            _message.postValue("Error de I/O")
            Log.e(HomeViewModel::class.java.simpleName, "I/O exception: ${ioException.message}")
        } catch (netError: HttpException) {
            _message.postValue("Error en la comunicación de red")
            Log.e(HomeViewModel::class.java.simpleName, "Network error: URL: ${netError.response().toString()} ")
        }
    }

    fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                articlesRepository.refreshArticles()
            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(homeItems.value.isNullOrEmpty())
                    _message.value = "Network error"
            }
        }
    }

    fun itemSelected(homeItem: HomeModel) {
        _isHomeItemSelected.value = true
        _homeItemSelected.postValue(homeItem)
    }

    fun onItemSelectedShown() {
        _isHomeItemSelected.value = false
    }

}