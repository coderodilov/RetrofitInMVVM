package uz.coderodilov.retrofitinmvvm

import android.annotation.SuppressLint
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
    private lateinit var list: ArrayList<User>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            Factory(MainRepository()))[MainViewModel::class.java]

        viewModel.getAllUsers().observe(this){
            list = it
            adapter = UserAdapter(list)
            binding.rvUsers.adapter = adapter

            adapter.setOnBtnClickListener{ position ->
                viewModel.deleteUser(list[position].id.toString())
            }
        }

        viewModel.getDeletedUserStatus().observe(this){
            list.removeAt(it)
            adapter.notifyDataSetChanged()
        }

        binding.btnGetAllUser.setOnClickListener {
            viewModel.getAllUsers()
        }

        binding.btnPostUser.setOnClickListener {
            showUserPostDialog()
        }

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