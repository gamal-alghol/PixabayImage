package com.gamal.Pixabay.data.repository

import app.aleem.com.data.api.ApiHelper
import app.aleem.com.data.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository@Inject constructor(
    private val apiHelper: ApiHelper
) {

    suspend fun getImages(key: String,
                        q: String,
                        image_type: String,per_page: Int,
                          page: Int) = apiHelper.getImages(key,q,image_type,per_page,page)

}