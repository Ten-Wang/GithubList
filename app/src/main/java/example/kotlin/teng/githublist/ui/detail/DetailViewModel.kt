package example.kotlin.teng.githublist.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import example.kotlin.teng.githublist.resource.network.response.UserDetailResponse
import example.kotlin.teng.githublist.resource.repository.GithubRepo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(private val appRepo: GithubRepo) : ViewModel() {

    val userDetail = MutableLiveData<UserDetailResponse>()

    fun getUserDetail(login: String) = viewModelScope.launch {
        appRepo.getUserDetail(login).collect { it ->
            it.data.let {
                userDetail.value = it
            }
        }
    }
}