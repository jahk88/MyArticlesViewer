package com.jahk.myarticlesviewer.network

import com.jahk.myarticlesviewer.domain.HomeModel
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class MainResponse(
    val exhaustiveNbHits: Boolean? = null,
    val hits: List<Hit>? = null,
    val hitsPerPage: Int? = null,
    val nbHits: Int? = null,
    val nbPages: Int? = null,
    val page: Int? = null,
    val params: String? = null,
    val processingTimeMS: Int? = null,
    val query: String? = null
) : Serializable

@JsonClass(generateAdapter = true)
data class Hit(
    val _highlightResult: HighlightResult? = null,
    val _tags: List<String>? = null,
    val author: String? = null,
    val comment_text: String? = null,
    val created_at: String? = null,
    val created_at_i: Int? = null,
    val num_comments: Any? = null,
    val objectID: String? = null,
    val parent_id: Int? = null,
    val points: Any? = null,
    val story_id: Int? = null,
    val story_text: String? = null,
    val story_title: String? = null,
    val story_url: String? = null,
    val title: String? = null,
    val url: String? = null
) : Serializable

@JsonClass(generateAdapter = true)
data class HighlightResult(
    val author: Author? = null,
    val comment_text: CommentText? = null,
    val story_title: StoryTitle? = null,
    val story_url: StoryUrl? = null,
    val title: Title? = null,
    val url: Url? = null
) : Serializable

@JsonClass(generateAdapter = true)
data class Author(
    val fullyHighlighted: Boolean? = null,
    val matchLevel: String? = null,
    val matchedWords: List<String>? = null,
    val value: String? = null
) : Serializable

@JsonClass(generateAdapter = true)
data class CommentText(
    val fullyHighlighted: Boolean? = null,
    val matchLevel: String? = null,
    val matchedWords: List<String>? = null,
    val value: String? = null
) : Serializable

@JsonClass(generateAdapter = true)
data class StoryTitle(
    val matchLevel: String? = null,
    val matchedWords: List<Any>? = null,
    val value: String? = null
) : Serializable

@JsonClass(generateAdapter = true)
data class StoryUrl(
    val matchLevel: String? = null,
    val matchedWords: List<Any>? = null,
    val value: String? = null
) : Serializable

@JsonClass(generateAdapter = true)
data class Title(
    val matchLevel: String? = null,
    val matchedWords: List<Any>? = null,
    val value: String? = null
) : Serializable

@JsonClass(generateAdapter = true)
data class Url(
    val matchLevel: String? = null,
    val matchedWords: List<Any>? = null,
    val value: String? = null
) : Serializable

fun MainResponse.getHitsList(): List<HomeModel>? {
    return hits?.map {
        HomeModel(
            _highlightResult = it._highlightResult,
            _tags = it._tags,
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
