package id.phephen.coroutinekoinandpagination.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Phephen on 10/05/2022.
 */
data class Result(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val items: List<User>
)
