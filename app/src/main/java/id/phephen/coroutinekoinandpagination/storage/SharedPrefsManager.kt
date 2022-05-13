package id.phephen.coroutinekoinandpagination.storage

import android.content.Context
import android.content.SharedPreferences
import id.phephen.coroutinekoinandpagination.extensions.setValue
import id.phephen.coroutinekoinandpagination.model.Filters
import java.lang.IllegalStateException

/**
 * Created by Phephen on 13/05/2022.
 */
class SharedPrefsManager(private val context: Context) {

    private fun get(): SharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)


    fun getFilterWhenSearchingUsers(): Filters.ResultSearchUsers {
        val value = get().getString(KEY_FILTERS, Filters.ResultSearchUsers.BY_SCORE.value)
        return when (value) {
            Filters.ResultSearchUsers.BY_SCORE.value -> Filters.ResultSearchUsers.BY_SCORE
            Filters.ResultSearchUsers.BY_REPOS.value -> Filters.ResultSearchUsers.BY_REPOS
            Filters.ResultSearchUsers.BY_FOLLOWERS.value -> Filters.ResultSearchUsers.BY_FOLLOWERS
            else -> throw IllegalStateException("Filter not recognized")
        }
    }

    fun saveFilterWhenSearchingUsers(filters: Filters.ResultSearchUsers) {
        get().setValue(KEY_FILTERS, filters.value)
    }

    companion object {
        private const val SP_NAME = "coroutinekoinandpagination"
        private const val KEY_FILTERS = "KEY_FILTERS"
    }

}