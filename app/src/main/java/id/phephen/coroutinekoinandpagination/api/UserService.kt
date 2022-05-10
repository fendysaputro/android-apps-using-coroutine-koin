package id.phephen.coroutinekoinandpagination.api

import id.phephen.coroutinekoinandpagination.model.Repository
import id.phephen.coroutinekoinandpagination.model.Result
import id.phephen.coroutinekoinandpagination.model.User
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Phephen on 10/05/2022.
 */
interface UserService {

    @GET("search/users")
    fun search(@Query("q") query: String,
               @Query("page") page: Int,
               @Query("per_page") perPage: Int,
               @Query("sort") sort: String): Deferred<Result>

    @GET("users/{username}")
    fun getDetail(@Path("username") username: String): Deferred<User>

    @GET("users/{username}/repos")
    fun getRepos(@Path("username") username: String): Deferred<Repository>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String,
                     @Query("per_page") perPage: Int = 2): Deferred<User>

}