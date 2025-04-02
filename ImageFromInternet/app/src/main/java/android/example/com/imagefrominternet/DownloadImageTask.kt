package android.example.com.imagefrominternet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import android.widget.ProgressBar
import java.net.HttpURLConnection
import java.net.URL

class DownloadImageTask(
    private val imageView: ImageView,
    private val progressBar: ProgressBar
) : AsyncTask<String, Int, Bitmap?>() {

    override fun onPreExecute() {
        super.onPreExecute()
        progressBar.visibility = ProgressBar.VISIBLE
    }

    override fun doInBackground(vararg urls: String?): Bitmap? {
        val urlString = urls[0] ?: return null
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val inputStream = connection.inputStream
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        progressBar.visibility = ProgressBar.GONE
        if (result != null) {
            imageView.setImageBitmap(result)
        }
    }
}
