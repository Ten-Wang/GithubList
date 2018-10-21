package example.kotlin.teng.githublist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import example.kotlin.teng.githublist.R.id.recyclerview_user_list
import example.kotlin.teng.githublist.R.layout.activity_user_list
import java.util.*

class UserListActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_user_list)

        val userItems = ArrayList<TestItem>()
        for (i in 0..4) {
            userItems.add(TestItem("測試$i"))
        }

        val tAdapter = TestAdapter(userItems)
        val recyclerView = findViewById<RecyclerView>(recyclerview_user_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = tAdapter
        tAdapter.notifyDataSetChanged()
    }
}
