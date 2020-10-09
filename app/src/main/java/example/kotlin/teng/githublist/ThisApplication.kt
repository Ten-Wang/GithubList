package example.kotlin.teng.githublist

import android.app.Application
import android.util.Log
import example.kotlin.teng.githublist.resource.network.api_interface.GithubAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ThisApplication : Application() {
    
    lateinit var mGithubService: GithubAPI

    override fun onCreate() {
        Log.i(TAG, "onCreate")
        super.onCreate()
        mGithubService = getGithubService(BuildConfig.GITHUTAPI_URI)
    }

    companion object {
        private const val TAG = "BaseApplication"
    }

    private fun getGithubService(url: String): GithubAPI {
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
