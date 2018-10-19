package br.com.gfc.mindvalley_loading_lib.ui

import android.arch.lifecycle.MutableLiveData
import android.graphics.Bitmap
import android.util.Log
import br.com.gfc.mindvalley_loading_lib.loading_lib.interfaces.DownloadStatusInterface
import br.com.gfc.mindvalley_loading_lib.loading_lib.model.Downloadable
import br.com.gfc.mindvalley_loading_lib.loading_lib.model.DownloadableImage
import br.com.gfc.mindvalley_loading_lib.loading_lib.util.DownloadProvider
import br.com.mercafacil.app_mercafacil.view.commom.BaseViewModel

class MainViewModel : BaseViewModel() {
    private val mutableEventList: MutableList<String> = ArrayList()
    var mutableBitmapList: MutableList<Bitmap>? = null
    val liveDataBitmaps: MutableLiveData<List<Bitmap>> = MutableLiveData()
    val liveDataEvents: MutableLiveData<List<String>> = MutableLiveData()

    fun doRequestImages() {
        addEvent("Function doRequestImages called")
        addEvent("Take a look on logcat to see library logs (cache or download)")
        mutableBitmapList = ArrayList()

        val urlImagesList = getMockUrlImages()
        addEvent("it will search for " + urlImagesList.size + "images")

        urlImagesList.forEach {
            val downloadable = DownloadableImage(it, getListener())
            DownloadProvider.init(downloadable)
            addEvent("DownloadProvider init: " + downloadable.url)
        }
    }

    private fun getMockUrlImages(): ArrayList<String> {
        val stringList = arrayListOf<String>(
            "https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026w=400\\u0026fit=max\\u0026s=d5682032c546a3520465f2965cde1cec",
            "https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026w=400\\u0026fit=max\\u0026s=d5682032c546a3520465f2965cde1cec",
            "https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026w=400\\u0026fit=max\\u0026s=d5682032c546a3520465f2965cde1cec",
            "https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026w=400\\u0026fit=max\\u0026s=d5682032c546a3520465f2965cde1cec",
            "https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026w=400\\u0026fit=max\\u0026s=d5682032c546a3520465f2965cde1cec",
            "https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026w=400\\u0026fit=max\\u0026s=d5682032c546a3520465f2965cde1cec",
            "https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026w=400\\u0026fit=max\\u0026s=d5682032c546a3520465f2965cde1cec",
            "https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026w=400\\u0026fit=max\\u0026s=d5682032c546a3520465f2965cde1cec"
        )
        return stringList
    }

    private fun getListener(): DownloadStatusInterface {
        return object : DownloadStatusInterface {
            override fun onSuccess(downloadable: Downloadable) {
                addEvent("DownloadProvider successful: " + downloadable.url)
                mutableBitmapList?.add((downloadable as DownloadableImage).getImageBitmap())
                liveDataBitmaps.value = mutableBitmapList
            }

            override fun onError(throwable: Throwable) {
                addEvent("DownloadProvider error: " + throwable.cause)
                Log.e(MainViewModel::class.java.simpleName, "Error", throwable)
            }
        }
    }

    private fun addEvent(event: String) {
        mutableEventList.add(event)
        liveDataEvents.value = mutableEventList
    }

}