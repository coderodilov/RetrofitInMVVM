package uz.coderodilov.retrofitinmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.coderodilov.retrofitinmvvm.databinding.UserItemBinding
import uz.coderodilov.retrofitinmvvm.model.User

/* 
* Created by Coder Odilov on 04/07/2023
*/

class UserAdapter(private val list: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var onBtnDeleteClicked: OnBtnDeleteClicked

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: User) {
            binding.tvUserId.text = user.id.toString()
            binding.tvUserMail.text = user.email
            binding.tvUserName.text = user.name
            binding.tvUserStatus.text = user.status
            binding.tvUserGender.text = user.gender

            binding.btnDelete.setOnClickListener {
                onBtnDeleteClicked.setOnBtnClickListener(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    fun interface OnBtnDeleteClicked{
        fun setOnBtnClickListener(position: Int)
    }

    fun setOnBtnClickListener(listener:OnBtnDeleteClicked){
        onBtnDeleteClicked = listener
    }
}