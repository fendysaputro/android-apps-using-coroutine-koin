package id.phephen.coroutinekoinandpagination.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Phephen on 10/05/2022.
 */
data class User(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("followers")
    val totalFollowers: Int,
    @SerializedName("public_repos") var totalRepos: Int,
    @Transient var totalStars: Int,
    @Transient var followers: List<User>
)
