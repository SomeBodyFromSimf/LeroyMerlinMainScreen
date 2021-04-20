package com.mihailchistousov.leroymerlin.screens.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.mihailchistousov.leroymerlin.R
import com.mihailchistousov.leroymerlin.customview.SpeedyLinearLayoutManager
import com.mihailchistousov.leroymerlin.screens.main.adapter.CategoryAdapter
import com.mihailchistousov.leroymerlin.screens.main.adapter.ProductAdapter
import kotlinx.android.synthetic.main.main.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.search_toolbar.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay


class MainFragment: Fragment(R.layout.main) {

    private val viewModel: MainVM by viewModels()

    private lateinit var catalogAdapter: CategoryAdapter
    private lateinit var recentlyViewedAdapter: ProductAdapter
    private lateinit var limitedOfferAdapter: ProductAdapter
    private lateinit var bestPriceAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.isRefreshing = false
        }
        catalogAdapter = CategoryAdapter(requireContext()).apply {
            onCatalogClick = { Toast.makeText(context,"Нужно перейти во все категории", Toast.LENGTH_SHORT).show()}
            onAllClick = { Toast.makeText(context,"Нужно перейти во все категории", Toast.LENGTH_SHORT).show()}
            onItemClick = {id, title -> Toast.makeText(context,"Нажали категорию $title, у нее id: $id", Toast.LENGTH_SHORT).show()}
        }
        recentlyViewedAdapter = ProductAdapter(requireContext()).apply {
            onItemClick = {Toast.makeText(context,"Нажали на недавний товар ${it.title}, у него id: ${it.id}", Toast.LENGTH_SHORT).show()}
        }
        limitedOfferAdapter = ProductAdapter(requireContext()).apply { onItemClick = viewModel::addToRecent }
        bestPriceAdapter = ProductAdapter(requireContext()).apply { onItemClick = viewModel::addToRecent }
        catalogList.layoutManager = SpeedyLinearLayoutManager(context)
        catalogList.adapter = catalogAdapter
        catalogList.setHasFixedSize(true)
        catalogList.addOnScrollListener (object: RecyclerView.OnScrollListener() {
            var currentState = RecyclerView.SCROLL_STATE_IDLE
            val manager =  catalogList.layoutManager as? SpeedyLinearLayoutManager
            var sliderJob: Job? = null

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                currentState = newState
                sliderJob?.cancel()
                sliderJob = null
                when(newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        sliderJob = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                            delay(200)
                            if(currentState==RecyclerView.SCROLL_STATE_IDLE && catalogAdapter.itemCount-1 != (manager?.findLastCompletelyVisibleItemPosition()?:0))
                                catalogList.smoothScrollToPosition(manager?.findFirstVisibleItemPosition() ?: 0)
                        }
                    }
                }
            }
        })
        rvRecentlyViewed.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvRecentlyViewed.adapter = recentlyViewedAdapter
        rvLimitedOffer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvLimitedOffer.adapter = limitedOfferAdapter
        rvBestPrice.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvBestPrice.adapter = bestPriceAdapter
        search.setOnClickListener {goToSearch()}
        btnSearch.setOnClickListener {goToSearch()}
        btnScan.setOnClickListener { Toast.makeText(context, "Переходим к сканеру", Toast.LENGTH_SHORT).show() }
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            swipeToRefresh.isEnabled = verticalOffset == 0
        })
        observeData()
    }

    private fun observeData() {
        viewModel.catalog.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()) { catalogAdapter.submitList(it) }
        }
        viewModel.recentlyViewed.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()) { llRecentlyViewed.isVisible=true; recentlyViewedAdapter.submitList(it) }
        }
        viewModel.limitedOffer.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()) { limitedOfferAdapter.submitList(it) }
        }
        viewModel.bestPrice.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()) { bestPriceAdapter.submitList(it) }
        }
    }

    private fun goToSearch() {
        if(app_bar.isLifted) {
            findNavController().navigate(R.id.to_search)
        } else {
            val extras = FragmentNavigatorExtras(search to "searchField", btnSearch to "searchBtn", searchIcon to "searchBtnIcon")
            findNavController().navigate(R.id.to_search, bundleOf("addShared" to true), null, extras)
        }
    }
}