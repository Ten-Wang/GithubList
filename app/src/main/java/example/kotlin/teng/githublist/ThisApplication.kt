package example.kotlin.teng.githublist

import android.app.Application
import android.util.Log
import example.kotlin.teng.githublist.di.viewModelModule
import example.kotlin.teng.githublist.resource.network.api_interface.GithubAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ThisApplication : Application() {

    lateinit var mGithubService: GithubAPI

    override fun onCreate() {
        Log.i(TAG, "onCreate")
        super.onCreate()
        startKoin {
            // Koin Android logger
            androidLogger()
            // inject Android context
            androidContext(this@ThisApplication)
            // use modules
            modules(
                viewModelModule
            )
        }
        mGithubService = getGithubService()
    }

    companion object {
        private const val TAG = "BaseApplication"
    }

    private fun getGithubService(): GithubAPI {
        val baseUrl = BuildConfig.GITHUTAPI_URI
        Log.i(TAG, "getGithubService")
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(GithubAPI::class.java)
    }
}
