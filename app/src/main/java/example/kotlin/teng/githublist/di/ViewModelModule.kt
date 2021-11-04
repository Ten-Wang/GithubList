package example.kotlin.teng.githublist.di

import example.kotlin.teng.githublist.ui.detail.DetailViewModel
import example.kotlin.teng.githublist.ui.list.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule by lazy {
    module {
      viewModel {
            UserListViewModel(get())
            DetailViewModel(get())
        }
    }
}