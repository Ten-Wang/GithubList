package example.kotlin.teng.githublist

import android.app.Application
import android.util.Log
import example.kotlin.teng.githublist.network.GithubService
import example.kotlin.teng.githublist.network.GithubServiceHelper

class BaseApplication : Application() {
    lateinit var mGithubService: GithubService
    override fun onCreate() {
        Log.i(TAG, "onCreate")
        super.onCreate()
        mGithubService = GithubServiceHelper.getGithubService(BuildConfig.GITHUTAPI_URI)
    }

    companion object {
        private const val TAG = "BaseApplication"
    }
}
