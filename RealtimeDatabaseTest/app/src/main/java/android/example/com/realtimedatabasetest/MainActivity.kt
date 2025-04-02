package android.example.com.realtimedatabasetest
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo Firebase Authentication & Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")

        // Ánh xạ View
        val edtEmail = findViewById<EditText>(R.id.edt_text_email)
        val edtPassword = findViewById<EditText>(R.id.edt_text_password)
        val btnRegister = findViewById<Button>(R.id.btn_register)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnShowData = findViewById<Button>(R.id.btn_show_data)
        val txtViewResult = findViewById<TextView>(R.id.txt_view_result)

        // Xử lý đăng ký
        btnRegister.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isNotEmpty() && password.length >= 6) {
                registerUser(email, password)
            } else {
                Toast.makeText(this, "Email hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý đăng nhập
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý hiển thị dữ liệu từ Firebase
        btnShowData.setOnClickListener {
            val user = auth.currentUser
            if (user != null) {
                Toast.makeText(this, "Người dùng đã đăng nhập: ${user.email}", Toast.LENGTH_SHORT).show()
            }
            user?.let {
                val userId = it.uid

                database.child(userId).get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val email = snapshot.child("email").value.toString()
                        val password = snapshot.child("password").value.toString()

                        txtViewResult.text = "Email: $email\nMật khẩu: $password"
                    } else {
                        Toast.makeText(this, "Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Lỗi khi lấy dữ liệu!", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this,
                        "Đăng nhập thất bại: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                val user = auth.currentUser
                user?.let {
                    val userId = it.uid
                    val userData = User(email, password)

                    // Lưu dữ liệu vào Firebase Realtime Database
                    database.child(userId).setValue(userData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Dữ liệu đã được lưu!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Lỗi khi lưu dữ liệu!", Toast.LENGTH_SHORT).show()
                        }
                }
            }
    }
    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Đăng ký thất bại: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}


// Định nghĩa lớp User để lưu vào Firebase
data class User(val email: String = "", val password: String = "")
