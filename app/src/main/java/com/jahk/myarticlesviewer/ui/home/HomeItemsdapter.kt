package com.jahk.myarticlesviewer.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.jahk.myarticlesviewer.R
import com.jahk.myarticlesviewer.domain.HomeModel
import com.jahk.myarticlesviewer.utils.getTimeAgo
import com.jahk.myarticlesviewer.utils.inflate
import kotlinx.android.synthetic.main.home_item.view.*


class HomeItemsdapter(private val viewModel: HomeViewModel, @NonNull val items: List<HomeModel>) : RecyclerView.Adapter<HomeItemsdapter.ViewHolder>() {

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(viewModel, items[position])
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.home_item))
    override fun getItemId(position: Int): Long = items[position].objectID?.toLong() ?: 0L

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(viewModel: HomeViewModel, homeItem: HomeModel) {
            var timeAgo: String = ""

            homeItem.created_at_i?.let {
                timeAgo = " - ${getTimeAgo(it.toLong()) ?: ""}"
            }

            val description = "${homeItem.author}" + timeAgo
            itemView.tvTitle.text = homeItem.story_title
            itemView.tvDescription.text = description

            itemView.setOnClickListener { viewModel.itemSelected(homeItem) }
        }
    }
}