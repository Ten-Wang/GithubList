package example.kotlin.teng.githublist.ui.list

import android.app.Application
import example.kotlin.teng.githublist.ui.base.BaseViewModel

class UserListViewModel (application: Application) : BaseViewModel(application) {

    val userList = appRepo.userListLiveData
    fun getUserList(since: Int, perPage: Int) {
        appRepo.getUserList(since,perPage)
    }
}
