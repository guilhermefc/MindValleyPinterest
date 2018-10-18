package br.com.gfc.mindvalley_loading_lib.loading_lib.model

import br.com.gfc.mindvalley_loading_lib.loading_lib.interfaces.DownloadStatusInterface
import java.nio.charset.Charset

class DownloadableJson(
    url: String,
    statusListener: DownloadStatusInterface
) : Downloadable(null, url, DataType.JSON, statusListener) {

    fun getJsonText(): String {
        if (byteArray == null) return ""

        try {
            return String(super.byteArray!!, Charset.defaultCharset())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }
}