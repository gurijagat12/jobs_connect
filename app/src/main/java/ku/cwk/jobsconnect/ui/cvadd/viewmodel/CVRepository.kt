package ku.cwk.jobsconnect.ui.cvadd.viewmodel

import ku.cwk.jobsconnect.network.ApiClient
import ku.cwk.jobsconnect.ui.cvadd.model.CVAddExperienceData
import ku.cwk.jobsconnect.ui.cvadd.model.CVAddQualificationData
import ku.cwk.jobsconnect.ui.cvadd.model.CVAddRequestData
import ku.cwk.jobsconnect.ui.home.model.CVData
import ku.cwk.jobsconnect.ui.home.model.CVRequestData
import ku.cwk.jobsconnect.util.NetworkHandler

class CVRepository {

    private val apiInstance = ApiClient.instance.serviceApi!!

    suspend fun addCVDetails(cvAddRequestData: CVAddRequestData): Pair<String, String?> {
        try {
            val response = apiInstance.addCVDetails(cvAddRequestData)

            if (response.isSuccessful) {
                return Pair(NetworkHandler.STATUS_SUCCESS, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(NetworkHandler.STATUS_ERROR, null)
    }

    suspend fun addCVDetails(cvAddExperienceData : CVAddExperienceData): Pair<String, String?> {
        try {
            val response = apiInstance.addCVDetails(cvAddExperienceData)

            if (response.isSuccessful) {
                return Pair(NetworkHandler.STATUS_SUCCESS, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(NetworkHandler.STATUS_ERROR, null)
    }

    suspend fun addCVDetails(cvAddQualificationData : CVAddQualificationData): Pair<String, String?> {
        try {
            val response = apiInstance.addCVDetails(cvAddQualificationData)

            if (response.isSuccessful) {
                return Pair(NetworkHandler.STATUS_SUCCESS, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(NetworkHandler.STATUS_ERROR, null)
    }

}