package uz.coderodilov.retrofitinmvvm.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.coderodilov.retrofitinmvvm.databinding.UserItemBinding
import uz.coderodilov.retrofitinmvvm.model.User

/* 
* Created by Coder Odilov on 04/07/2023
*/

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var onBtnDeleteClicked: OnBtnDeleteClicked
    private lateinit var onBtnEditClicked: OnBtnEditClicked

    private val usersList = ArrayList<User>()

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(user: User) {
            binding.tvUserId.text = user.id.toString()
            binding.tvUserMail.text = user.email
            binding.tvUserName.text = user.name
            binding.tvUserStatus.text = user.status
            binding.tvUserGender.text = user.gender

            if (user.status == "active") binding.userContainer.setBackgroundColor(Color.parseColor("#DCEDC8"))
            else binding.userContainer.setBackgroundColor(Color.parseColor("#E1BEE7"))

            binding.btnDelete.setOnClickListener {
                onBtnDeleteClicked.setOnBtnDeleteClickListener(adapterPosition)
            }

            binding.btnEdit.setOnClickListener {
                onBtnEditClicked.setOnBtnEditClickListener(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = usersList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(usersList[position])
    }


    fun interface OnBtnDeleteClicked{
        fun setOnBtnDeleteClickListener(position: Int)
    }

    fun interface OnBtnEditClicked{
        fun setOnBtnEditClickListener(position: Int)
    }

    fun setOnBtnDeleteClickListener(listener:OnBtnDeleteClicked){
        onBtnDeleteClicked = listener
    }

    fun setOnBtnEditClickListener(listener:OnBtnEditClicked){
        onBtnEditClicked = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<User>){
        this.usersList.clear()
        this.usersList.addAll(list)
        notifyDataSetChanged()
    }
}