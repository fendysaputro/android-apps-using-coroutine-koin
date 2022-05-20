package id.phephen.coroutinekoinandpagination.ui.user.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.phephen.coroutinekoinandpagination.api.NetworkState
import id.phephen.coroutinekoinandpagination.base.BaseViewModel
import id.phephen.coroutinekoinandpagination.model.Filters
import id.phephen.coroutinekoinandpagination.pagination.datasource.UserDataSourceFactory
import id.phephen.coroutinekoinandpagination.repository.UserRepository
import id.phephen.coroutinekoinandpagination.storage.SharedPrefsManager

/**
 * Created by Phephen on 20/05/2022.
 */
class SearchUserViewModel(repository: UserRepository, private val sharedPrefsManager: SharedPrefsManager): BaseViewModel() {

    private val userDataSource = UserDataSourceFactory(repository = repository, scope = ioScope)

    val users = LivePagedListBuilder(userDataSource, pagedListConfig()).build()
    val networkState: LiveData<NetworkState>? = switchMap(userDataSource.source) {
        it.getNetworkState()
    }

    fun fetchUsersByName(query: String) {
        val search = query.trim()
        if (userDataSource.getQuery() == search) return
        userDataSource.updateQuery(search, sharedPrefsManager.getFilterWhenSearchingUsers().value)
    }

    fun refreshFailedRequest() =
        userDataSource.getSource()?.retryFailedQuery()

    fun refreshAllList() =
        userDataSource.getSource()?.refresh()

    fun getFilterWhenSearchingUsers() =
        sharedPrefsManager.getFilterWhenSearchingUsers()

    fun saveFilterWhenSearchingUsers(filter: Filters.ResultSearchUsers) =
        sharedPrefsManager.saveFilterWhenSearchingUsers(filter)

    fun getCurrentQuery() =
        userDataSource.getQuery()

    private fun pagedListConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(5)
        .setEnablePlaceholders(false)
        .setPageSize(5 * 2)
        .build()

}