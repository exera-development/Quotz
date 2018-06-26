package io.exera.quotz.tools

import android.content.Context
import android.util.Log
import io.exera.quotz.database.Quote
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*


var quotes: JSONArray? = null

/**
 * initializes the quotes JSON array, from this we will get a random quote JSON object
 */
fun Context.InitQuotes() {
    var reader: BufferedReader? = null
    try {
        reader = BufferedReader(
                InputStreamReader(assets.open("quotes.json"), "UTF-8"))

        var text = ""
        var line = reader.readLine()
        while (line != null) {
            text += line
            line = reader.readLine()
        }
        quotes = JSONArray(text)
    } catch (e: IOException) {
        //log the exception
        Log.e("QUOTE", e.toString())
    } finally {
        if (reader != null) {
            try {
                reader.close()
            } catch (e: IOException) {
                //log the exception
            }
        }
    }
}

private fun getRandomQuoteJSON(): JSONObject {
    val random = Random()

    val number = random.nextInt(1639)
    val quote = quotes!!.getJSONObject(number)
//    message.text = quote.getString("quoteText")
//    author.text = "Author\n${quote.getString("quoteAuthor")}"
//    Log.d("quote", "number: $number")
    return quote
}


fun getQuote(): Quote {
    val quote = Quote()
    val quoteJSON = getRandomQuoteJSON()
    quote.text = quoteJSON.getString("quoteText")
    quote.author = quoteJSON.getString("quoteAuthor")
    return quote
}