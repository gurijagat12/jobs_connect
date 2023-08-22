package ku.cwk.jobsconnect.ui.onboard.model

import com.google.gson.annotations.SerializedName
import ku.cwk.jobsconnect.ui.home.model.Personal

data class LoginResponseData(
    val message: String,
    @SerializedName("data")
    val personal: Personal
)
