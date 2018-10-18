package br.com.gfc.mindvalley_loading_lib.loading_lib.interfaces

interface DownloadStatusInterface {
    fun onQueue()
    fun onStart()
    fun onSuccess()
    fun onError()
}