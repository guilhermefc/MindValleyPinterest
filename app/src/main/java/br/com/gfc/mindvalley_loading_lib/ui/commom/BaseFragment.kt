package br.com.mercafacil.app_mercafacil.view.commom

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    var mActivity: BaseActivity<BaseViewModel>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            this.mActivity = context as BaseActivity<BaseViewModel>?
        }
    }

    abstract fun getViewModel(): T?

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    fun getBaseActivity(): BaseActivity<BaseViewModel>? {
        return mActivity
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }

    fun getFragmentTag(): String {
        return this.javaClass.simpleName
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    fun hideKeyboard() {
        if (context == null || context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) == null || view == null)
            return

        val imm = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    fun showMsgToast(str: String) {
        getBaseActivity()?.showMsgToast(str)
    }

    fun showMsgSnack(str: String) {
        getBaseActivity()?.showMsgSnack(str)
    }

    fun handleError(throwable: Throwable) {
        getBaseActivity()?.handleError(throwable)
    }

}