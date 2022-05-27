package id.phephen.coroutinekoinandpagination.ui.user.search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.phephen.coroutinekoinandpagination.R
import id.phephen.coroutinekoinandpagination.api.NetworkState
import id.phephen.coroutinekoinandpagination.model.User

/**
 * Created by Phephen on 20/05/2022.
 */
class SearchUserAdapter(private val callback: View.OnClickListener): PagedListAdapter<User, RecyclerView.ViewHolder>(
    diffCallback
) {

    private var networkState: NetworkState? = null
    interface OnClickListener {
        fun onClickRetry()
        fun whenListIsUpdated(size: Int, networkState: NetworkState?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_search_user -> SearchUserViewHolder(
                view
            )
            R.layout.item_search_network_state -> SearchUserNetworkStateViewHolder(
                view
            )
            else -> throw IllegalStateException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_search_user -> (holder as SearchUserViewHolder).bindTo(getItem(position))
            R.layout.item_search_network_state -> (holder as SearchUserNetworkStateViewHolder).bindTo(networkState, callback)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_search_network_state
        } else {
            R.layout.item_search_user
        }
    }

    override fun getItemCount(): Int {
        this.callback.whenListIsUpdated(super.getItemCount(), this.networkState)
        return super.getItemCount()
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.SUCCESS

    fun updateNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem

        }
    }
}