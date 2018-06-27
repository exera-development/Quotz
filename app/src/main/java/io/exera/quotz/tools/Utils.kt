package io.exera.quotz.tools

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import io.exera.quotz.R
import io.exera.quotz.database.Quote
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.card_view.view.*


/**
 * Verifies that the device has internet connection or not
 */
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

@SuppressLint("SetTextI18n")

        /**
         * generates an shareable image from the quote
         * @param quote the quote from which we want to create an image
         */
fun Context.getQuoteImage(quote: Quote): Bitmap {
    val view = RelativeLayout(this)
    val layoutInflater = LayoutInflater.from(this)
    layoutInflater.inflate(R.layout.facebook_post, view, true)
    view.layoutParams = ViewGroup.LayoutParams(800, 800)
    view.quote_picture.setImageDrawable(getGradient())
    view.quote_text.text = "\"${quote.text}\""
    view.quote_author.text = "- ${quote.author}"

    view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

    view.layout(0, 0, view.measuredWidth, view.measuredHeight);

    val b = Bitmap.createBitmap(view.measuredWidth,
            view.measuredHeight,
            Bitmap.Config.ARGB_8888)
    val c = Canvas(b)
    view.layout(view.left, view.top, view.right, view.bottom)
    view.draw(c)
    return b
}