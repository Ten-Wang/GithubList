package example.kotlin.teng.githublist.di

import example.kotlin.teng.githublist.resource.repository.AppRepository
import example.kotlin.teng.githublist.ui.detail.DetailViewModel
import example.kotlin.teng.githublist.ui.list.UserListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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
            AppRepository(androidContext())
        }
    }
}