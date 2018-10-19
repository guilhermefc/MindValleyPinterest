package br.com.gfc.mindvalley_loading_lib.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import br.com.gfc.mindvalley_loading_lib.R
import br.com.gfc.mindvalley_loading_lib.ui.MainViewModel
import br.com.gfc.mindvalley_loading_lib.ui.home.adapter.ImagesAdapter
import br.com.mercafacil.app_mercafacil.view.commom.BaseFragment
import br.com.mercafacil.app_mercafacil.view.commom.BaseViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment<BaseViewModel>() {

    private lateinit var imagesAdapter: ImagesAdapter

    override fun getViewModel(): MainViewModel? {
        return ViewModelProviders.of(getBaseActivity()!!).get(MainViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.fragment_home
    companion object {
        fun newInstance() = HomeFragment()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)
        setupImagesAdapter()
        changeToolbarTitle()
        getViewModel()?.doRequestImages()
        setupObservers()
        setupSwipeRefreshView()
    }

    private fun setupSwipeRefreshView() {

        swipe_container.setOnRefreshListener {
            getViewModel()?.doRequestImages()
        }

        swipe_container.setColorSchemeResources(
            R.color.colorPrimary,
            R.color.colorPrimaryDark,
            R.color.colorAccent,
            android.R.color.holo_red_light
        )

    }

    private fun setupObservers() {
        getViewModel()?.liveDataBitmaps?.observe(this, Observer {
            imagesAdapter.setNewData(it)
            swipe_container.isRefreshing = false
        })
    }

    private fun changeToolbarTitle() {
        getBaseActivity()?.setTitle(R.string.app_name)
    }

    private fun setupImagesAdapter() {
        imagesAdapter = ImagesAdapter()
        imagesAdapter.bindToRecyclerView(recycler)
        recycler.layoutManager = GridLayoutManager(getBaseActivity(), 2)
        recycler.adapter = imagesAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_refresh) {
            swipe_container.isRefreshing = true
            imagesAdapter.setNewData(null)
            getViewModel()?.doRequestImages()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
