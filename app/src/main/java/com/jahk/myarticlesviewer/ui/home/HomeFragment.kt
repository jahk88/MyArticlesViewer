package com.jahk.myarticlesviewer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jahk.myarticlesviewer.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    val viewModel: HomeViewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionbar = (activity as AppCompatActivity).supportActionBar
        actionbar?.let {it.title = resources.getString(R.string.app_name) }

        viewModel.homeItems.observe(viewLifecycleOwner, Observer {homeItemsList ->
            if (!homeItemsList.isEmpty()) {
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

        viewModel.getHomeItems()
    }

}