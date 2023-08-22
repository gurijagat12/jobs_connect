package ku.cwk.jobsconnect.ui.home.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ku.cwk.jobsconnect.ui.employer.model.SearchData
import ku.cwk.jobsconnect.ui.home.model.CVData
import ku.cwk.jobsconnect.util.Constants
import ku.cwk.jobsconnect.util.NetworkHandler

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = HomeRepository()
    var cvStatus = MutableLiveData(NetworkHandler.STATUS_NONE)
    var searchStatus = MutableLiveData(NetworkHandler.STATUS_NONE)
    lateinit var cvData: CVData
    lateinit var searchList: ArrayList<SearchData>
    private val pref = app.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE)


    fun fetchCVDetails(email: String?) {
        viewModelScope.launch {
            cvStatus.value = NetworkHandler.STATUS_LOADING
            val result =
                repository.fetchCVDetails(email ?: pref.getString(Constants.USER_EMAIL, "") ?: "")
            if (result.first == NetworkHandler.STATUS_SUCCESS) {
                cvData = result.second!!
            }
            cvStatus.value = result.first
        }
    }

    fun fetchEmployerDetails() {
        viewModelScope.launch {
            cvStatus.value = NetworkHandler.STATUS_LOADING
            val result =
                repository.fetchEmployerDetails(pref.getString(Constants.USER_EMAIL, "") ?: "")
            if (result.first == NetworkHandler.STATUS_SUCCESS) {
                cvData = result.second!!
            }
            cvStatus.value = result.first
        }
    }

    fun searchSkills(skills: String) {
        viewModelScope.launch {
            searchStatus.value = NetworkHandler.STATUS_LOADING
            val result = repository.searchSkills(skills)
            if (result.first == NetworkHandler.STATUS_SUCCESS) {
                searchList = result.second!!
            }
            searchStatus.value = result.first
        }
    }

    fun logout() = pref.edit().clear().apply()
}