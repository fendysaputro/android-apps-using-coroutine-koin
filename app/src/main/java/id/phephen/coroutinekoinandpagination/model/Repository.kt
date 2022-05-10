package id.phephen.coroutinekoinandpagination.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Phephen on 10/05/2022.
 */
data class Repository(
    @SerializedName("stargazers_count")
    val numberStars: Int
)
