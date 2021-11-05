package example.kotlin.teng.githublist.resource.repository

import example.kotlin.teng.githublist.resource.network.*
import example.kotlin.teng.githublist.resource.network.api_interface.GithubService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GithubRepo(private val service: GithubService) : BaseApiResponse() {

    suspend fun getUserList(since: Int, perPage: Int): Flow<NetworkResult<UserListItem>> {
        return flow {
            emit(safeApiCall { service.getPagerUsers(since, perPage) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserDetail(login: String): Flow<NetworkResult<UserDetailItem>> {
        return flow {
            emit(safeApiCall { service.getUser(login) })
        }.flowOn(Dispatchers.IO)
    }

}