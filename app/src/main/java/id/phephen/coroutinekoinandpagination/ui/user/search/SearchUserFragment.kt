package id.phephen.coroutinekoinandpagination.ui.user.search

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.phephen.coroutinekoinandpagination.R
import id.phephen.coroutinekoinandpagination.api.NetworkState
import id.phephen.coroutinekoinandpagination.base.BaseFragment
import id.phephen.coroutinekoinandpagination.extensions.onQueryTextChange
import id.phephen.coroutinekoinandpagination.ui.user.search.view.SearchUserAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice


class SearchUserFragment : BaseFragment(), SearchUserAdapter.OnClickListener {

    private val viewModel: SearchUserViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchUserAdapter
    private lateinit var emptyListImage: ImageView
    private lateinit var emptyListText: ImageView
    private lateinit var emptyListButton: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var fab: FloatingActionButton

    override fun getLayoutId(): Int = R.layout.fragment_search_user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.fragment_search_user_rv)
        emptyListImage = view.findViewById(R.id.fragment_search_user_empty_list_image)
        emptyListText = view.findViewById(R.id.fragment_search_user_empty_list_title)
        emptyListButton = view.findViewById(R.id.fragment_search_user_empty_list_button)
        progressBar = view.findViewById(R.id.fragment_search_user_progress)
        fab = view.findViewById(R.id.fragment_search_user_fab)
        configureRecyclerView()
        configureObservable()
        configureOnClick()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        configureMenu(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onClickRetry() {
        viewModel.refreshFailedRequest()
    }

    override fun whenListIsUpdated(size: Int, networkState: NetworkState?) {
        updateUIWhenLoading(size, networkState)
        updateUIWhenEmptyList(size, networkState)
    }

    private fun configureOnClick() {
        emptyListButton.setOnClickListener { viewModel.refreshAllList() }
        fab.setOnClickListener { showDialogWithFilterItem { viewModel.refreshAllList() } }
    }

    private fun configureMenu(menu: Menu) {
        val searchMenuItem = menu.findItem(R.id.action_search)
        val possibleExistingQuery = viewModel.getCurrentQuery()
        val searchView = searchMenuItem.actionView as SearchView
        if (possibleExistingQuery != null && !possibleExistingQuery.isEmpty()) {
            searchMenuItem.expandActionView()
            searchView.setQuery(possibleExistingQuery, false)
            searchView.clearFocus()
        }
        searchView.onQueryTextChange {
            viewModel.fetchUsersByName(it)
        }
    }

    private fun configureRecyclerView() {
        adapter = id.phephen.coroutinekoinandpagination.ui.user.search.view.SearchUserAdapter(this)
        recyclerView.adapter = adapter
    }

    private fun configureObservable() {
        viewModel.networkState?.observe(viewLifecycleOwner, Observer { adapter.updateNetworkState(it) })
        viewModel.users.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
    }

    private fun updateUIWhenEmptyList(size: Int, networkState: NetworkState?) {
        emptyListImage.visibility = View.GONE
        emptyListButton.visibility = View.GONE
        emptyListText.visibility = View.GONE
        if (size == 0) {
            when (networkState) {
                NetworkState.SUCCESS -> {
                    Glide.with(requireContext())
                        .load(R.drawable.ic_directions_run_black_24dp)
                        .into(emptyListImage)
                    emptyListImage.visibility = View.VISIBLE
                    emptyListText.visibility = View.VISIBLE
                    emptyListButton.visibility = View.GONE
                }
                NetworkState.FAILED -> {
                    Glide.with(requireContext())
                        .load(R.drawable.ic_healing_black_24dp)
                        .into(emptyListImage)
                    emptyListImage.visibility = View.VISIBLE
                    emptyListButton.visibility = View.VISIBLE
                    emptyListText.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun updateUIWhenLoading(size: Int, networkState: NetworkState?) {
        progressBar.visibility = if (size == 0 && networkState == NetworkState.RUNNING) View.VISIBLE else View.GONE
    }

    private fun showDialogWithFilterItem(callback: () -> Unit) {
        Mater
    }

}