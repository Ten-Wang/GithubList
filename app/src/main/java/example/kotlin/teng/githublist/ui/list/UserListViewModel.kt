package example.kotlin.teng.githublist.ui.list

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import example.kotlin.teng.githublist.resource.network.UserItem
import example.kotlin.teng.githublist.resource.network.api_interface.GithubService
import example.kotlin.teng.githublist.resource.repository.UserListPagingDataSource
import kotlinx.coroutines.flow.Flow

class UserListViewModel(private val service: GithubService) :
    ViewModel() {

    val userListPaging: Flow<PagingData<UserItem>> = getUserListStream()

    private fun getUserListStream(): Flow<PagingData<UserItem>> {
        return Pager(PagingConfig(20)) {
            UserListPagingDataSource(service)
        }.flow
    }
}
