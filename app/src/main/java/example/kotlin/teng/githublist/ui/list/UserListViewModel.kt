package example.kotlin.teng.githublist.ui.list

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import example.kotlin.teng.githublist.resource.network.response.UserItem
import example.kotlin.teng.githublist.resource.network.api_interface.GithubService
import example.kotlin.teng.githublist.resource.repository.UsersPagingDataSource
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserListViewModel : ViewModel(), KoinComponent {
    private val service: GithubService by inject()

    val userListPaging: Flow<PagingData<UserItem>> = getUserListStream()

    private fun getUserListStream(): Flow<PagingData<UserItem>> {
        return Pager(PagingConfig(20)) {
            UsersPagingDataSource(service)
        }.flow
    }
}
