package uz.coderodilov.retrofitinmvvm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.coderodilov.retrofitinmvvm.repository.MainRepository
import uz.coderodilov.retrofitinmvvm.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

/* 
* Created by Coder Odilov on 04/07/2023
*/

class Factory(private val mainRepository: MainRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(mainRepository) as T
        }

        throw IllegalArgumentException("Illegal argument")
    }
}