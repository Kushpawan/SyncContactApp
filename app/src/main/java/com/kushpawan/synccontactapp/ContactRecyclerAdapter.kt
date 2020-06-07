package com.kushpawan.synccontactapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kushpawan.synccontactapp.database.ContactData

class ContactRecyclerAdapter(private var genresList: ArrayList<ContactData>) :
    RecyclerView.Adapter<ContactRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.contact_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genresList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return genresList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var name: TextView
        private lateinit var email: TextView
        private lateinit var number: TextView

        fun bind(
            item: ContactData
        ) = with(itemView) {
            name = itemView.findViewById(R.id.name_text)
            email = itemView.findViewById(R.id.email_text)
            number = itemView.findViewById(R.id.number_text)

            name.text = item.name
            email.text = item.email
            number.text = item.mobileNumber

        }
    }
}