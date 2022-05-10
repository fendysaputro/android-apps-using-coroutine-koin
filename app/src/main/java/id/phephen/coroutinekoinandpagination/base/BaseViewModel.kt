package id.phephen.coroutinekoinandpagination.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel

/**
 * Created by Phephen on 10/05/2022.
 */
abstract class BaseViewModel: ViewModel() {

    protected val uiScope = CoroutineScope(Dispatchers.IO)

    protected val ioScope = CoroutineScope(Dispatchers.Default)

    override fun onCleared() {
        super.onCleared()
        uiScope.coroutineContext.cancel()
        ioScope.coroutineContext.cancel()
    }

}