package example.kotlin.teng.githublist.resource.network.api_interface

import example.kotlin.teng.githublist.resource.network.UserDetailItem
import example.kotlin.teng.githublist.resource.network.UserListItem
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    suspend fun getPagerUsers(since: Int,perPage: Int): Response<UserListItem>
    suspend fun getUser(userId: String): Response<UserDetailItem>

    class Network(
        private val retrofit: Retrofit,
    ) : GithubService {

        override suspend fun getPagerUsers(since: Int,perPage: Int): Response<UserListItem> {
            return retrofit.create(NetworkCalls::class.java).getPagerUsers(since,perPage)
        }

        override suspend fun getUser(userId: String):Response<UserDetailItem> {
            return retrofit.create(NetworkCalls::class.java).getUser(userId)
        }

        interface NetworkCalls {
            @GET("/users")
            suspend fun getPagerUsers(
                @Query("since") since: Int,
                @Query("per_page") perPage: Int
            ): Response<UserListItem>

            @GET("/users/{user_id}")
            suspend fun getUser(@Path("user_id") userId: String): Response<UserDetailItem>
        }
    }
}
