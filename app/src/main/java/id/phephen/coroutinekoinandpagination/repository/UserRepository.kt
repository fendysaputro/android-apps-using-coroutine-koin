package id.phephen.coroutinekoinandpagination.repository

import id.phephen.coroutinekoinandpagination.api.UserService
import id.phephen.coroutinekoinandpagination.model.User

/**
 * Created by Phephen on 13/05/2022.
 */
class UserRepository(private val service: UserService) {

    private suspend fun search(query: String, page: Int, perPage: Int, sort: String) = service.search(query, page, perPage, sort).await()

    private suspend fun getDetail(login: String) = service.getDetail(login).await()

    private suspend fun getRepos(login: String) = service.getRepos(login).await()

    private suspend fun getFollowers(login: String) = service.getFollowers(login).await()

    suspend fun searchUsersWithPagination(query: String, page: Int, perPage: Int, sort: String): List<User> {
        if (query.isEmpty()) return listOf()

        val users = mutableListOf<User>()
        val request = search(query, page, perPage, sort)
        request.items.forEach {
            val user = getDetail(it.login)
            val repositories = getRepos(user.login)
            val followers = getFollowers(user.login)

//            user.totalStars = repositories.map { it.nu}
            user.followers = listOf(followers)

            users.add(user)
        }
        return users
    }

}