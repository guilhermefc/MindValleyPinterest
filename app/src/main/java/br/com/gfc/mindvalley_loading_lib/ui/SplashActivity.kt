package br.com.gfc.mindvalley_loading_lib.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import br.com.gfc.mindvalley_loading_lib.R
import br.com.mercafacil.app_mercafacil.view.commom.BaseActivity
import br.com.mercafacil.app_mercafacil.view.commom.BaseViewModel

class SplashActivity: BaseActivity<BaseViewModel>(){
    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun getContainerId(): Int = 0

    override fun getViewModel(): BaseViewModel? = ViewModelProviders.of(this).get(BaseViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            MainActivity.launch(this)
            finish()
        }, 2500)
    }
}