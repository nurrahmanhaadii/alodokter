package id.haadii.alodoktersubmission.data.objectbox

import android.content.Context
import android.util.Log
import id.haadii.alodoktersubmission.BuildConfig
import id.haadii.alodoktersubmission.data.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {

    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext).build()

        if (BuildConfig.DEBUG) {
            Log.d(App.TAG, "Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()})")
        }
    }

}