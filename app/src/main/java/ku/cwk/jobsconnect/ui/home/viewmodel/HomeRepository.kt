package ku.cwk.jobsconnect.ui.home.viewmodel

import ku.cwk.jobsconnect.network.ApiClient
import ku.cwk.jobsconnect.ui.employer.model.SearchData
import ku.cwk.jobsconnect.ui.employer.model.SearchRequestData
import ku.cwk.jobsconnect.ui.home.model.CVData
import ku.cwk.jobsconnect.ui.home.model.CVRequestData
import ku.cwk.jobsconnect.util.NetworkHandler

class HomeRepository {

    private val apiInstance = ApiClient.instance.serviceApi!!

    suspend fun fetchCVDetails(email: String): Pair<String, CVData?> {
        try {
            val response = apiInstance.fetchCVDetails(CVRequestData(email))

            if (response.isSuccessful) {
                return Pair(NetworkHandler.STATUS_SUCCESS, response.body()!!.cvData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(NetworkHandler.STATUS_ERROR, null)
    }

    suspend fun fetchEmployerDetails(email: String): Pair<String, CVData?> {
        try {
            val response = apiInstance.fetchCVDetails(CVRequestData(email))

            if (response.isSuccessful) {
                return Pair(NetworkHandler.STATUS_SUCCESS, response.body()!!.cvData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(NetworkHandler.STATUS_ERROR, null)
    }

    suspend fun searchSkills(skills: String): Pair<String, ArrayList<SearchData>?> {
        try {
            val response = apiInstance.searchSkills(SearchRequestData(skills))

            if (response.isSuccessful) {
                return Pair(NetworkHandler.STATUS_SUCCESS, response.body())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(NetworkHandler.STATUS_ERROR, null)
    }
}