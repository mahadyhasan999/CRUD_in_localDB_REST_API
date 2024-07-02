package com.example.loctry2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loctry2.databinding.ItemDataBinding

class DataAdapter(
    private val dataList: List<Data>,
    private val editListener: (Data) -> Unit,
    private val deleteListener: (Data) -> Unit
): RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: Data,  editListener: (Data) -> Unit, deleteListener: (Data) -> Unit) {
                binding.idTextView.text = data.id.toString()
                binding.nameTextView.text = data.name
                binding.ageTextView.text = data.age.toString()
                binding.emailTextView.text = data.email

                binding.editButton.setOnClickListener {
                    editListener(data)
                }
                binding.deleteButton.setOnClickListener {
                    deleteListener(data)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position], editListener, deleteListener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
