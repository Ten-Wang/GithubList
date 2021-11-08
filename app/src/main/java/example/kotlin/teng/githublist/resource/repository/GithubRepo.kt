package example.kotlin.teng.githublist.resource.repository

import example.kotlin.teng.githublist.resource.network.*
import example.kotlin.teng.githublist.resource.network.response.BaseApiResponse
import example.kotlin.teng.githublist.resource.network.api_interface.GithubService
import example.kotlin.teng.githublist.resource.network.response.UserDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GithubRepo(private val service: GithubService) : BaseApiResponse() {
    suspend fun getUserDetail(login: String): Flow<NetworkResult<UserDetailResponse>> {
        return flow {
            emit(safeApiCall { service.getUser(login) })
        }.flowOn(Dispatchers.IO)
    }
}