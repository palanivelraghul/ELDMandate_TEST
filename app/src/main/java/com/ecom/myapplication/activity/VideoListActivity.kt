package com.ecom.myapplication.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ecom.myapplication.R
import com.ecom.myapplication.adapter.VideoFileListAdapter
import com.ecom.myapplication.databinding.ActivityVideoListBinding
import com.ecom.myapplication.model.responsemodel.VideoFilesResponseModel
import com.ecom.myapplication.utils.NetworkUtils
import com.ecom.myapplication.utils.base.BaseActivityViewModel
import com.ecom.myapplication.videmodel.VideoListActivityViewModel

class VideoListActivity : BaseActivityViewModel<VideoListActivityViewModel>(),
    VideoListActivityViewModel.VideoListActivityViewModelCallBack,
    VideoFileListAdapter.VideoFileListAdapterCallback {

    private lateinit var mBinding: ActivityVideoListBinding
    private lateinit var mViewModel: VideoListActivityViewModel
    private var adapter: VideoFileListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityVideoListBinding.inflate(layoutInflater)
        mBinding.callBack = this
        onCreateViewModel()
        setContentView(mBinding.root)
    }

    override fun onCreateViewModel(): VideoListActivityViewModel {
        mViewModel = ViewModelProvider(this).get(VideoListActivityViewModel::class.java)
        mViewModel.initiate(this)
        mViewModel.mVideoListLiveData.observe(this, VideoListResponseObserver())
        return mViewModel
    }

    override fun loadVideoListAdapter(videoFilesList: MutableList<VideoFilesResponseModel>) {
        if (adapter != null) {
            adapter!!.updateData(videoFilesList);
        } else {
            adapter = VideoFileListAdapter(this, videoFilesList)
            mBinding.rvVideoList.adapter = adapter
            mBinding.rvVideoList.layoutManager = GridLayoutManager(this, 2)
            mBinding.rvVideoList.addOnScrollListener(mViewModel.getVideoListPagenation(mBinding.rvVideoList.layoutManager as GridLayoutManager)!!)
        }
    }

    override fun onSearchClick() {
        showToastMessage(getString(R.string.text_search))
    }

    override fun onCalendarClick() {
        showToastMessage(getString(R.string.text_calendar))
    }

    override fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun isInternetConnectionSuccess(): Boolean {
        return NetworkUtils.isConnected(this)
    }

    override fun showLoader() {
        showProgressBar(this)
    }

    override fun dismissLoader() {
        dismissProgress()
    }

    override fun showNetworkError() {
        showToastMessage(getString(R.string.text_no_internet))
    }

    override fun showAPIError() {
        showToastMessage(getString(R.string.text_api_error))
    }

    override fun showLazyLoader() {
        mBinding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLazyLoader() {
        mBinding.progressBar.visibility = View.GONE
    }

    override fun onVideoClick(videoFile: VideoFilesResponseModel) {
        showToastMessage(videoFile.id.toString())
    }


    private inner class VideoListResponseObserver : Observer<MutableList<VideoFilesResponseModel>> {
        override fun onChanged(videoFilesList: MutableList<VideoFilesResponseModel>) {
            hideLazyLoader()
            mViewModel.initiateVideoList(videoFilesList)
            mBinding.executePendingBindings()
        }
    }
}