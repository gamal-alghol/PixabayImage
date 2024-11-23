package app.aleem.com.data.api

import okhttp3.MultipartBody
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiHelper @Inject constructor(private val apiService: ApiService) {
    suspend fun getImages( key: String,
                          q: String,
                          image_type: String,
                           per_page: Int,
                           page: Int) = apiService.getImages(key,q,image_type,per_page,page)

}
