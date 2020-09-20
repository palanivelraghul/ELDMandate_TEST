package com.ecom.myapplication.videmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecom.myapplication.model.responsemodel.VideoFilesResponseModel
import com.ecom.myapplication.utils.base.AppBaseViewModel
import com.ecom.myapplication.utils.retrofit.ServiceRepository
import kotlinx.coroutines.launch

class VideoListActivityViewModel : AppBaseViewModel() {

    private lateinit var mCallback: VideoListActivityViewModelCallBack
    val mVideoListLiveData = MutableLiveData<MutableList<VideoFilesResponseModel>>()
    private var limit = 6
    private var offset = 0
    private val headerKey = "x-api-key"
    private val headerValue = "jvmNAyPNr1JhiCeUlYmB2ae517p3Th0aGG6syqMb"


    fun initiate(callback: VideoListActivityViewModelCallBack) {
        this.mCallback = callback
        mCallback.showLoader()
        initiateAPICall(ServiceRepository.GET_VIDEO_FILES)
    }

    override fun initiateAPICall(apiTransactionId: Int) {
        super.initiateAPICall(apiTransactionId)
        if (mCallback.isInternetConnectionSuccess()) {
            viewModelScope.launch {
                when (apiTransactionId) {
                    ServiceRepository.GET_VIDEO_FILES -> ServiceRepository.getVideoFiles(this@VideoListActivityViewModel, getHeaderMap(), offset.toString(), limit.toString())
                }
            }
        } else {
            mCallback.dismissLoader()
            mCallback.showNetworkError()
        }
    }

    override fun onAPISuccess(successResponse: MutableList<VideoFilesResponseModel>, failureTransactionId: Int) {
        super.onAPISuccess(successResponse, failureTransactionId)
        mCallback.dismissLoader()
        mVideoListLiveData.value = successResponse
    }

    override fun onAPIFailure(failureResponse: String?, successTransactionId: Int) {
        super.onAPIFailure(failureResponse, successTransactionId)
        mCallback.dismissLoader()
        mCallback.showAPIError()
    }

    fun initiateVideoList(videoFilesList: MutableList<VideoFilesResponseModel>) {
        mCallback.loadVideoListAdapter(videoFilesList)
        mCallback.dismissLoader()
    }

    private fun getHeaderMap(): Map<String, String> {
        var headerMap = mutableMapOf<String, String>()
        headerMap[headerKey] = headerValue
        return headerMap
    }

    fun getVideoListPagenation(layoutManager: GridLayoutManager): RecyclerView.OnScrollListener? {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + firstVisibleItem >= totalItemCount && firstVisibleItem >= 0 && totalItemCount >= limit) {
                        if (offset <= 1000) {
                            offset += limit
                            mCallback.showLazyLoader()
                            initiateAPICall(ServiceRepository.GET_VIDEO_FILES)
                        }
                }
            }
        }
    }

    interface VideoListActivityViewModelCallBack {
        fun showToastMessage(message: String)
        fun isInternetConnectionSuccess(): Boolean
        fun showLoader()
        fun dismissLoader()
        fun showNetworkError()
        fun showAPIError()

        fun showLazyLoader()
        fun hideLazyLoader()
        fun loadVideoListAdapter(videoFilesList: MutableList<VideoFilesResponseModel>)
        fun onSearchClick()
        fun onCalendarClick()
    }

}
