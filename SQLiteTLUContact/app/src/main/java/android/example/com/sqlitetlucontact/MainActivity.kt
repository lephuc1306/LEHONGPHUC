package android.example.com.sqlitetlucontact

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo database helper
        databaseHelper = DatabaseHelper(this)

        // Ánh xạ view
        val editTextName = findViewById<EditText>(R.id.edt_text_name)
        val editTextPhone = findViewById<EditText>(R.id.edt_text_phone)
        val buttonAdd = findViewById<Button>(R.id.btn_add)
        val buttonUpdate = findViewById<Button>(R.id.btn_update)
        val buttonDelete = findViewById<Button>(R.id.btn_delete)
        val buttonShow = findViewById<Button>(R.id.btn_show)
        val textViewResult = findViewById<TextView>(R.id.txt_view_result)

        // Xử lý sự kiện thêm
        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()
            if (name.isNotEmpty() && phone.isNotEmpty()) {
                if (databaseHelper.addContact(name, phone)) {
                    textViewResult.text = "Thêm thành công!"
                } else {
                    textViewResult.text = "Lỗi khi thêm!"
                }
            } else {
                textViewResult.text = "Vui lòng nhập đủ thông tin!"
            }
        }

        // Xử lý sự kiện sửa
        buttonUpdate.setOnClickListener {
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()
            if (name.isNotEmpty() && phone.isNotEmpty()) {
                if (databaseHelper.updateContact(name, phone)) {
                    textViewResult.text = "Sửa thành công!"
                } else {
                    textViewResult.text = "Không tìm thấy liên hệ!"
                }
            }
        }

        // Xử lý sự kiện xóa
        buttonDelete.setOnClickListener {
            val name = editTextName.text.toString()
            if (name.isNotEmpty()) {
                if (databaseHelper.deleteContact(name)) {
                    textViewResult.text = "Xóa thành công!"
                } else {
                    textViewResult.text = "Không tìm thấy liên hệ!"
                }
            }
        }

        // Xử lý sự kiện hiển thị
        buttonShow.setOnClickListener {
            val contacts = databaseHelper.getAllContacts()
            textViewResult.text = if (contacts.isNotEmpty()) {
                contacts.joinToString("\n") { "${it.first}: ${it.second}" }
            } else {
                "Không có dữ liệu!"
            }
        }
    }
}
