package io.exera.quotz

import android.app.Application
import io.exera.quotz.database.MyMigration
import io.realm.Realm
import io.realm.RealmConfiguration



class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .schemaVersion(2)
                .name("quote.realm")
                .migration( MyMigration())
                .build()
        Realm.setDefaultConfiguration(config)
    }

}