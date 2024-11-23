package app.aleem.com.data.api

import com.gamal.Pixabay.data.model.ImagesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiService {


    @GET("api/")
    suspend fun getImages(@Query("key") key: String,
                          @Query("q") q: String,
                          @Query("image_type") image_type: String,
                          @Query("per_page") per_page: Int,
                          @Query("page") page: Int): ImagesResponse
}