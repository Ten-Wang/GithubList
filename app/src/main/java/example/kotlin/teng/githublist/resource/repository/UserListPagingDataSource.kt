package example.kotlin.teng.githublist.resource.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import example.kotlin.teng.githublist.resource.network.UserItem
import example.kotlin.teng.githublist.resource.network.api_interface.GithubService

class UserListPagingDataSource(
    private val service: GithubService) :
    PagingSource<Int, UserItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserItem> {
        val pageNumber = params.key ?: 0
        return try {
            val response = service.getPagerUsers(pageNumber, 20)
            val pagedResponse = response.body()

            LoadResult.Page(
                data = pagedResponse.orEmpty(),
                prevKey = null,
                nextKey = if (pageNumber < 100)
                    pageNumber.plus(20) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserItem>): Int = 1
}