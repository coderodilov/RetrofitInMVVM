package uz.coderodilov.retrofitinmvvm.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import uz.coderodilov.retrofitinmvvm.model.BaseResponse
import uz.coderodilov.retrofitinmvvm.model.User

/* 
* Created by Coder Odilov on 04/07/2023
*/

interface ApiService {

    @GET("users")
    @Headers(
        APIConfig.ACCEPT,
        APIConfig.CONTENT_TYPE,
        APIConfig.AUTH_TOKEN
    )
    fun getAllUsers(): Call<List<User>>


    @POST("users")
    @Headers(
        APIConfig.ACCEPT,
        APIConfig.CONTENT_TYPE,
        APIConfig.AUTH_TOKEN
    )
    fun createUser(@Body user: User): Call<User>


    @PATCH("users/{userId}")
    @Headers(
        APIConfig.ACCEPT,
        APIConfig.CONTENT_TYPE,
        APIConfig.AUTH_TOKEN
    )
    fun updateUser(@Path("userId") userId: String, @Body user: User): Call<User>


    @DELETE("users/{userId}")
    @Headers(
        APIConfig.ACCEPT,
        APIConfig.CONTENT_TYPE,
        APIConfig.AUTH_TOKEN
    )
    fun deleteUser(@Path("userId") userId: String): Call<BaseResponse>


    @GET("users")
    @Headers(
        APIConfig.ACCEPT,
        APIConfig.CONTENT_TYPE,
        APIConfig.AUTH_TOKEN
    )
    fun searchUserByName(@Query("name") query: String): Call<List<User>>
}