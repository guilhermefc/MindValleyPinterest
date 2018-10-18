package br.com.gfc.mindvalley_loading_lib.loading_lib.model

import br.com.gfc.mindvalley_loading_lib.loading_lib.interfaces.DownloadStatusInterface

open class Downloadable(
    var byteArray: ByteArray? = null,
    val url: String,
    val dataType: DataType,
    val statusListener: DownloadStatusInterface
)