package example.kotlin.teng.githublist.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.util.Linkify
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import example.kotlin.teng.githublist.ThisApplication
import example.kotlin.teng.githublist.resource.network.UserDetailItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import example.kotlin.teng.githublist.R
import kotlinx.android.synthetic.main.activity_github_users_detail.*

class UserDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_users_detail)

        initViews()

        val login = intent.getStringExtra("login")
        if(login != null){
            setData(login)
        }
    }

    private fun initViews() {
        Log.i(TAG, "initViews")

        textView_blog.autoLinkMask = Linkify.ALL
    }

    private fun setData(login: String) {
        Log.i(TAG, "setData")

        (applicationContext as ThisApplication).mGithubService.
            getUser(login).enqueue(object : Callback<UserDetailItem> {
            override fun onResponse(
                    call: Call<UserDetailItem>,
                    response: Response<UserDetailItem>
            ) {
                val item = response.body()
                if (item != null) {
                    textView_name.text = item.name
                    if (item.bio != null) {
                        textView_Bio.text = item.bio.toString()
                    }
                    textView_login.text = item.login
                    textView_location.text = item.location
                    textView_blog.text = item.blog
                    relative_badge.visibility = if (item.siteAdmin != null) View.VISIBLE else View.GONE

                    Glide.with(applicationContext)
                        .load(item.avatarUrl)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?, model: Any,
                                target: Target<Drawable>, isFirstResource: Boolean
                            ): Boolean {
                                progressBar.visibility = View.GONE
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable, model: Any,
                                target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean
                            ): Boolean {
                                progressBar.visibility = View.GONE
                                return false
                            }
                        })
                        .into(img_avatar_url)
                } else {
                    Toast.makeText(application, "GitHub reject request!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserDetailItem>, t: Throwable) {
                Toast.makeText(application, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        btn_close.setOnClickListener { finish() }
    }

    companion object {
        private const val TAG = "ActivityGUsersDetail"
    }
}
