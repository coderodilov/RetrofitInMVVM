package uz.coderodilov.retrofitinmvvm.retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url
import uz.coderodilov.retrofitinmvvm.model.BaseResponse
import uz.coderodilov.retrofitinmvvm.model.User

/* 
* Created by Coder Odilov on 04/07/2023
*/

interface ApiService {
    @GET("users")
    @Headers("Accept:application/json",
        "Content-Type:application/json",
        "Authorization:Bearer a9fe2814ba79c5c03615d26a004cb2652390408f97694bd8bacb86e2616d7538")
    fun getAllUsers(): Call<List<User>>

    @POST("users")
    @Headers("Accept:application/json",
    "Content-Type:application/json",
    "Authorization:Bearer a9fe2814ba79c5c03615d26a004cb2652390408f97694bd8bacb86e2616d7538")
    fun createUser(@Body user: User) : Call<BaseResponse>

}