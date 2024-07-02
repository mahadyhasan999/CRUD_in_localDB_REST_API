package com.example.loctry2

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loctry2.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var dataAdapter: DataAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.btnAddData.setOnClickListener {
            showCreateDialog()
        }
        binding.fabRefresh.setOnClickListener {
            fetchData()
        }
        fetchData()
    }

    private fun fetchData() {

        RetrofitClient.instance.getData().enqueue(object : Callback<List<Data>> {

            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                if (response.isSuccessful) {
                    val dataList = response.body() ?: emptyList()
                    dataAdapter = DataAdapter(dataList, this@MainActivity::showEditDialog, this@MainActivity::deleteData)
                    binding.recyclerView.adapter = dataAdapter
                } else {
                    Toast.makeText(this@MainActivity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showEditDialog(data: Data) {

        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_data, null)

        val idTextView = dialogView.findViewById<TextView>(R.id.idTextView)
        val editName = dialogView.findViewById<EditText>(R.id.editName)
        val editAge = dialogView.findViewById<EditText>(R.id.editAge)
        val editEmail = dialogView.findViewById<EditText>(R.id.editEmail)

        idTextView.text = data.id.toString()
        editName.setText(data.name)
        editAge.setText(data.age.toString())
        editEmail.setText(data.email)

        AlertDialog.Builder(this)
            .setTitle("Edit Data")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val updatedData = Data(
                    data.id,
                    editName.text.toString(),
                    editAge.text.toString().toInt(),
                    editEmail.text.toString()
                )
                updateData(updatedData)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateData(data: Data) {
        // Update the data in the API using Retrofit
        RetrofitClient.instance.updateData(data.id, data.name, data.age, data.email)
            .enqueue(object : Callback<Void> {

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Data updated successfully", Toast.LENGTH_SHORT).show()
                        fetchData() // Refresh the data list
                    } else {
                        Toast.makeText(this@MainActivity, "Failed to update data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun showCreateDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_data, null)

        val editName = dialogView.findViewById<EditText>(R.id.createName)
        val editAge = dialogView.findViewById<EditText>(R.id.createAge)
        val editEmail = dialogView.findViewById<EditText>(R.id.createEmail)

        AlertDialog.Builder(this)
            .setTitle("Create Data")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val newData = Data(
                    id = 0, // ID will be auto-generated by the database
                    name = editName.text.toString(),
                    age = editAge.text.toString().toInt(),
                    email = editEmail.text.toString()
                )
                createData(newData)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun createData(data: Data) {
        RetrofitClient.instance.createData(data).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Data created successfully", Toast.LENGTH_SHORT).show()
                    fetchData() // Refresh the data list
                } else {
                    Toast.makeText(this@MainActivity, "Failed to create data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun deleteData(data: Data) {
    AlertDialog.Builder(this)
        .setTitle("Delete Data")
        .setMessage("Are you sure you want to delete this data?")
        .setPositiveButton("Yes") { dialog, _ ->
            RetrofitClient.instance.deleteData(data.id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Data deleted successfully", Toast.LENGTH_SHORT).show()
                        fetchData() // Refresh the data list
                    } else {
                        Toast.makeText(this@MainActivity, "Failed to delete data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
                }
            })
            dialog.dismiss()
        }
        .setNegativeButton("No", null)
        .show()
    }
}