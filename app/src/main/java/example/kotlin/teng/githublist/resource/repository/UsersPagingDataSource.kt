package example.kotlin.teng.githublist.resource.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import example.kotlin.teng.githublist.resource.network.response.UserItem
import example.kotlin.teng.githublist.resource.network.mock.MockUsersResponse
import example.kotlin.teng.githublist.resource.network.response.UsersResponse
import example.kotlin.teng.githublist.resource.network.api_interface.GithubService

class UsersPagingDataSource(
    private val service: GithubService
) :
    PagingSource<Int, UserItem>() {

    private val mockConfig = false // mock to test
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserItem> {
        val pageNumber = params.key ?: 0
        if (!mockConfig) {
            return try {
                val response = service.getUsers(pageNumber, 20)
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
        } else {
            return LoadResult.Page(
                data = Gson().fromJson(
                    MockUsersResponse.response, UsersResponse::class.java),
                prevKey = null,
                nextKey = if (pageNumber < 100)
                    pageNumber.plus(20) else null
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserItem>): Int = 1
}