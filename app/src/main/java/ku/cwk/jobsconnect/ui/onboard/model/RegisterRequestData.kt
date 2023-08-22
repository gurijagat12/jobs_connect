package ku.cwk.jobsconnect.ui.onboard.model

import com.google.gson.annotations.SerializedName

data class RegisterRequestData(
    @SerializedName("first_name")
    var firstName: String = "",
    @SerializedName("last_name")
    var lastName: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("user_type")
    var userType: Int = 0,
    var password: String = ""
)