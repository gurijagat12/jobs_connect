package ku.cwk.jobsconnect.ui.home.model

import com.google.gson.annotations.SerializedName


data class Personal(

    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("last_name") var lastName: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("user_type") var userType: Int? = null

)