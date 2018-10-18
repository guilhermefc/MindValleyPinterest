package br.com.gfc.mindvalley_loading_lib.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.gfc.mindvalley_loading_lib.R
import br.com.gfc.mindvalley_loading_lib.loading_lib.interfaces.DownloadStatusInterface
import br.com.gfc.mindvalley_loading_lib.loading_lib.model.Downloadable
import br.com.gfc.mindvalley_loading_lib.loading_lib.model.DownloadableImage
import br.com.gfc.mindvalley_loading_lib.loading_lib.model.DownloadableJson
import br.com.gfc.mindvalley_loading_lib.loading_lib.util.DownloadProvider

import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    val listUrls = arrayListOf(
        "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026fit=crop\\u0026h=32\\u0026w=32\\u0026s=63f1d805cffccb834cf839c719d91702",
        "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026fit=crop\\u0026h=64\\u0026w=64\\u0026s=ef631d113179b3137f911a05fea56d23",
        "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026fit=crop\\u0026h=128\\u0026w=128\\u0026s=622a88097cf6661f84cd8942d851d9a2",
        "https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026s=4b142941bfd18159e2e4d166abcd0705",
        "https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=entropy\\u0026w=1080\\u0026fit=max\\u0026s=1881cd689e10e5dca28839e68678f432"
    )

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        message.setOnClickListener {
            callProvider()
        }

        img.setOnClickListener {
            callProvider2()
        }
    }

    private fun callProvider2() {
        DownloadProvider.getDownloadable(DownloadableImage(listUrls[1], obtainListenerImage()))
    }

    private fun callProvider() {
        DownloadProvider.getDownloadable(DownloadableImage(listUrls[0], obtainListenerImage()))
        DownloadProvider.getDownloadable(DownloadableImage(listUrls[1], obtainListenerImage()))
        DownloadProvider.getDownloadable(DownloadableImage(listUrls[2], obtainListenerImage()))
        DownloadProvider.getDownloadable(DownloadableImage(listUrls[3], obtainListenerImage()))
        DownloadProvider.getDownloadable(DownloadableImage(listUrls[4], obtainListenerImage()))
        DownloadProvider.getDownloadable(DownloadableImage(listUrls[1], obtainListenerImage()))
        DownloadProvider.getDownloadable(DownloadableImage(listUrls[1], obtainListenerImage()))

    }

    private fun obtainListenerImage(): DownloadStatusInterface =
        object : DownloadStatusInterface {
            override fun onSuccess(downloadable: Downloadable) {
                val bitmap: Bitmap = (downloadable as DownloadableImage).getImageBitmap()
                img.setImageBitmap(bitmap)
            }

            override fun onError(throwable: Throwable) {
                Log.e("onSuccess", "erro :(", throwable)
            }
        }

    private fun obtainListenerJson(): DownloadStatusInterface =
        object : DownloadStatusInterface {
            override fun onSuccess(downloadable: Downloadable) {
                Log.e("onSuccess", "aa" + (downloadable as DownloadableJson).getJsonText())
            }

            override fun onError(throwable: Throwable) {
                Log.e("onSuccess", "erro :(", throwable)
            }
        }


}
