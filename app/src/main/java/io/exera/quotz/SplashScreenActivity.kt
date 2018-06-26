package io.exera.quotz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.exera.quotz.tools.InitQuotes


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InitQuotes()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
