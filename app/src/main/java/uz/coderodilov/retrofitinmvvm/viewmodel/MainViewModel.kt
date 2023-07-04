package uz.coderodilov.retrofitinmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.coderodilov.retrofitinmvvm.model.BaseResponse
import uz.coderodilov.retrofitinmvvm.model.User
import uz.coderodilov.retrofitinmvvm.repository.MainRepository

/* 
* Created by Coder Odilov on 04/07/2023
*/

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val list = MutableLiveData<ArrayList<User>>()
    val listOfUsers get() = list

    private val toastMessage = MutableLiveData<String>()
    val toastMsg get() = toastMessage

    fun getAllUsers() {
        mainRepository.getApiService().getAllUsers().enqueue(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) list.postValue(response.body() as ArrayList<User>)
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun createUser(user: User){
        mainRepository.getApiService().createUser(user).enqueue(object :Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) toastMessage.postValue(response.body()?.id)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                toastMessage.postValue(t.toString())
            }

        })
    }

}