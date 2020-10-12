package example.kotlin.teng.githublist.resource.repository

import android.app.Application
import example.kotlin.teng.githublist.ThisApplication
import example.kotlin.teng.githublist.custom.livedata.LiveDataDelegate
import example.kotlin.teng.githublist.resource.network.UserDetailItem
import example.kotlin.teng.githublist.resource.network.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository private constructor(private val _application: Application) {

    companion object {
        private var INSTANCE: AppRepository? = null

        @Synchronized
        fun getInstance(application: Application): AppRepository {
            if (INSTANCE == null) {
                INSTANCE = AppRepository(application)
            }
            return INSTANCE!!
        }
    }

    val userListLiveData = LiveDataDelegate<ArrayList<UserItem>>()

    fun getUserList(since: Int, perPage: Int) {
        (_application as ThisApplication).mGithubService.getPagerUsers(since, perPage)
            .enqueue(object : Callback<List<UserItem>> {
                override fun onResponse(
                    call: Call<List<UserItem>>,
                    response: Response<List<UserItem>>
                ) {
                    val item = response.body()
                    if (item != null) {
                        if (since == 0) {
                            userListLiveData.value = ArrayList(item)
                        } else {
                            val list = userListLiveData.value
                            for (userItem: UserItem in item) {
                                list.add(userItem)
                            }
                            userListLiveData.postValue(list)
                        }
                    } else {
                        TODO("Not yet implemented")
                    }
                }

                override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    val userDetailLiveData = LiveDataDelegate<UserDetailItem>()

    fun getUserDetail(login: String) {
        (_application as ThisApplication).mGithubService.getUser(login)
            .enqueue(object : Callback<UserDetailItem> {
                override fun onResponse(
                    call: Call<UserDetailItem>,
                    response: Response<UserDetailItem>
                ) {
                    val item = response.body()
                    if (item != null) {
                        userDetailLiveData.value = item
                    } else {
                        TODO("Not yet implemented")
                    }
                }

                override fun onFailure(call: Call<UserDetailItem>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }


}