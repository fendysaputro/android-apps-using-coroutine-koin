package id.phephen.coroutinekoinandpagination.utils

import id.phephen.coroutinekoinandpagination.model.Filters
import java.lang.IllegalStateException

/**
 * Created by Phephen on 13/05/2022.
 */

fun convertFilterToIndex(filter: Filters.ResultSearchUsers) = when (filter) {
    Filters.ResultSearchUsers.BY_SCORE -> 2
    Filters.ResultSearchUsers.BY_FOLLOWERS -> 1
    Filters.ResultSearchUsers.BY_REPOS -> 0
}

fun convertIndexToFilter(index: Int) = when (index) {
    2 -> Filters.ResultSearchUsers.BY_SCORE
    1 -> Filters.ResultSearchUsers.BY_FOLLOWERS
    0 -> Filters.ResultSearchUsers.BY_REPOS
    else -> throw IllegalStateException("Index not recognized")
}