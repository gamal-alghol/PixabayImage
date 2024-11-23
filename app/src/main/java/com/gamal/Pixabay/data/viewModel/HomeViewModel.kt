package com.gamal.Pixabay.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gamal.Pixabay.data.model.Hit
import com.gamal.Pixabay.data.repository.MainRepository
import com.gamal.Pixabay.ui.home.ImagesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    fun getPagedImages(query: String): Flow<PagingData<Hit>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15, // Adjust based on your API
                enablePlaceholders = false,
                initialLoadSize = 15,
                  prefetchDistance=16
            ),
            pagingSourceFactory = { ImagesPagingSource(repository, query) }
        ).flow.cachedIn(viewModelScope) // Cache in ViewModelScope for lifecycle awareness
    }
    /*
     val _getImages = MutableLiveData<Resource<ImagesResponse>>()
    val getImagesData: LiveData<Resource<ImagesResponse>> get() = _getImages

     fun getImages(key: String,
                 q: String,
                 image_type: String
                   ,per_page: Int,
                   page: Int) =viewModelScope.launch{
    _getImages.postValue(Resource.loading(null))
        try {
            val response = repository.getImages(key,q,image_type,per_page,page)
            Log.d("TEST", "getImages: ${response.toString()}")

            if (response.hits.isNotEmpty()) {
                // Successful response
                _getImages.postValue(Resource.success(response)) // Update the LiveData with success data
            } else {
                _getImages.postValue(Resource.error(  "Error Occurred!",response))
            }
        } catch (exception: Exception) {
            Log.d("TEST", "getImages: "+exception.message)
            _getImages.postValue(Resource.error(exception.message ?: "Error Occurred!", null))
        }

    }*/
}