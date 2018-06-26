package io.exera.quotz.database

import android.annotation.SuppressLint
import io.realm.Realm

class QuoteDAO {

    companion object {

        @SuppressLint("StaticFieldLeak")
        val realm = Realm.getDefaultInstance()

        fun add(quote: Quote) {
            realm.executeTransaction {
                realm.insert(quote)
            }
        }

        fun deleteAll() {
            realm.executeTransaction {
                realm.deleteAll()
            }
        }

        fun remove(quote: Quote) {
            realm.executeTransaction {
                val quoteRef = realm.where(Quote::class.java).equalTo("id", quote.getId()).findFirst()
                quoteRef!!.deleteFromRealm()
            }
        }

        fun getAll(): ArrayList<Quote> {
            val quoteList = ArrayList<Quote>()
            val quotes = realm.where(Quote::class.java).findAll()
            for (q in quotes) {
                quoteList.add(Quote.copyQuote(q))
            }
            return quoteList
        }

        fun getById(id: Int): Quote? {
            val goin = realm.where(Quote::class.java).equalTo("id", id).findFirst()
            return goin
        }

    }

}