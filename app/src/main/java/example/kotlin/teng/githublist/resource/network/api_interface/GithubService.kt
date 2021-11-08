package example.kotlin.teng.githublist.resource.network.api_interface

import example.kotlin.teng.githublist.resource.network.response.UserDetailResponse
import example.kotlin.teng.githublist.resource.network.response.UsersResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    suspend fun getUsers(since: Int, perPage: Int): Response<UsersResponse>
    suspend fun getUser(userId: String): Response<UserDetailResponse>

    class Network(
        private val retrofit: Retrofit,
    ) : GithubService {

        override suspend fun getUsers(since: Int, perPage: Int): Response<UsersResponse> {
            return retrofit.create(NetworkCalls::class.java).getUsers(since,perPage)
        }

        override suspend fun getUser(userId: String):Response<UserDetailResponse> {
            return retrofit.create(NetworkCalls::class.java).getUser(userId)
        }

        interface NetworkCalls {
            @GET("/users")
            suspend fun getUsers(
                @Query("since") since: Int,
                @Query("per_page") perPage: Int
            ): Response<UsersResponse>

            @GET("/users/{user_id}")
            suspend fun getUser(@Path("user_id") userId: String): Response<UserDetailResponse>
        }
    }
}
