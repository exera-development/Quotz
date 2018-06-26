package io.exera.quotz

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import io.exera.quotz.database.Quote
import io.exera.quotz.database.QuoteDAO
import io.exera.quotz.tools.CardInterractionListener
import io.exera.quotz.tools.getQuoteImage
import io.exera.quotz.tools.getQuote
import io.exera.quotz.tools.swipedeck.SwipeDeck
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity(), CardInterractionListener {

    private val quotes = ArrayList<Quote>()
    private var adapter: QuotesAdapter? = null
    private var favorites = false
    private var swipeView: SwipeDeck? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        initList()
    }

    override fun onShare(quote: Quote) {
        share(quote)
    }

    override fun onSkip() {
        swipeView!!.swipeTopCardLeft(300)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {

            R.id.navigation_home -> {
                favorites = false
                initList()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorites -> {
                favorites = true
                initList()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_menu -> {

                startActivity(Intent(this, MenuActivity::class.java))
            }
        }
        false
    }

    private fun initList() {
        swipe_container.removeAllViews()
        swipeView = SwipeDeck(this@MainActivity, null)
        swipeView!!.setPadding(resources.getDimensionPixelOffset(R.dimen.padding_cards_left_right),
                resources.getDimensionPixelOffset(R.dimen.padding_cards_top),
                resources.getDimensionPixelOffset(R.dimen.padding_cards_left_right),
                resources.getDimensionPixelOffset(R.dimen.padding_cards_bottom))
        container.destroyDrawingCache()

        quotes.clear()
        if (favorites) {
            quotes.addAll(QuoteDAO.getAll())
        } else {
            for (i in 0..10) {
                quotes!!.add(getQuote())
            }
        }
        adapter = QuotesAdapter(quotes, this@MainActivity, this)
        swipeView!!.setAdapter(adapter)
        adapter!!.notifyDataSetChanged()
        swipeView!!.adapterIndex = 0
        swipeView!!.setCallback(object : SwipeDeck.SwipeDeckCallback {
            override fun isDragEnabled(itemId: Long): Boolean {
                return true
            }

            override fun cardSwipedLeft(stableId: Long) {
                if (!favorites) {
                    quotes!!.add(getQuote())
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun cardSwipedRight(stableId: Long) {
                if (!favorites) {
                    quotes!!.add(getQuote())
                    adapter!!.notifyDataSetChanged()
                }
            }

        })
        swipe_container.addView(swipeView)
    }

    private fun share(quote: Quote) {
        val image = getQuoteImage(quote)
        try {

            val cachePath = File(cacheDir, "images")
            cachePath.mkdirs() // don't forget to make the directory
            val stream = FileOutputStream("$cachePath/image.png") // overwrites this image every time
            image.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }

        val imagePath = File(cacheDir, "images")
        val newFile = File("$imagePath/image.png")
        val contentUri = FileProvider.getUriForFile(this, "io.exera.quotz.fileprovider", newFile)

        if (contentUri != null) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, contentResolver.getType(contentUri))
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
            startActivity(Intent.createChooser(shareIntent, getString(R.string.choose_an_app)))
        }

    }
}
