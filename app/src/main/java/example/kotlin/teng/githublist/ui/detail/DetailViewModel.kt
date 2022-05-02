package example.kotlin.teng.githublist.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import example.kotlin.teng.githublist.resource.network.response.UserDetailResponse
import example.kotlin.teng.githublist.resource.repository.GithubRepo
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailViewModel : ViewModel(), KoinComponent {

    private val appRepo: GithubRepo by inject()
    val userDetail = MutableLiveData<UserDetailResponse>()

    fun getUserDetail(login: String) = viewModelScope.launch {
        appRepo.getUserDetail(login).collect { it ->
            it.data.let {
                userDetail.value = it
            }
        }
    }
}