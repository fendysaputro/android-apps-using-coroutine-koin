package id.phephen.coroutinekoinandpagination.ui.user.search.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import id.phephen.coroutinekoinandpagination.R
import id.phephen.coroutinekoinandpagination.model.User
import java.lang.Exception
import java.util.*

/**
 * Created by Phephen on 27/05/2022.
 */
class SearchUserViewHolder(parent: View): RecyclerView.ViewHolder(parent) {

    fun bindTo(user: User?) {
        user?.let {
            loadImage(it.avatarUrl, itemView.findViewById(R.id.item_search_user_image_profile))
            val firstFollower: User? = try {
                it.followers[0]
            } catch (e: Exception) {
                null
            }
            val secondFollower: User? = try {
                it.followers[1]
            } catch (e: Exception) {
                null
            }
            firstFollower?.let { loadImage(it.avatarUrl, itemView.findViewById(R.id.item_search_user_follower_image)) }
            secondFollower?.let { loadImage(it.avatarUrl, itemView.findViewById(R.id.item_search_user_follower_image_bis)) }
            val title = itemView.findViewById<TextView>(R.id.item_search_user_title)
            val repositories = itemView.findViewById<TextView>(R.id.item_search_user_repositories)
            val followerName = itemView.findViewById<TextView>(R.id.item_search_user_follower_name)
            val followerCount = itemView.findViewById<TextView>(R.id.item_search_user_follower_count)
            title.text = it.login.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            repositories.text = "${it.totalStars} - ${it.totalRepos} ${itemView.context.getString(R.string.repositories)}"
            followerName.text = firstFollower?.login?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            followerCount.text = "+${it.totalFollowers - 1}"

        }
    }

    private fun loadImage(url: String, imageView: ImageView) {
        Glide.with(itemView.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions().circleCrop())
            .into(imageView)
    }
}