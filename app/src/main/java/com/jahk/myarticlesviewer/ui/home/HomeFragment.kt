package com.jahk.myarticlesviewer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jahk.myarticlesviewer.R
import kotlinx.android.synthetic.main.fragment_home.*
class HomeFragment : Fragment() {

    val viewModel: HomeViewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel.homeItems.observe(viewLifecycleOwner, Observer {homeItemsList ->
            swipeRefresh.isRefreshing = false
            if (homeItemsList.isNotEmpty()) {
                viewModel.adapter = HomeItemsdapter(viewModel, homeItemsList)
                recycler_view.apply {
                    layoutManager = LinearLayoutManager(context)
                    activity?.let {
                        val dividerItemDecoration = DividerItemDecoration(it, RecyclerView.VERTICAL)
                        this.addItemDecoration(dividerItemDecoration)
                    }
                    adapter = viewModel.adapter
                }
            }
        })

        viewModel.homeItemSelected.observe(viewLifecycleOwner, Observer {homeModelItem ->
            if (viewModel.isHomeItemSelected.value == true) {
                homeModelItem?.story_url?.let {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToArticleViewerFragment(it)
                    findNavController().navigate(action)
                    viewModel.onItemSelectedShown()
                }
            }
        })
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionbar = (activity as AppCompatActivity).supportActionBar
        actionbar?.let {it.title = resources.getString(R.string.app_name) }

        viewModel.refreshDataFromRepository()
        swipeRefresh.setOnRefreshListener { viewModel.refreshDataFromRepository() }
    }
}

