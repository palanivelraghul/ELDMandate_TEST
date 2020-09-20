package com.ecom.myapplication.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ecom.myapplication.R
import com.ecom.myapplication.databinding.ItemVideoListAdapterBinding
import com.ecom.myapplication.model.responsemodel.VideoFilesResponseModel
import com.ecom.myapplication.utils.CommonUtils

class VideoFileListAdapter(private val callBack: VideoFileListAdapterCallback, private var mVideoFileList: MutableList<VideoFilesResponseModel>) : RecyclerView.Adapter<VideoFileListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVideoListAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(mVideoFileList[position], callBack)

    }

    override fun getItemCount(): Int {
        return mVideoFileList.size
    }

    fun updateData(videoFilesList: List<VideoFilesResponseModel>) {
        val size = mVideoFileList.size
        mVideoFileList.addAll(videoFilesList)
        notifyItemRangeInserted(size, videoFilesList.size)
    }

    fun listSize() : Int{
        return mVideoFileList.size
    }

    class ViewHolder(var mBinding: ItemVideoListAdapterBinding) : RecyclerView.ViewHolder(mBinding.root) {
        private val STATUS_UPLOADED = "STATUS_UPLOADED"
        private val STATUS_NONE = "STATUS_NONE"
        private val STATUS_DOWNLOADED = "STATUS_DOWNLOADED"

        fun bindData(videoFile: VideoFilesResponseModel, callBack: VideoFileListAdapterCallback) {
            mBinding.callBack = callBack
            mBinding.videoFile = videoFile
            CommonUtils.loadUrlImage(mBinding.imgVideoThumbnail.context, mBinding.imgVideoThumbnail, videoFile.thumbnail)
            mBinding.tvVideoDuration.text = videoFile.fileSize
            mBinding.tvDate.text = CommonUtils.stringDate(videoFile.dateTime, CommonUtils.yyyyMMddTHHmmssSSS, CommonUtils.yyMMdd)
            mBinding.tvDateTime.text = String.format("%s%s", "@", CommonUtils.stringDate(videoFile.dateTime, CommonUtils.yyyyMMddTHHmmssSSS, CommonUtils.HHmmss))
            mBinding.imgStatus.setImageDrawable(getStatusImage(mBinding.imgStatus.context, videoFile.status))
        }

        private fun getStatusImage(context: Context, status: String?): Drawable? {
           return when(status){
                    STATUS_NONE -> ContextCompat.getDrawable(context, R.drawable.ic_status_none)
                    STATUS_UPLOADED -> ContextCompat.getDrawable(context, R.drawable.ic_status_uploaded)
                    STATUS_DOWNLOADED -> ContextCompat.getDrawable(context, R.drawable.ic_status_downloaded)
                    else -> ContextCompat.getDrawable(context, R.drawable.ic_status_none)
           }
        }
    }

    interface VideoFileListAdapterCallback{
        fun onVideoClick(videoFile: VideoFilesResponseModel)
    }

}
