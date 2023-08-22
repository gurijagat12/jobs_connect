package ku.cwk.jobsconnect.ui.onboard.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ku.cwk.jobsconnect.ui.onboard.model.RegisterRequestData
import ku.cwk.jobsconnect.util.Constants
import ku.cwk.jobsconnect.util.NetworkHandler

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val repository =
        LoginRepository(app.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE))

    var loginStatus = MutableLiveData(NetworkHandler.STATUS_NONE)
    var userType = 0
    var registerData = RegisterRequestData()

    fun login(userName: String, password: String) {
        viewModelScope.apply {
            viewModelScope.launch {
                loginStatus.value = NetworkHandler.STATUS_LOADING
                val result = repository.login(userName, password)
                if (result.first == NetworkHandler.STATUS_SUCCESS)
                    userType = result.second ?: 0
                loginStatus.value = result.first
            }
        }
    }

    fun register() {
        viewModelScope.apply {
            viewModelScope.launch {
                loginStatus.value = NetworkHandler.STATUS_LOADING
                val result = repository.register(registerData)
                if (result.first == NetworkHandler.STATUS_SUCCESS)
                    userType = result.second ?: 0
                loginStatus.value = result.first
            }
        }
    }
}