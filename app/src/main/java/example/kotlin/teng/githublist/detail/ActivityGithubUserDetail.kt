package example.kotlin.teng.githublist.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.util.Linkify
import android.util.Log
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import example.kotlin.teng.githublist.BaseApplication
import example.kotlin.teng.githublist.network.GithubService
import example.kotlin.teng.githublist.network.GithubUserDetailItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import example.kotlin.teng.githublist.R

class ActivityGithubUserDetail : AppCompatActivity() {
    private lateinit var mGithubService: GithubService
    private lateinit var textView_name: TextView
    private lateinit var textView_Bio: TextView
    private lateinit var textView_login: TextView
    private lateinit var textView_location: TextView
    private lateinit var textView_blog: TextView
    private lateinit var progress: ProgressBar
    private lateinit var img_avatar_url: ImageView
    private lateinit var btn_close: Button
    private lateinit var relative_badge: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_users_detail)
        mGithubService = (application as BaseApplication).githubService

        initViews()
        setData()
    }

    private fun initViews() {
        Log.i(TAG, "initViews")
        textView_name = findViewById(R.id.textView_name)
        textView_login = findViewById(R.id.textView_login)
        textView_Bio = findViewById(R.id.textView_Bio)
        textView_location = findViewById(R.id.textView_location)
        textView_blog = findViewById(R.id.textView_blog)
        textView_blog!!.autoLinkMask = Linkify.ALL
        progress = findViewById(R.id.progressBar)
        img_avatar_url = findViewById(R.id.img_avatar_url)
        btn_close = findViewById(R.id.btn_close)
        relative_badge = findViewById(R.id.relative_badge)
    }

    private fun setData() {
        Log.i(TAG, "setData")
        val login = intent.getStringExtra("login")
        mGithubService!!.getUser(login).enqueue(object : Callback<GithubUserDetailItem> {
            override fun onResponse(
                call: Call<GithubUserDetailItem>,
                response: Response<GithubUserDetailItem>
            ) {
                val item = response.body()
                if (item != null) {
                    textView_name!!.text = item.name
                    if (item.bio != null) {
                        textView_Bio!!.text = item.bio!!.toString()
                    }
                    textView_login!!.text = item.login
                    textView_location!!.text = item.location
                    textView_blog!!.text = item.blog
                    relative_badge!!.visibility = if (item.siteAdmin != null) View.VISIBLE else View.GONE

                    Glide.with(applicationContext)
                        .load(item.avatarUrl)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?, model: Any,
                                target: Target<Drawable>, isFirstResource: Boolean
                            ): Boolean {
                                progress!!.visibility = View.GONE
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable, model: Any,
                                target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean
                            ): Boolean {
                                progress!!.visibility = View.GONE
                                return false
                            }
                        })
                        .into(img_avatar_url!!)
                } else {
                    Toast.makeText(application, "GitHub reject request!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GithubUserDetailItem>, t: Throwable) {
                Toast.makeText(application, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        btn_close!!.setOnClickListener { v -> finish() }
    }

    companion object {

        private val TAG = "ActivityGUsersDetail"
    }
}
