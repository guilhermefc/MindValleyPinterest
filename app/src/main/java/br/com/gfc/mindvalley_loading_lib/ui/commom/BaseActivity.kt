package br.com.mercafacil.app_mercafacil.view.commom

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import br.com.gfc.mindvalley_loading_lib.R


abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {
    private lateinit var mBaseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getContainerId(): Int

    abstract fun getViewModel(): T?



    fun replaceFragment(fragment: BaseFragment<BaseViewModel>, animate: Boolean, animationStyle: AnimationStyle) {
        supportFragmentManager.transact {
            if (animate) getAnimator(animationStyle)
            replace(getContainerId(), fragment)
        }
    }

    private fun FragmentTransaction.getAnimator(animationStyle: AnimationStyle) {
        when(animationStyle){
            AnimationStyle.TO_FRONT -> setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            AnimationStyle.TO_BACK -> setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
        }
    }

    fun replaceFragment(fragment: BaseFragment<BaseViewModel>) {
        replaceFragment(fragment, false, AnimationStyle.TO_FRONT)
    }

    /**
     * The `fragment` is added to the container view with tag. The operation is
     * performed by the `fragmentManager`.
     */
    fun replaceAndBackstack(fragment: BaseFragment<*>, animate: Boolean, animationStyle: AnimationStyle) {
        supportFragmentManager.transact {
            if (animate) {

            }

            add(getContainerId(), fragment, this.javaClass.simpleName)
            addToBackStack(fragment.getFragmentTag())
        }
    }

    fun replaceAndBackstack(fragment: BaseFragment<*>) {
        replaceAndBackstack(fragment, false, AnimationStyle.TO_FRONT)
    }

    fun setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
        setSupportActionBar(findViewById(toolbarId))
        supportActionBar?.run {
            action()
        }
    }

    /**
     * Runs a FragmentTransaction, then calls commit().
     */
    private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
        beginTransaction().apply {
            action()
        }.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun showMsgToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()
    }

    fun showMsgSnack(str: String) {
        Snackbar.make(findViewById(getContainerId()), str, Snackbar.LENGTH_LONG).show()
    }

    fun getTag(): String {
        return this.javaClass.simpleName
    }

    fun handleError(throwable: Throwable) {

    }

    enum class AnimationStyle {
        TO_FRONT, TO_BACK
    }

}