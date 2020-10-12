package example.kotlin.teng.githublist.ui.detail

import android.app.Application
import example.kotlin.teng.githublist.ui.base.BaseViewModel

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val userDetail = appRepo.userDetailLiveData
    fun getUserDetail(login: String) {
        appRepo.getUserDetail(login)
    }
}