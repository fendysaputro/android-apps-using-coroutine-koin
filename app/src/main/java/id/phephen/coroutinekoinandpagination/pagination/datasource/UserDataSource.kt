package id.phephen.coroutinekoinandpagination.pagination.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import id.phephen.coroutinekoinandpagination.api.NetworkState
import id.phephen.coroutinekoinandpagination.model.User
import id.phephen.coroutinekoinandpagination.repository.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Phephen on 13/05/2022.
 */
class UserDataSource(
    private val repository: UserRepository,
    private val query: String,
    private val sort: String,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, User>() {

    private var supervisorJob = Super
    private val networkState = MutableLiveData<NetworkState>()
    private var retryQuery: (() -> Any)? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        retryQuery = { loadInitial(params, callback)}
        executeQuery(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) { }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeQuery(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    private fun executeQuery(page: Int, perPage: Int, callback:(List<User>) -> Unit) {
        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            delay(200) // To handle user typing case
            val users = repository.searchUsersWithPagination(query, page, perPage, sort)
            retryQuery = null
            networkState.postValue(NetworkState.SUCCESS)
            callback(users)
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Log.e(UserDataSource::class.java.simpleName, "An error happened: $e")
        networkState.postValue(NetworkState.FAILED)
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()   // Cancel possible running job to only keep last result searched by user
    }

    fun getNetworkState(): LiveData<NetworkState> =
        networkState

    fun refresh() =
        this.invalidate()

    fun retryFailedQuery() {
        val prevQuery = retryQuery
        retryQuery = null
        prevQuery?.invoke()
    }

}