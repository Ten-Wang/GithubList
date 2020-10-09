package example.kotlin.teng.githublist.resource.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import example.kotlin.teng.githublist.ThisApplication
import example.kotlin.teng.githublist.custom.ApiResult
import example.kotlin.teng.githublist.custom.livedata.LiveDataDelegate
import example.kotlin.teng.githublist.resource.network.UserDetailItem
import example.kotlin.teng.githublist.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.users_detail_activity.*
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