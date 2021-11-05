package example.kotlin.teng.githublist.di

import example.kotlin.teng.githublist.BuildConfig
import example.kotlin.teng.githublist.resource.network.api_interface.GithubService
import example.kotlin.teng.githublist.resource.repository.GithubRepo
import example.kotlin.teng.githublist.ui.detail.DetailViewModel
import example.kotlin.teng.githublist.ui.list.UserListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule by lazy {
    module {
        viewModel {
            UserListViewModel(get())
        }
        viewModel {
            DetailViewModel(get())
        }
    }
}

val repositoryModule by lazy {
    module {
        single {
            GithubRepo(get())
        }
    }
}

val serviceModule by lazy {
    module {
        single<GithubService> {
            GithubService.Network(get(named("GithubBase")))
        }
        single<Retrofit>(named("GithubBase")){
            val baseUrl = BuildConfig.GITHUTAPI_URI
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }
}