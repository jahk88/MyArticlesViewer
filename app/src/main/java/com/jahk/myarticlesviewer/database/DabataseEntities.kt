package com.jahk.myarticlesviewer.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jahk.myarticlesviewer.domain.HomeModel
import com.jahk.myarticlesviewer.network.HighlightResult
import com.jahk.myarticlesviewer.utils.TimestampConverter
import java.util.*

@Entity
data class HitDb(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val author: String? = null,
    val comment_text: String? = null,
    val created_at: String? = null,
    /*@NonNull
    @TypeConverters(TimestampConverter::class)
    val created_at_d: Date?,*/
    val created_at_i: Int? = null,
    val objectID: String? = null,
    val parent_id: Int? = null,
    val story_id: Int? = null,
    val story_text: String? = null,
    val story_title: String? = null,
    val story_url: String? = null
)

fun List<HitDb>.asDomainModel(): List<HomeModel> {
    return map {
        HomeModel(
            author = it.author,
            comment_text = it.comment_text,
            created_at = it.created_at,
            created_at_i = it.created_at_i,
            objectID = it.objectID,
            parent_id = it.parent_id,
            story_id = it.story_id,
            story_text = it.story_text,
            story_title = it.story_title,
            story_url = it.story_url
            )
    }
}