package id.phephen.coroutinekoinandpagination.ui.user.search.view

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.phephen.coroutinekoinandpagination.R
import id.phephen.coroutinekoinandpagination.api.NetworkState

/**
 * Created by Phephen on 27/05/2022.
 */
class SearchUserNetworkStateViewHolder(parent: View): RecyclerView.ViewHolder(parent) {

    private lateinit var retryButton: Button
    private lateinit var retryTitle: TextView
    private lateinit var progressBar: ProgressBar

    fun bindTo(networkState: NetworkState?, callback: SearchUserAdapter.OnClickListener) {
        hideViews()
        setVisibleRightViews(networkState)
        retryButton = itemView.findViewById<Button>(R.id.item_search_user_network_state_button)
        retryButton.setOnClickListener { callback.onClickRetry() }
    }

    private fun hideViews() {
        retryButton.visibility = View.GONE
        retryTitle.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    private fun setVisibleRightViews(networkState: NetworkState?) {
        when (networkState) {
            NetworkState.FAILED -> {
                retryButton.visibility = View.VISIBLE
                retryTitle.visibility = View.VISIBLE
            }
            NetworkState.RUNNING -> {
                progressBar.visibility = View.VISIBLE
            }
        }
    }

}