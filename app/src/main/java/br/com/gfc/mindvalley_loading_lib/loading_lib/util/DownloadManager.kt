package br.com.gfc.mindvalley_loading_lib.loading_lib.util

import br.com.gfc.mindvalley_loading_lib.loading_lib.model.Downloadable
import okhttp3.OkHttpClient
import okhttp3.Request


class DownloadManager {

    var client = OkHttpClient()

    fun startDownload(downloadable: Downloadable): String? {

        val request = Request.Builder()
            .url(downloadable.url)
            .build()

        val response = client.newCall(request).execute()
        if(response.isSuccessful){
            return response.body()?.string()
        } else {

        }

        return ""
    }

}