package br.com.gfc.mindvalley_loading_lib.loading_lib.interfaces

import br.com.gfc.mindvalley_loading_lib.loading_lib.model.Downloadable

interface DownloadStatusInterface {
    fun onSuccess(downloadable: Downloadable)
    fun onError(throwable: Throwable)
}