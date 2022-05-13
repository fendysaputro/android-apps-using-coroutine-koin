package id.phephen.coroutinekoinandpagination.pagination.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import id.phephen.coroutinekoinandpagination.model.User
import id.phephen.coroutinekoinandpagination.repository.UserRepository
import kotlinx.coroutines.CoroutineScope

/**
 * Created by Phephen on 13/05/2022.
 */
class UserDataSourceFactory(
    private val repository: UserRepository,
    private var query: String = "",
    private var sort: String = "",
    private val scope: CoroutineScope
) : DataSource.Factory<Int, User>() {

    val source = MutableLiveData<UserDataSource>()

    override fun create(): DataSource<Int, User> {
        val source = UserDataSource(repository, query, sort, scope)
        this.source.postValue(source)
        return source
    }

    fun getQuery() = query

    fun getSource() = source.value

    fun updateQuery(query: String, sort: String){
        this.query = query
        this.sort = sort
        getSource()?.refresh()
    }

}