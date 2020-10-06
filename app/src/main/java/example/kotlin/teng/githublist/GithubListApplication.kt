package example.kotlin.teng.githublist

import android.app.Application
import android.util.Log
import example.kotlin.teng.githublist.resource.network.api_interface.GithubAPI
import example.kotlin.teng.githublist.resource.network.GithubAPIHelper

class GithubListApplication : Application() {
    lateinit var mGithubService: GithubAPI
    override fun onCreate() {
        Log.i(TAG, "onCreate")
        super.onCreate()
        mGithubService = GithubAPIHelper.getGithubService(BuildConfig.GITHUTAPI_URI)
    }

    companion object {
        private const val TAG = "BaseApplication"
    }
}
