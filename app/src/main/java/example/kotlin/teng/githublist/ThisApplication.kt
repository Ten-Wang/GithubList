package example.kotlin.teng.githublist

import android.app.Application
import example.kotlin.teng.githublist.di.repositoryModule
import example.kotlin.teng.githublist.di.serviceModule
import example.kotlin.teng.githublist.di.viewModelModule
import example.kotlin.teng.githublist.resource.network.api_interface.GithubService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ThisApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // inject Android context
            androidContext(this@ThisApplication)
            // use modules
            modules(
                serviceModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}
