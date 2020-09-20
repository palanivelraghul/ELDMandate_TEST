package com.ecom.myapplication.utils.base

abstract class BaseActivityViewModel<VM : AppBaseViewModel> : BaseAppCompactActivity() {

    abstract fun onCreateViewModel(): VM

    open fun <T : AppBaseViewModel?> onRetry(viewModel: T) {

    }
}