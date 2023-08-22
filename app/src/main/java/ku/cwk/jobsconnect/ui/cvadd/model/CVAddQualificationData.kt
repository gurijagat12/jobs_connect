package ku.cwk.jobsconnect.ui.cvadd.model

import com.google.gson.annotations.SerializedName
import ku.cwk.jobsconnect.ui.home.model.Qualification

data class CVAddQualificationData(
    var data: Qualification? = Qualification(),
    var user: User? = User()
)