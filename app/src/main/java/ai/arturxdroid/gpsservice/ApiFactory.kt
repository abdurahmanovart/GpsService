package ai.arturxdroid.gpsservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query

public interface GpsApI {

    @POST("")
    suspend fun postData(@Query("data") data: GpsData)

}


object ApiFactory {

    private const val BASE_URL = "https://localhost:1111/"

    private lateinit var retrofit: GpsApI

    @JvmStatic
    public fun getGpsApi(): GpsApI {
        if (!::retrofit.isInitialized) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GpsApI::class.java)

        }
        return retrofit
    }
}
