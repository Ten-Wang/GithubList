package example.kotlin.teng.githublist.network

import retrofit2.Call
import retrofit2.http.GET

interface GithubService {
    @get:GET("/users")
    val allUsers: Call<List<GithubUserItem>>
}
