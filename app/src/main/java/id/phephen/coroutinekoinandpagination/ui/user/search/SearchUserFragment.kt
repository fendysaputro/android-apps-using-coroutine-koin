package id.phephen.coroutinekoinandpagination.ui.user.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.phephen.coroutinekoinandpagination.R
import id.phephen.coroutinekoinandpagination.base.BaseFragment


class SearchUserFragment : BaseFragment() {


    override fun getLayoutId(): Int = R.layout.fragment_search_user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

}