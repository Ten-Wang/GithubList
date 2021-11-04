package example.kotlin.teng.githublist.ui.detail

import androidx.lifecycle.ViewModel
import example.kotlin.teng.githublist.resource.repository.AppRepository

class DetailViewModel(private val appRepo: AppRepository): ViewModel()  {
    val userDetail = appRepo.userDetailLiveData
    fun getUserDetail(login: String) {
        appRepo.getUserDetail(login)
    }
}