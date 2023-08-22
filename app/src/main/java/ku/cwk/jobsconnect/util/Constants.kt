package ku.cwk.jobsconnect.util

class NetworkHandler {
    companion object {
        const val STATUS_NONE = "NONE"
        const val STATUS_LOADING = "LOADING"
        const val STATUS_SUCCESS = "SUCCESS"
        const val STATUS_ERROR = "ERROR"
    }
}

class Constants {
    companion object {
        const val SHARED_PREF = "jobs_connect_pref"

        const val INTENT_DATA = "intent_data"
        const val USER_ID = "user_id"
        const val USER_EMAIL = "user_email"
        const val USER_TYPE = "user_type"
        const val INTENT_EDUCATION = "intent_education"

        //Gender
        const val GENDER_MALE = "Male"
        const val GENDER_FEMALE = "Female"
        const val GENDER_OTHER = "Other"
    }
}
