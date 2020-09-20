package com.ecom.myapplication.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ecom.myapplication.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


object CommonUtils {

    const val yyyyMMddTHHmmssSSS = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    const val yyMMdd = "yy/MM/dd"
    const val HHmmss = "HH:mm:ss"

    fun stringDate(date: String?, inputDateFormat: String?, outputDateFormat: String?): String? {
        @SuppressLint("SimpleDateFormat") val inputFormat = SimpleDateFormat(inputDateFormat)
        @SuppressLint("SimpleDateFormat") val outputFormat = SimpleDateFormat(outputDateFormat)
        var formatDate: String? = ""
        try {
            val d = inputFormat.parse(date)
            formatDate = outputFormat.format(d)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formatDate
    }
    fun convertMillisToTimeFormat(millis: Long): String {
        return String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(
                    millis
                )
            ),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(
                    millis
                )
            )
        )
    }

    fun loadUrlImage(context: Context?, imageView: ImageView, imageUrl: String?) {
        Glide.with(context!!).load(imageUrl).thumbnail(0.5f)
            .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_status_none)
            .error(Glide.with(imageView).load(R.drawable.ic_status_none)).into(imageView)
    }

    fun setSnapHelperProperties(recyclerView: RecyclerView) {
        val snapHelper: SnapHelper = PagerSnapHelper()
        recyclerView.onFlingListener = null
        snapHelper.attachToRecyclerView(recyclerView)
    }

}