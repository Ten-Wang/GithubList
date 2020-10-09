package example.kotlin.teng.githublist.resource.network.api_interface

import example.kotlin.teng.githublist.resource.network.UserDetailItem
import example.kotlin.teng.githublist.resource.network.UserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {

    @get:GET("/users")
    val allUsers: Call<List<UserItem>>

    @GET("/users")
    fun getPagerUsers(@Query("since") since: Int, @Query("per_page") perPage: Int): Call<List<UserItem>>

    @GET("/users/{user_id}")
    fun getUser(@Path("user_id") userId: String): Call<UserDetailItem>
}
