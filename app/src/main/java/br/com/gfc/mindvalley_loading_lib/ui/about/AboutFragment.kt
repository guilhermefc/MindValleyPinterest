package br.com.gfc.mindvalley_loading_lib.ui.about

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import br.com.gfc.mindvalley_loading_lib.R
import br.com.gfc.mindvalley_loading_lib.ui.MainViewModel
import br.com.mercafacil.app_mercafacil.view.commom.BaseFragment
import br.com.mercafacil.app_mercafacil.view.commom.BaseViewModel
import kotlinx.android.synthetic.main.fragment_about.*
import java.lang.StringBuilder


class AboutFragment : BaseFragment<BaseViewModel>() {

    override fun getViewModel(): MainViewModel? {
        return ViewModelProviders.of(getBaseActivity()!!).get(MainViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.fragment_about

    companion object {
        fun newInstance() = AboutFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        changeHeaderText()
        setupObservers()
    }

    private fun setupObservers() {
        getViewModel()?.liveDataEvents?.observe(this, Observer {
            val sb = StringBuilder()
            it?.forEach {
                sb.append(it, System.getProperty("line.separator"), System.getProperty("line.separator"))
            }
            text_logs.text = sb.toString()
        })
    }

    private fun changeHeaderText() {
        getBaseActivity()?.setTitle(R.string.logs_title)
    }

}
