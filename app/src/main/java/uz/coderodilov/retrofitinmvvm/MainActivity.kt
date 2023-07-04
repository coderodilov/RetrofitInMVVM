package uz.coderodilov.retrofitinmvvm

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import uz.coderodilov.retrofitinmvvm.adapter.UserAdapter
import uz.coderodilov.retrofitinmvvm.databinding.ActivityMainBinding
import uz.coderodilov.retrofitinmvvm.databinding.CreateUserDialogBinding
import uz.coderodilov.retrofitinmvvm.factory.Factory
import uz.coderodilov.retrofitinmvvm.model.User
import uz.coderodilov.retrofitinmvvm.repository.MainRepository
import uz.coderodilov.retrofitinmvvm.viewmodel.MainViewModel
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            Factory(MainRepository()))[MainViewModel::class.java]

        viewModel.listOfUsers.observe(this){
            adapter = UserAdapter(it)
            binding.rvUsers.adapter = adapter
        }

        viewModel.toastMsg.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        binding.btnGetAllUser.setOnClickListener { viewModel.getAllUsers() }

        binding.btnPostUser.setOnClickListener { showUserPostDialog() }

    }


    private fun showUserPostDialog() {
        val dialog = Dialog(this)
        val dialogBinding = CreateUserDialogBinding.inflate(LayoutInflater.from(this))
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnSaveAndPostUser.setOnClickListener {
            with(dialogBinding){
                if (edName.text.isNotEmpty() && edMail.text.isNotEmpty()){
                    viewModel.createUser(
                        User(Random().nextInt(324580) + 1,
                            edName.text.toString(),
                            edMail.text.toString(),
                            "Male",
                            "Active"))
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }

}