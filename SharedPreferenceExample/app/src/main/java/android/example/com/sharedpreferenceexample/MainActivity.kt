package android.example.com.sharedpreferenceexample

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedprefdemo.helper.PreferenceHelper

class MainActivity : AppCompatActivity() {

    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo helper
        preferenceHelper = PreferenceHelper(this)

        // Ánh xạ view
        val edtUserName = findViewById<EditText>(R.id.edt_user_name)
        val edtPassword = findViewById<EditText>(R.id.edt_password)
        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnShow = findViewById<Button>(R.id.btn_show)
        val btnDelete = findViewById<Button>(R.id.btn_delete)
        val txtResult = findViewById<TextView>(R.id.txt_result)

        // Xử lý sự kiện lưu
        btnSave.setOnClickListener {
            val username = edtUserName.text.toString()
            val password = edtPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                preferenceHelper.saveUser(username, password)
                txtResult.text = "Đã lưu dữ liệu!"
            } else {
                txtResult.text = "Vui lòng nhập đủ thông tin!"
            }
        }

        // Xử lý sự kiện hiển thị
        btnShow.setOnClickListener {
            val (username, password) = preferenceHelper.getUser()
            if (username != null && password != null) {
                txtResult.text = "Tên: $username\nMật khẩu: $password"
            } else {
                txtResult.text = "Không có dữ liệu!"
            }
        }

        // Xử lý sự kiện xóa
        btnDelete.setOnClickListener {
            preferenceHelper.deleteUser()
            txtResult.text = "Đã xóa dữ liệu!"
        }
    }
}
