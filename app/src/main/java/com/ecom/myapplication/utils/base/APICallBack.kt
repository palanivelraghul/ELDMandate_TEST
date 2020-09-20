package com.ecom.myapplication.utils.base

import com.ecom.myapplication.model.responsemodel.VideoFilesResponseModel

interface APICallBack {
    fun initiateAPICall(apiTransactionId: Int) {}

    fun onAPISuccess(successResponse: MutableList<VideoFilesResponseModel>, successTransactionId: Int) {}

    fun onAPIFailure(failureResponse: String?, failureTransactionId: Int) {}
}