package ku.cwk.jobsconnect.network

import ku.cwk.jobsconnect.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        val instance = ApiClient()
    }

    private var _serviceApi: ApiInterface? = null
    val serviceApi: ApiInterface?
        get() {
            if (_serviceApi == null) {
                _serviceApi = retrofitApi.create(ApiInterface::class.java)
            }
            return _serviceApi
        }

    private val retrofitApi: Retrofit
        get() {

            val httpClient = OkHttpClient.Builder()
            httpClient.writeTimeout(3, TimeUnit.MINUTES)
            httpClient.readTimeout(3, TimeUnit.MINUTES)
            httpClient.connectTimeout(3, TimeUnit.MINUTES)

            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(logging)
            }

            return Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
}