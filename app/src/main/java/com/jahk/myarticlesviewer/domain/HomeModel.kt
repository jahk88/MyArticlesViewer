package com.jahk.myarticlesviewer.domain

import com.jahk.myarticlesviewer.database.HitDb
import com.jahk.myarticlesviewer.network.HighlightResult
import java.io.Serializable

data class HomeModel(
                     var id: Long = 0,
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

fun HomeModel.toHitDb(): HitDb {
    return HitDb(
                id = this.id,
                author = this.author,
                comment_text = this.comment_text,
                created_at = this.created_at,
                created_at_i = this.created_at_i,
                // created_at_d = it.created_at?.toDate(),
                objectID = this.objectID,
                parent_id = this.parent_id,
                story_id = this.story_id,
                story_text = this.story_text,
                story_title = this.story_title,
                story_url = this.story_url
            )
}