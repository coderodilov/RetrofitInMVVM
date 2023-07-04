package uz.coderodilov.retrofitinmvvm.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/* 
* Created by Coder Odilov on 04/07/2023
*/

class RetrofitClient {
    companion object{
        private const val BASE_URL = "https://gorest.co.in/public/v2/"

        fun getRetrofit() : Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
    }
}