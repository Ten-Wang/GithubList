package example.kotlin.teng.githublist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import example.kotlin.teng.githublist.resource.repository.AppRepository

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    protected var appRepo: AppRepository = AppRepository.getInstance(application)
}