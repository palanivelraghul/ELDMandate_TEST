package com.ecom.myapplication.utils.base

import androidx.lifecycle.ViewModel
import com.ecom.myapplication.model.responsemodel.VideoFilesResponseModel
import com.ecom.myapplication.utils.base.APICallBack

abstract class AppBaseViewModel : ViewModel(), APICallBack {

    override fun onAPIFailure(failureResponse: String?, successTransactionId: Int) {
        super.onAPIFailure(failureResponse, successTransactionId)
    }

    override fun onAPISuccess(successResponse: MutableList<VideoFilesResponseModel>, failureTransactionId: Int) {
        super.onAPISuccess(successResponse, failureTransactionId)
    }

    override fun initiateAPICall(apiTransactionId: Int) {
        super.initiateAPICall(apiTransactionId)
    }


}