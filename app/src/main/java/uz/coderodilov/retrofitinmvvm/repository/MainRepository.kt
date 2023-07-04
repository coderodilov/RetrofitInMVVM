package uz.coderodilov.retrofitinmvvm.repository
import uz.coderodilov.retrofitinmvvm.retrofit.ApiService
import uz.coderodilov.retrofitinmvvm.retrofit.RetrofitClient

/* 
* Created by Coder Odilov on 04/07/2023
*/

class MainRepository {

//    fun getAllUser() : ArrayList<User>{
//        val listOfUsers: ArrayList<User> = ArrayList()
//        val api = RetrofitClient.getRetrofit().create(ApiService::class.java)
//
//        api.getAllUsers().enqueue(object : Callback<List<User>> {
//            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
//                if (response.isSuccessful){
//                    listOfUsers.addAll(response.body() ?: emptyList())
//                }
//            }
//
//            override fun onFailure(call: Call<List<User>>, t: Throwable) {
//                t.printStackTrace()
//            }
//        })
//
//        return listOfUsers
//    }

    fun getApiService(): ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

}