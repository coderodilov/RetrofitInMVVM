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

    private val usersList = MutableLiveData<ArrayList<User>>()
    private val filteredList = MutableLiveData<ArrayList<User>>()

    private val api = RetrofitClient.getRetrofit().create(ApiService::class.java)

    fun getUsersList(): MutableLiveData<ArrayList<User>> = usersList
    fun getFilteredList(): MutableLiveData<ArrayList<User>> = filteredList

    fun getAllUser() {
        api.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    usersList.postValue(response.body() as ArrayList<User>)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

    fun deleteUser(userId: String) {
        api.deleteUser(userId).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    val index = usersList.value?.indexOfFirst {
                        it.id == userId.toInt()
                    }
                    if (index != null) {
                        usersList.value?.removeAt(index)
                        usersList.postValue(usersList.value)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun createUser(user: User) {
        api.createUser(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val resUser = response.body()!!

                    usersList.value?.add(0, resUser)
                    usersList.postValue(usersList.value)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }


    fun updateUser(userId: String, user: User) {
        api.updateUser(userId, user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val resUser = response.body()!!

                    val index = usersList.value!!.indexOfFirst {
                        it.id == userId.toInt()
                    }

                    usersList.value!!.removeAt(index)
                    usersList.value!!.add(index, resUser)

                    usersList.postValue(usersList.value)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }


    fun searchUser(query: String) {
        api.searchUserByName(query).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val resUser = response.body()
                    filteredList.postValue(resUser as ArrayList<User>)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

}