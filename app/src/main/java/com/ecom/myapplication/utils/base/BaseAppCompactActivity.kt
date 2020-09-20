package com.ecom.myapplication.utils.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ecom.myapplication.R


open class BaseAppCompactActivity : AppCompatActivity() {

    private var mDialog: Dialog? = null

    fun showProgressBar(context: Context) {
        try {
            if (mDialog == null) {
                mDialog = Dialog(context, R.style.CustomProgressTheme)
                mDialog?.setContentView(R.layout.custom_progress)
                mDialog?.setCancelable(false)
                mDialog?.setCanceledOnTouchOutside(false)
                mDialog?.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismissProgress() {
        try {
            if (mDialog != null && mDialog!!.isShowing) {
                mDialog?.dismiss()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        mDialog = null
    }
}