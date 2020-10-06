package example.kotlin.teng.githublist.resource.network

import android.util.Log
import example.kotlin.teng.githublist.resource.network.api_interface.GithubAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubAPIHelper {
    private const val TAG = "GithubServiceHelper"

    fun getGithubService(url: String): GithubAPI {
        Log.i(TAG, "getGithubService")
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
            .create(GithubAPI::class.java)
    }
}
