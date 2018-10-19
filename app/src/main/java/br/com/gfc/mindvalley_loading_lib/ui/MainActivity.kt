package br.com.gfc.mindvalley_loading_lib.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import br.com.gfc.mindvalley_loading_lib.R
import br.com.gfc.mindvalley_loading_lib.ui.about.AboutFragment
import br.com.gfc.mindvalley_loading_lib.ui.home.HomeFragment

import br.com.mercafacil.app_mercafacil.view.commom.BaseActivity
import br.com.mercafacil.app_mercafacil.view.commom.BaseViewModel
import kotlinx.android.synthetic.main.activity_home.*

class MainActivity : BaseActivity<BaseViewModel>(), BottomNavigationView.OnNavigationItemSelectedListener {
    companion object {
        fun launch(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            return context.startActivity(intent)
        }

    }


    override fun getLayoutId(): Int = R.layout.activity_home

    override fun getContainerId(): Int = R.id.container

    override fun getViewModel(): BaseViewModel? = ViewModelProviders.of(this).get(BaseViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) replaceFragment(HomeFragment.newInstance())
        initNavigation()
    }

    private fun initNavigation() {
        navigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigation_home -> {
                replaceFragment(HomeFragment.newInstance(), true, BaseActivity.AnimationStyle.TO_BACK)
                return true
            }
            R.id.navigation_about -> {
                replaceFragment(AboutFragment.newInstance(), true, BaseActivity.AnimationStyle.TO_FRONT)
                return true
            }
        }
        return false
    }
}
