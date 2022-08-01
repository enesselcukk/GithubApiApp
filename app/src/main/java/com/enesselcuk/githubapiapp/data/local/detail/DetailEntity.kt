package com.enesselcuk.githubapiapp.data.local.detail

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enesselcuk.githubapiapp.data.remote.model.detailResponse.DetailResponse

@Entity(tableName = "detailEntity")
data class DetailEntity(
    @PrimaryKey val id: Int? = null,
    val avatar_url: String? = null,
    val bio: String? = null,
    val blog: String? = null,
    val company: String? = null,
    val created_at: String? = null,
    val email: String? = null,
    val events_url: String? = null,
    val followers: Int? = null,
    val followers_url: String? = null,
    val following: Int? = null,
    val following_url: String? = null,
    val gists_url: String? = null,
    val gravatar_id: String? = null,
    val hireable: String? = null,
    val html_url: String? = null,
    val location: String? = null,
    val login: String? = null,
    val name: String? = null,
    val node_id: String? = null,
    val organizations_url: String? = null,
    val public_gists: Int? = null,
    val public_repos: Int? = null,
    val received_events_url: String? = null,
    val repos_url: String? = null,
    val site_admin: Boolean? = null,
    val starred_url: String? = null,
    val subscriptions_url: String? = null,
    val twitter_username: String? = null,
    val type: String? = null,
    val updated_at: String? = null,
    val url: String? = null,

    ){
    fun toDetailResponse() : DetailResponse{
        return DetailResponse(
            id = id,
            avatar_url = avatar_url,
            bio = bio,
            blog = blog,
            company = company,
            created_at = created_at,
            email = email,
            events_url = events_url,
            followers = followers,
            followers_url = followers_url,
            following = following,
            following_url = following_url,
            gists_url = gists_url,
            gravatar_id = gravatar_id,
            hireable = hireable,
            html_url = html_url,
            location = location,
            login = login,
            name = name,
            node_id = node_id,
            organizations_url = organizations_url,
            public_gists = public_gists,
            public_repos = public_repos,
            received_events_url = received_events_url,
            repos_url = repos_url,
            site_admin = site_admin,
            starred_url = starred_url,
            subscriptions_url = subscriptions_url,
            twitter_username = twitter_username,
            type = type,
            updated_at = updated_at,
            url = url
        )
    }
}
