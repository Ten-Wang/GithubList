package example.kotlin.teng.githublist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import example.kotlin.teng.githublist.R.layout.activity_user_list
import example.kotlin.teng.githublist.network.GithubUserItem
import kotlinx.android.synthetic.main.activity_user_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListActivity : AppCompatActivity() {

    val TAG = "ActivityGithubUserList"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_user_list)
        recyclerview_user_list.layoutManager = LinearLayoutManager(this)

        (application as BaseApplication).githubService.allUsers.enqueue(
            object : Callback<List<GithubUserItem>> {
            override fun onResponse(call: Call<List<GithubUserItem>>,
                                    response: Response<List<GithubUserItem>>) {
                Log.i(TAG, "onResponse")
                val items = response.body()
                recyclerview_user_list.adapter = TestAdapter(items!!)
                (recyclerview_user_list.adapter as TestAdapter).notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<GithubUserItem>>, t: Throwable) {
                Log.i(TAG, "onFailure")
            }
        })
    }
}


