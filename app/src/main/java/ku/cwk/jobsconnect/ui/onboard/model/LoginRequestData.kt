package ku.cwk.jobsconnect.ui.onboard.model

import com.google.gson.annotations.SerializedName

data class LoginRequestData(
    @SerializedName("username")
    val userName: String,
    val password: String
)
