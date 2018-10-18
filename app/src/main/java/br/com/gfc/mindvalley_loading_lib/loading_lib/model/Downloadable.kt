package br.com.gfc.mindvalley_loading_lib.loading_lib.model

import br.com.gfc.mindvalley_loading_lib.loading_lib.interfaces.DownloadStatusInterface
import java.util.*

data class Downloadable(
    var data: ByteArray,
    val url: String,
    val dataType: DataType,
    val statusListener: DownloadStatusInterface
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Downloadable

        if (!Arrays.equals(data, other.data)) return false
        if (url != other.url) return false
        if (dataType != other.dataType) return false
        if (statusListener != other.statusListener) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Arrays.hashCode(data)
        result = 31 * result + url.hashCode()
        result = 31 * result + dataType.hashCode()
        result = 31 * result + statusListener.hashCode()
        return result
    }
}