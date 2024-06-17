package vn.poly.mob305.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBase {

    val BASE_URL = "https://cataas.com"
    fun getClient(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}