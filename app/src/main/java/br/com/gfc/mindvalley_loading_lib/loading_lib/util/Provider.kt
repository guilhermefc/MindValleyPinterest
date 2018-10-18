package br.com.gfc.mindvalley_loading_lib.loading_lib.util

import br.com.gfc.mindvalley_loading_lib.loading_lib.model.Downloadable

class Provider private constructor() {
    private object Holder {
        val INSTANCE = Provider()
    }

    companion object {
        val INSTANCE: Provider by lazy { Holder.INSTANCE }
    }

    private var mCacheResponse: HashMap<String, ByteArray> = HashMap()


    fun initDownload(downloadable: Downloadable) {
        //check cache to check

        if(mCacheResponse.containsKey(downloadable.url)){
            downloadable.data = mCacheResponse[downloadable.url]!!
            downloadable.statusListener.onSuccess()
            return
        }

        startDownloadOfThis(downloadable)

    }

    private fun startDownloadOfThis(downloadable: Downloadable) {
        val downloadManager = DownloadManager()
        downloadManager.startDownload(downloadable)

    }
}