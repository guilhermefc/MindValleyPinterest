package br.com.gfc.mindvalley_loading_lib.loading_lib.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import br.com.gfc.mindvalley_loading_lib.loading_lib.interfaces.DownloadStatusInterface

class DownloadableImage(
    url: String,
    statusListener: DownloadStatusInterface
) : Downloadable(null, url, DataType.IMAGE, statusListener) {

    fun getImageBitmap(): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
    }


}