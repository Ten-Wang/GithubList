package example.kotlin.teng.githublist.ui.base


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import example.kotlin.teng.githublist.custom.livedata.LifecycleEvent

abstract class BaseActivity : AppCompatActivity() {

    protected var lifecycleEvent = LifecycleEvent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleEvent.event = Lifecycle.Event.ON_CREATE
    }

    override fun onStart() {
        super.onStart()
        lifecycleEvent.event = Lifecycle.Event.ON_START
    }

    override fun onResume() {
        super.onResume()
        lifecycleEvent.event = Lifecycle.Event.ON_RESUME
    }

    override fun onPause() {
        super.onPause()
        lifecycleEvent.event = Lifecycle.Event.ON_PAUSE
    }

    override fun onStop() {
        super.onStop()
        lifecycleEvent.event = Lifecycle.Event.ON_STOP
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleEvent.event = Lifecycle.Event.ON_DESTROY
    }
}