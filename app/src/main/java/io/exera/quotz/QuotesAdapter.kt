package io.exera.quotz

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.*
import io.exera.quotz.tools.getGradient
import io.exera.quotz.database.Quote
import io.exera.quotz.database.QuoteDAO
import io.exera.quotz.tools.CardInterractionListener
import kotlinx.android.synthetic.main.card_view.view.*
import java.util.*

const val ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110"
const val ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713"

class QuotesAdapter(val quotes: ArrayList<Quote>, val context: Context, private val cardInterractionListener: CardInterractionListener) : BaseAdapter() {


    init {
        MobileAds.initialize(context, ADMOB_APP_ID)
    }

    override fun getItem(position: Int): Quote? {
        return quotes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.quotes.size
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, contentView: View?, parent: ViewGroup): View {

        if (position % 2 == 1)
            return getAdView(contentView, parent)
        else
            return getQuoteView(position, contentView, parent)
    }

    private fun getQuoteView(position: Int, contentView: View?, parent: ViewGroup): View {
        var contentView = contentView
        val holder: ViewHolder

        if (contentView == null) {
            val inflater = LayoutInflater.from(context)
            contentView = inflater.inflate(R.layout.card_view, parent, false)
            holder = ViewHolder(contentView)
            contentView!!.tag = holder
        } else {
            holder = contentView.tag as ViewHolder
        }
        holder.adContainer.visibility = View.GONE
        holder.quoteContainer.visibility = View.VISIBLE

        val quote = getItem(position)
        holder.text.text = "\"${quote!!.text}\""

        holder.image.setImageDrawable(getGradient())

        holder.author.text = "- ${quote!!.author}"
        holder.like.isChecked = quote.isFavorite
        holder.like.setOnClickListener {
            if (holder.like.isChecked) {
                quote.isFavorite = true
                QuoteDAO.add(quote)
            } else {
                QuoteDAO.remove(quote)
            }
        }
        holder.share.setOnClickListener {
            cardInterractionListener.onShare(quote)
        }

        return contentView
    }

    private class ViewHolder(view: View) {
        var text = view.quote_text!!
        var image = view.quote_picture!!
        var author = view.quote_author!!
        var share = view.share!!
        var like = view.like!!
        var adFrame = view.ad_frame
        var quoteContainer = view.quote_view
        var adContainer = view.ad_container
        var skip = view.button_skip
    }

    private fun getAdView(contentView: View?, parent: ViewGroup): View {
        var contentView = contentView
        val holder: ViewHolder

        if (contentView == null) {
            val inflater = LayoutInflater.from(context)
            contentView = inflater.inflate(R.layout.card_view, parent, false)
            holder = ViewHolder(contentView)
            contentView!!.tag = holder
        } else {
            holder = contentView.tag as ViewHolder
        }
        holder.adContainer.visibility = View.VISIBLE
        holder.quoteContainer.visibility = View.GONE
        holder.skip.setOnClickListener {
            cardInterractionListener.onSkip()
        }

        refreshAd(holder.adFrame)
        return contentView
    }

    /**
     * Populates a [UnifiedNativeAdView] object with data from a given
     * [UnifiedNativeAd].
     *
     * @param nativeAd the object containing the ad's assets
     * @param adView          the view to be populated
     */
    private fun populateUnifiedNativeAdView(nativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView) {
        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        val vc = nativeAd.videoController

        // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
        // VideoController will call methods on this object when events occur in the video
        // lifecycle.
        vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
            override fun onVideoEnd() {
                // Publishers should allow native ads to complete video playback before refreshing
                // or replacing them with another ad in the same UI location.
//                refresh_button.isEnabled = true
//                videostatus_text.text = "Video status: Video playback has ended."
                super.onVideoEnd()
            }
        }

        val mediaView = adView.findViewById<MediaView>(R.id.ad_media)
        val mainImageView = adView.findViewById<ImageView>(R.id.ad_image)
        val mainView = adView.findViewById<LinearLayout>(R.id.main_view)
//        mainView.background = getGradient()
        // Apps can check the VideoController's hasVideoContent property to determine if the
        // NativeAppInstallAd has a video asset.
        if (vc.hasVideoContent()) {
            adView.mediaView = mediaView
            mainImageView.visibility = View.GONE
//            videostatus_text.text = String.format(Locale.getDefault(),
//                    "Video status: Ad contains a %.2f:1 video asset.",
//                    vc.aspectRatio)
        } else {
            adView.imageView = mainImageView
            mediaView.visibility = View.GONE

            val images = nativeAd.images
            if (images.size > 0) {
                mainImageView.setImageDrawable(images[0].drawable)
            }
//
//            refresh_button.isEnabled = true
//            videostatus_text.text = "Video status: Ad does not contain a video asset."
        }

        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // Some assets are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        (adView.bodyView as TextView).text = nativeAd.body
        (adView.callToActionView as Button).text = nativeAd.callToAction

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.icon == null) {
            adView.iconView.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                    nativeAd.icon.drawable)
            adView.iconView.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView.visibility = View.INVISIBLE
        } else {
            adView.priceView.visibility = View.VISIBLE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView.visibility = View.INVISIBLE
        } else {
            adView.storeView.visibility = View.VISIBLE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility = View.VISIBLE
        }

        adView.setNativeAd(nativeAd)
    }

    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     *
     */
    private fun refreshAd(ad_frame: FrameLayout) {
//        refresh_button.isEnabled = false

        val builder = AdLoader.Builder(context, ADMOB_AD_UNIT_ID)

        builder.forUnifiedNativeAd { unifiedNativeAd ->
            // OnUnifiedNativeAdLoadedListener implementation.
            val adView = LayoutInflater.from(context)
                    .inflate(R.layout.ad_unified, null) as UnifiedNativeAdView
            populateUnifiedNativeAdView(unifiedNativeAd, adView)
            ad_frame.removeAllViews()
            ad_frame.addView(adView)
        }

        val videoOptions = VideoOptions.Builder()
                .setStartMuted(true)
                .build()

        val adOptions = NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build()

        builder.withNativeAdOptions(adOptions)

        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(errorCode: Int) {
//                refresh_button.isEnabled = true
//                Toast.makeText(this@MainActivity, "Failed to load native ad: " + errorCode, Toast.LENGTH_SHORT).show()
            }
        }).build()

        adLoader.loadAd(AdRequest.Builder().build())

//        videostatus_text.text = ""
    }


}