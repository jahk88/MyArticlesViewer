package com.jahk.myarticlesviewer.domain

import com.jahk.myarticlesviewer.network.HighlightResult
import java.io.Serializable

data class HomeModel(val _highlightResult: HighlightResult? = null,
                     val _tags: List<String>? = null,
                     val author: String? = null,
                     val comment_text: String? = null,
                     val created_at: String? = null,
                     val created_at_i: Int? = null,
                     val objectID: String? = null,
                     val parent_id: Int? = null,
                     val story_id: Int? = null,
                     val story_text: String? = null,
                     val story_title: String? = null,
                     val story_url: String? = null
)