package io.exera.quotz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.close_slide_in, R.anim.close_slide_out)
    }
}
