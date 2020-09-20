package com.ecom.myapplication.utils.retrofit

import com.ecom.myapplication.model.responsemodel.VideoFilesResponseModel
import com.ecom.myapplication.utils.AppConstant
import retrofit2.Response
import retrofit2.http.*

interface RetrofitInterface {
    @GET(ServicePathConstant.GET_VIDEO_FILES)
    suspend fun getVideoFiles(
        @HeaderMap headerMap: Map<String, String>,
        @Query(AppConstant.API_OFFSET) offset: String,
        @Query(AppConstant.API_LIMIT) limit: String
    ): Response<MutableList<VideoFilesResponseModel>>?

}