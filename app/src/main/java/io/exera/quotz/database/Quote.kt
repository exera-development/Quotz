package io.exera.quotz.database

/**
 * Created by attilajanosi on 12/03/2018.
 */

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.realm.RealmObject
import java.util.*

open class Quote() : RealmObject(), Parcelable {

    private var id = UUID.randomUUID().toString()
    var text: String = ""
    var author: String = ""
    var startColor: String = ""
    var endcolor: String = ""
    var imageURL: String = ""
    var isFavorite: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        text = parcel.readString()
        author = parcel.readString()
        startColor = parcel.readString()
        endcolor = parcel.readString()
        imageURL = parcel.readString()
        isFavorite = parcel.readByte() != 0.toByte()
    }


    fun getId(): String {
        return this.id
    }


    fun getGradient(): GradientDrawable {
        return GradientDrawable(GradientDrawable.Orientation.BL_TR, intArrayOf(Color.parseColor(startColor),
                Color.parseColor(endcolor)))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(text)
        parcel.writeString(author)
        parcel.writeString(startColor)
        parcel.writeString(endcolor)
        parcel.writeString(imageURL)
        parcel.writeByte(if (isFavorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Quote> {
        override fun createFromParcel(parcel: Parcel): Quote {
            return Quote(parcel)
        }

        override fun newArray(size: Int): Array<Quote?> {
            return arrayOfNulls(size)
        }

        fun copyQuote(toCopy: Quote): Quote {
            val quote = Quote()
            quote.id = toCopy.id
            quote.text = toCopy.text
            quote.author = toCopy.author
            quote.startColor = toCopy.startColor
            quote.endcolor = toCopy.endcolor
            quote.isFavorite = toCopy.isFavorite

            return quote
        }
    }

}