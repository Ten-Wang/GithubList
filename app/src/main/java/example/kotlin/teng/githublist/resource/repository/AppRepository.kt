package example.kotlin.teng.githublist.resource.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData

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

    private var carSpeedLiveData = MutableLiveData<Double>()
    private var roadSpeedLimitLiveData = MutableLiveData<Float>()

    fun getCarSpeedLiveData(): MutableLiveData<Double> {
        return carSpeedLiveData
    }

    fun getRoadSpeedLimitLiveData(): MutableLiveData<Float> {
        return roadSpeedLimitLiveData
    }

    fun setCarSpeed(carSpeed: Double) {
        carSpeedLiveData.value = carSpeed
    }

    fun setRoadSpeedLimit(roadSpeedLimit: Float) {
        roadSpeedLimitLiveData.value = roadSpeedLimit
    }
}