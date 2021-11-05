package example.kotlin.teng.githublist.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import example.kotlin.teng.githublist.resource.network.UserListItem
import example.kotlin.teng.githublist.resource.repository.GithubRepo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserListViewModel(private val appRepo: GithubRepo) : ViewModel() {

    val userList = MutableLiveData<UserListItem>()
    fun getUserList(since: Int, perPage: Int) = viewModelScope.launch {
        appRepo.getUserList(since, perPage).collect { it ->
            it.data.let {
                userList.value = it
            }
        }
    }
}
