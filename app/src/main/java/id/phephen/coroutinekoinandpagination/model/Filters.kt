package id.phephen.coroutinekoinandpagination.model

/**
 * Created by Phephen on 10/05/2022.
 */
object Filters {

    enum class ResultSearchUsers(val value: String) {
        BY_REPOS("repositories"),
        BY_FOLLOWERS("followers"),
        BY_SCORE("score")
    }

}