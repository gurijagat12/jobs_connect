package ku.cwk.jobsconnect.ui.cvadd.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ku.cwk.jobsconnect.ui.cvadd.model.CVAddExperienceData
import ku.cwk.jobsconnect.ui.cvadd.model.CVAddQualificationData
import ku.cwk.jobsconnect.ui.cvadd.model.CVAddRequestData
import ku.cwk.jobsconnect.util.NetworkHandler

class CVViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = CVRepository()
    var cvStatus = MutableLiveData(NetworkHandler.STATUS_NONE)
    var userId = 0
    var cvAddRequestData = CVAddRequestData()
    var cvAddExperienceData = CVAddExperienceData()
    var cvAddQualificationData = CVAddQualificationData()

    fun addCVDetails() {
        viewModelScope.launch {
            cvStatus.value = NetworkHandler.STATUS_LOADING
            val result = repository.addCVDetails(cvAddRequestData)
            /*if (result.first == NetworkHandler.STATUS_SUCCESS) {
            }*/
            cvStatus.value = result.first
        }
    }

    fun addCVDetails(cvAddExperienceData: CVAddExperienceData) {
        viewModelScope.launch {
            cvStatus.value = NetworkHandler.STATUS_LOADING
            val result = repository.addCVDetails(cvAddExperienceData)
            /*if (result.first == NetworkHandler.STATUS_SUCCESS) {
            }*/
            cvStatus.value = result.first
        }
    }

    fun addCVDetails(cvAddQualificationData: CVAddQualificationData) {
        viewModelScope.launch {
            cvStatus.value = NetworkHandler.STATUS_LOADING
            val result = repository.addCVDetails(cvAddQualificationData)
            /*if (result.first == NetworkHandler.STATUS_SUCCESS) {
            }*/
            cvStatus.value = result.first
        }
    }
}