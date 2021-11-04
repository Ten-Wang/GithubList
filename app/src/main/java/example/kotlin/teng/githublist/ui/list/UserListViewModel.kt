package example.kotlin.teng.githublist.ui.list

import androidx.lifecycle.ViewModel
import example.kotlin.teng.githublist.resource.repository.AppRepository

class UserListViewModel(private val appRepo:AppRepository): ViewModel() {

    val userList = appRepo.userListLiveData
    fun getUserList(since: Int, perPage: Int) {
        appRepo.getUserList(since, perPage)
    }
}
