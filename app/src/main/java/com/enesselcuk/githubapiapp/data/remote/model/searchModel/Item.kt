package com.enesselcuk.githubapiapp.data.remote.model.searchModel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enesselcuk.githubapiapp.data.local.favorite.FavoriteEntity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "githubEntity")
@Parcelize
data class Item(
    @PrimaryKey @field:SerializedName("id") val id: Int? = null,
    @field:SerializedName("avatar_url") val avatar_url: String? = null,
    @field:SerializedName("events_url") val events_url: String? = null,
    @field:SerializedName("followers_url") val followers_url: String? = null,
    @field:SerializedName("following_url") val following_url: String? = null,
    @field:SerializedName("gists_url") val gists_url: String? = null,
    @field:SerializedName("gravatar_id") val gravatar_id: String? = null,
    @field:SerializedName("html_url") val html_url: String? = null,
    @field:SerializedName("login") val login: String? = null,
    @field:SerializedName("node_id") val node_id: String? = null,
    @field:SerializedName("organizations_url") val organizations_url: String? = null,
    @field:SerializedName("received_events_url") val received_events_url: String? = null,
    @field:SerializedName("repos_url") val repos_url: String? = null,
    @field:SerializedName("score") val score: Int? = null,
    @field:SerializedName("site_admin") val site_admin: Boolean? = null,
    @field:SerializedName("starred_url") val starred_url: String? = null,
    @field:SerializedName("subscriptions_url") val subscriptions_url: String? = null,
    @field:SerializedName("type") val type: String? = null,
    @field:SerializedName("url") val url: String? = null,

    var liked: Boolean = false

) : Parcelable {
    fun toFavoriteMap(): FavoriteEntity {
        return FavoriteEntity(
            id = id,
            avatar_url = avatar_url,
            events_url = events_url,
            followers_url = followers_url,
            following_url = following_url,
            gists_url = gists_url,
            gravatar_id = gravatar_id,
            html_url = html_url,
            login = login,
            node_id = node_id,
            organizations_url = organizations_url,
            received_events_url = received_events_url,
            repos_url = repos_url,
            score = score,
            site_admin = site_admin,
            starred_url = starred_url,
            subscriptions_url = subscriptions_url,
            type = type,
            url = url,
            liked = liked
        )
    }
}