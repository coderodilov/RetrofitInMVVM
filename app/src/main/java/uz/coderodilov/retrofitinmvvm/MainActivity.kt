package uz.coderodilov.retrofitinmvvm


import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import uz.coderodilov.retrofitinmvvm.adapter.UserAdapter
import uz.coderodilov.retrofitinmvvm.databinding.ActivityMainBinding
import uz.coderodilov.retrofitinmvvm.databinding.CreateUserDialogBinding
import uz.coderodilov.retrofitinmvvm.databinding.UpdateUserDialogBinding
import uz.coderodilov.retrofitinmvvm.factory.Factory
import uz.coderodilov.retrofitinmvvm.model.User
import uz.coderodilov.retrofitinmvvm.repository.MainRepository
import uz.coderodilov.retrofitinmvvm.viewmodel.MainViewModel
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var userAdapter: UserAdapter
    private lateinit var usersList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        viewModel = ViewModelProvider(this, Factory(MainRepository()))[MainViewModel::class.java]

        viewModel.getAllUsers().observe(this) {
            usersList = it
            userAdapter.submitList(usersList)
            binding.rvUsers.adapter = userAdapter

            userAdapter.setOnBtnDeleteClickListener { index -> showDeleteUserDialog(index) }

            userAdapter.setOnBtnEditClickListener { index -> showUpdateUserDialog(index) }
        }


        binding.btnPostUser.setOnClickListener { showCreateUserDialog() }

        binding.searchViewUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?):
                    Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?):
                    Boolean {
                viewModel.searchUserByName(newText!!).observe(this@MainActivity) {
                    userAdapter.submitList(it)
                }
                return true
            }
        })

        onBackPressedEvent()
    }

    private fun showDeleteUserDialog(index: Int) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Warning")
            .setIcon(R.drawable.baseline_warning_24)
            .setMessage("User will be delete permanently...")
            .setPositiveButton("Proceed") { _, _ ->
                viewModel.deleteUser(usersList[index].id.toString())
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        dialog.show()
    }


    private fun showCreateUserDialog() {
        val dialog = Dialog(this)
        val dialogBinding = CreateUserDialogBinding.inflate(LayoutInflater.from(this))
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnSaveAndPostUser.setOnClickListener {
            val status = if (dialogBinding.checkboxUserStatus.isChecked) "active" else "inactive"
            with(dialogBinding) {
                if (edName.text.isNotEmpty() && edMail.text.isNotEmpty()) {
                    viewModel.createUser(
                        User(
                            Random().nextInt(324580) + 1,
                            edName.text.toString(),
                            edMail.text.toString(),
                            "Male",
                            status
                        )
                    )
                    userAdapter.notifyItemRangeChanged(0, usersList.lastIndex)
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }


    private fun showUpdateUserDialog(position: Int) {
        val dialog = Dialog(this)
        val dialogBinding = UpdateUserDialogBinding.inflate(LayoutInflater.from(this))
        dialog.setContentView(dialogBinding.root)

        val selectedUser = usersList[position]

        dialogBinding.edName.setText(selectedUser.name)
        dialogBinding.edMail.setText(selectedUser.email)
        dialogBinding.checkboxUserStatus.isChecked = selectedUser.status == "active"

        dialogBinding.btnSaveAndPostUser.setOnClickListener {
            val status = if (dialogBinding.checkboxUserStatus.isChecked) "active" else "inactive"

            with(dialogBinding) {
                if (edName.text.isNotEmpty() && edMail.text.isNotEmpty()) {
                    viewModel.updateUser(
                        selectedUser.id.toString(),
                        User(
                            selectedUser.id,
                            edName.text.toString(),
                            edMail.text.toString(),
                            "Male",
                            status
                        )
                    )
                    userAdapter.notifyItemRangeChanged(0, usersList.lastIndex)
                    dialog.dismiss()
                }
            }

        }

        dialog.show()
    }


    private fun onBackPressedEvent() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.searchViewUsers.isIconified.not())
                    binding.searchViewUsers.onActionViewCollapsed()
                else finish()
            }

        })
    }

}