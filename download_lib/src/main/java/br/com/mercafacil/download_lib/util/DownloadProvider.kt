package br.com.gfc.mindvalley_loading_lib.loading_lib.util

import android.annotation.SuppressLint
import android.util.Log
import br.com.gfc.mindvalley_loading_lib.loading_lib.model.Downloadable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*


object DownloadProvider {
    private val okHttpClient = OkHttpClient()

    private var maxCache: Int = obtainMaxCache()
    private var currentUsedCache: Int = 0

    private var mCacheResponse: HashMap<String, ByteArray> = HashMap()
    private var mCacheQueue = PriorityQueue<String>()


    fun init(downloadable: Downloadable) {
        if (hasOnCache(downloadable))
        else startDownload(downloadable)
    }

    fun cancelRequest(downloadable: Downloadable) {
        okHttpClient.dispatcher().queuedCalls().forEach {
            if (it.request().url().toString() == downloadable.url) {
                it.cancel()
                return
            }
        }
        okHttpClient.dispatcher().runningCalls().forEach {
            if (it.request().url().toString() == downloadable.url) {
                it.cancel()
                return
            }
        }
    }

    fun changeCacheSize(newMaxSize: Int) {
        val maxSizeAvailable = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        if (newMaxSize > maxSizeAvailable) {
            throw RuntimeException("New value of cache is bigger than maximum cache available on system")
        } else {
            maxCache = newMaxSize
        }
    }

    private fun hasOnCache(downloadable: Downloadable): Boolean {
        if (mCacheResponse.containsKey(downloadable.url)) {
            Log.i(getTagName(), "return by cache")
            downloadable.byteArray = mCacheResponse[downloadable.url]
            downloadable.statusListener.onSuccess(downloadable)

            mCacheQueue.add(mCacheQueue.poll())
            return true
        }
        return false
    }

    @SuppressLint("CheckResult")
    private fun startDownload(downloadable: Downloadable) {
        val requestObservable: Observable<ByteArray> = Observable.create {
            try {
                val request = Request.Builder()
                    .url(downloadable.url)
                    .build()

                val response = okHttpClient.newCall(request).execute()
                val byteArray: ByteArray = response.body()!!.bytes()
                it.onNext(byteArray)

                tryToAddOnCache(downloadable.url, byteArray)
                Log.i(getTagName(), "return by HTTP")
            } catch (e: Exception) {
                it.onError(e)
            }
        }

        val disposable = requestObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnDispose({ Log.e(getTagName(), "I did dispose") })
            .subscribe({ response: ByteArray ->
                downloadable.byteArray = response
                downloadable.statusListener.onSuccess(downloadable)
            }, { t: Throwable? ->
                downloadable.statusListener.onError(t!!)
            })

    }

    private fun tryToAddOnCache(url: String, byteArray: ByteArray) {
        val totalCacheNeeded = byteArray.size + currentUsedCache
        when {
            totalCacheNeeded < maxCache -> {
                Log.i(getTagName(), "cache - item added")
                mCacheResponse[url] = byteArray
                mCacheQueue.add(url)
                currentUsedCache += byteArray.size
            }
            byteArray.size < maxCache -> {
                Log.i(getTagName(), "cache - removed last item from cache and retry")
                val sizeRemoved = mCacheResponse[mCacheQueue.poll()]?.size
                currentUsedCache -= sizeRemoved!!
                tryToAddOnCache(url, byteArray)
            }
            else -> Log.i(getTagName(), "cache - item too big to be added")
        }
    }

    private fun obtainMaxCache(): Int {
        return (Runtime.getRuntime().maxMemory() / 1024).toInt()
    }

    private fun getTagName(): String = DownloadProvider.javaClass.simpleName
}