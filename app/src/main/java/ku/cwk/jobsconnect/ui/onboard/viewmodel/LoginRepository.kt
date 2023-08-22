package ku.cwk.jobsconnect.ui.onboard.viewmodel

import android.content.SharedPreferences
import ku.cwk.jobsconnect.network.ApiClient
import ku.cwk.jobsconnect.ui.onboard.model.LoginRequestData
import ku.cwk.jobsconnect.ui.onboard.model.RegisterRequestData
import ku.cwk.jobsconnect.util.Constants
import ku.cwk.jobsconnect.util.NetworkHandler

class LoginRepository(private val prefs: SharedPreferences) {
    private val apiInstance = ApiClient.instance.serviceApi!!

    suspend fun login(userName: String, password: String): Pair<String, Int?> {
        try {
            val response = apiInstance.login(LoginRequestData(userName, password))

            if (response.isSuccessful) {
                val result = response.body()!!
                if (result.message == "Login Successful") {

                    val editor = prefs.edit()
                    editor.putString(Constants.USER_EMAIL, result.personal.email)
                    editor.putInt(Constants.USER_TYPE, result.personal.userType ?: 0)
                    editor.apply()

                    return Pair(NetworkHandler.STATUS_SUCCESS, result.personal.userType ?: 0)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(NetworkHandler.STATUS_ERROR, null)
    }

    suspend fun register(registerRequestData: RegisterRequestData): Pair<String, Int?> {
        try {
            val response = apiInstance.register(registerRequestData)

            if (response.isSuccessful) {
                val result = response.body()!!
                if (result.message == "Record Inserted Successfully") {
                    val editor = prefs.edit()
                    editor.putString(Constants.USER_EMAIL, registerRequestData.email)
                    editor.putInt(Constants.USER_TYPE, registerRequestData.userType)
                    editor.apply()

                    return Pair(NetworkHandler.STATUS_SUCCESS, registerRequestData.userType)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(NetworkHandler.STATUS_ERROR, null)
    }
}