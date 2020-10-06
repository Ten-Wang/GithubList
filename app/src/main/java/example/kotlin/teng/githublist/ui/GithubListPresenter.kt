package example.kotlin.teng.githublist.ui

import android.util.Log
import example.kotlin.teng.githublist.resource.network.api_interface.GithubAPI
import example.kotlin.teng.githublist.resource.network.GithubUserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.ArrayList

class GithubListPresenter internal constructor(private val glView: GLView, private val mGithubService: GithubAPI) {
    private var since = 0
    private val perPage = 20
    private lateinit var userList: ArrayList<GithubUserItem>

    init {
        Log.i(TAG, "GithubListPresenter")
    }

    fun onCreate() {
        Log.i(TAG, "onCreate")

        mGithubService.getPagerUsers(since, perPage).enqueue(object : Callback<List<GithubUserItem>> {
            override fun onResponse(
                call: Call<List<GithubUserItem>>,
                response: Response<List<GithubUserItem>>
            ) {
                Log.i(TAG, "onResponse")
                if (response.body() != null) {
                    since += perPage
                    userList = ArrayList(response.body()!!)
                    glView.setUsersListRecyclerView(userList)
                } else {
                    glView.onGitHubRejectRequest()
                }

            }

            override fun onFailure(
                call: Call<List<GithubUserItem>>,
                t: Throwable
            ) {
                Log.i(TAG, "onFailure")
                glView.onGitHubRejectRequest()
            }
        })
    }

    fun onUserItemClick(position: Int) {
        Log.i(TAG, "onUserItemClick")
        glView.onListItemClick(userList[position])
    }

    fun onLoadMore() {
        mGithubService.getPagerUsers(since, perPage).enqueue(object : Callback<List<GithubUserItem>> {
            override fun onResponse(
                call: Call<List<GithubUserItem>>,
                response: Response<List<GithubUserItem>>
            ) {
                Log.i(TAG, "onResponse")
                if (response.body() != null) {
                    since += perPage
                    val items = ArrayList(response.body()!!)
                    userList.addAll(items)
                    glView.addUsersListRecyclerView(items)
                } else {
                    glView.onGitHubRejectRequest()
                }
            }

            override fun onFailure(
                call: Call<List<GithubUserItem>>,
                t: Throwable
            ) {
                Log.i(TAG, "onFailure")
                glView.onLoadMoreFailed()
            }
        })
    }

    internal interface GLView {
        fun onGitHubRejectRequest()

        fun setUsersListRecyclerView(items: ArrayList<GithubUserItem>)

        fun addUsersListRecyclerView(items: ArrayList<GithubUserItem>)

        fun onLoadMoreFailed()

        fun onListItemClick(githubUserItem: GithubUserItem)

    }

    companion object {
        private const val TAG = "GithubListPresenter"
    }
}
