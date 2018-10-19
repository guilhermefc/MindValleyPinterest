package br.com.gfc.mindvalley_loading_lib.ui.home.adapter

import android.graphics.Bitmap
import br.com.gfc.mindvalley_loading_lib.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class ImagesAdapter:  BaseQuickAdapter<Bitmap ,BaseViewHolder>(R.layout.item_images_recycler){
    override fun convert(helper: BaseViewHolder?, item: Bitmap?) {
        helper?.setImageBitmap(R.id.img, item)
    }
}