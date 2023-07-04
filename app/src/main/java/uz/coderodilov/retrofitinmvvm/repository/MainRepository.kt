package uz.coderodilov.retrofitinmvvm.repository
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.coderodilov.retrofitinmvvm.model.BaseResponse
import uz.coderodilov.retrofitinmvvm.model.User
import uz.coderodilov.retrofitinmvvm.retrofit.ApiService
import uz.coderodilov.retrofitinmvvm.retrofit.RetrofitClient

/* 
* Created by Coder Odilov on 04/07/2023
*/

class MainRepository {
    private val list = MutableLiveData<ArrayList<User>>()
    val deletedUser = MutableLiveData<Int>()

    private val api = RetrofitClient.getRetrofit().create(ApiService::class.java)

    fun getList():MutableLiveData<ArrayList<User>> = list

    fun getAllUser() : ArrayList<User>{
        val listOfUsers: ArrayList<User> = ArrayList()

        api.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful){
                    listOfUsers.addAll(response.body() ?: emptyList())
                    list.postValue(response.body() as ArrayList<User>)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return listOfUsers
    }

    fun deleteUser(userId: String){
        api.deleteUser(userId).enqueue(object :Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful){
                    val index = list.value?.indexOfFirst {
                        it.id == userId.toInt()
                    }
                    if (index != null){
                        deletedUser.postValue(index)
                        list.value?.removeAt(index)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {

            }
        })
    }

    fun createUser(user: User){
        api.createUser(user).enqueue(object :Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
               t.printStackTrace()
            }

        })
    }


    fun searchUser(query:String){
        api.searchUserByName(query).enqueue(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {

            }

        })
    }

}