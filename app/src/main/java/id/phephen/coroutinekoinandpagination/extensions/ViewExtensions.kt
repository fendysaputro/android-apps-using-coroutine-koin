package id.phephen.coroutinekoinandpagination.extensions

import androidx.appcompat.widget.SearchView

/**
 * Created by Phephen on 13/05/2022.
 */

fun SearchView.onQueryTextChange(action: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            action.invoke(newText)
            return true
        }

    })
}