package uz.coderodilov.retrofitinmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import uz.coderodilov.retrofitinmvvm.model.User
import uz.coderodilov.retrofitinmvvm.repository.MainRepository

/* 
* Created by Coder Odilov on 04/07/2023
*/

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getAllUsers(): LiveData<ArrayList<User>> {
       mainRepository.getAllUser()
       return mainRepository.getUsersList()
    }

    fun deleteUser(userId:String){
        mainRepository.deleteUser(userId)
    }

    fun createUser(user:User){
        mainRepository.createUser(user)
    }

    fun updateUser(userId:String, user:User){
        mainRepository.updateUser(userId, user)
    }

    fun searchUserByName(query:String) : LiveData<ArrayList<User>>{
        mainRepository.searchUser(query)
        return mainRepository.getFilteredList()
    }

}