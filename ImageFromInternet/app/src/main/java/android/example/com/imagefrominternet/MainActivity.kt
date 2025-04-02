package android.example.com.imagefrominternet
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.DataSource

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtImageUrl = findViewById<EditText>(R.id.edtImageUrl)
        val btnLoadImage = findViewById<Button>(R.id.btnLoadImage)
        val imgView = findViewById<ImageView>(R.id.imgView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        btnLoadImage.setOnClickListener {
            val imageUrl = edtImageUrl.text.toString()

            if (imageUrl.isNotEmpty()) {
                progressBar.visibility = View.VISIBLE  // Hiện ProgressBar

                Glide.with(this)
                    .load(imageUrl)
                    .listener(object : RequestListener<android.graphics.drawable.Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE  // Ẩn ProgressBar nếu lỗi
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable>?,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE  // Ẩn ProgressBar khi tải xong
                            return false                        }
                    })
                    .into(imgView)
            }
        }
    }
}

