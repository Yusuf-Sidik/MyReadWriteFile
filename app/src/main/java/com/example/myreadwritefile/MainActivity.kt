package com.example.myreadwritefile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myreadwritefile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNew.setOnClickListener(this)
        binding.btnOpen.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_new -> newFile()
            R.id.btn_open -> showList()
            R.id.btn_save -> saveFile()
        }
    }

    private fun saveFile() {
        when {
            binding.edtTitle.text.toString().isEmpty() ->
                Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            binding.edtFile.text.toString().isEmpty() ->
                Toast.makeText(this, "Konten harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            else -> {
                val title = binding.edtTitle.text.toString()
                val text = binding.edtFile.text.toString()
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "Saving" + fileModel.filename + "file", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showList() {
        val items = fileList()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih file yang diinginkan")
        builder.setItems(items) {dialog, item -> loadData(items[item].toString())}
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFormFile(this,title)
        binding.edtTitle.setText(fileModel.filename)
        binding.edtFile.setText(fileModel.data)
        Toast.makeText(this, "Loading" + fileModel.filename + "data", Toast.LENGTH_SHORT).show()
    }

    private fun newFile() {
        binding.edtTitle.setText("")
        binding.edtFile.setText("")
        Toast.makeText(this, "Clearing File", Toast.LENGTH_SHORT).show()
    }


}