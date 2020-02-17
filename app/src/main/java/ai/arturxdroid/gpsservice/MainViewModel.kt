package ai.arturxdroid.gpsservice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val isServiceRunning = MutableLiveData<Boolean>(false)

}