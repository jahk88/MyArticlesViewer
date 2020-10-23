package com.jahk.myarticlesviewer.ui.article_viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jahk.myarticlesviewer.R
import kotlinx.android.synthetic.main.fragment_article_viewer.view.*
import kotlinx.android.synthetic.main.fragment_home.*

class ArticleViewerFragment : Fragment() {

//    val viewModel: HomeViewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_article_viewer, container, false)
        val safeArgs: ArticleViewerFragmentArgs by navArgs()
        safeArgs.url.let {
            root.web_content.loadUrl(it)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionbar = (activity as AppCompatActivity).supportActionBar
        actionbar?.let { it.title = resources.getString(R.string.back_text) }
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

}