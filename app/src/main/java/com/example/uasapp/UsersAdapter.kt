package com.example.uasapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uasapp.data.DataItem
import com.squareup.picasso.Picasso

class UsersAdapter(private val clickListener: UserClickListener) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private val data: MutableList<DataItem?> = mutableListOf()

    //region mengirim data ke halaman ketiga ketika user mengklik item
    interface UserClickListener {
        fun onUserClick(firstName: String?, lastName: String?)
    }
    //endregion

    //region menginisalisasi komponen objek ke dalam recycle view yang berasal dari item_user.xml
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.third_name)
        val emailTextView: TextView = itemView.findViewById(R.id.email)
        val imageView: ImageView = itemView.findViewById(R.id.avatar)
    }
    //endregion

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    //region menampilkan data dari json ke komponen yang ada pada item_user, dan juga mengirim data
    //username yang di klik ke halaman ketiga
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = data[position]
        holder.nameTextView.text = "${dataItem?.firstName} ${dataItem?.lastName}"
        holder.emailTextView.text = dataItem?.email

        Picasso.get().load(dataItem?.avatar).into(holder.imageView)

        holder.itemView.setOnClickListener {
            clickListener.onUserClick(dataItem?.firstName, dataItem?.lastName)
        }
    }
    //endregion

    //region dua fungsi yang digunakan untuk refresh dan load data
    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    fun addData(newData: List<DataItem?>?) {
        if (newData != null) {
            data.addAll(newData)
        }
        notifyDataSetChanged()
    }
    //endregion
}
