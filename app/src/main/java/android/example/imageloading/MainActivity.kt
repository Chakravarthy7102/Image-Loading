package android.example.imageloading


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.URL

private var IMAGE_URL="https://thekitchensurvival.com/wp-content/uploads/2020/03/24-10-1024x1009.jpg"

class MainActivity : AppCompatActivity() {
    private lateinit var image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image=findViewById(R.id.imageB)
        //coroutine dispatcher with the scope of newly created thread
        CoroutineScope(Dispatchers.IO).launch{
            val bitmap=fetchImage(IMAGE_URL)
            withContext(Dispatchers.Main){
                image.setImageBitmap(bitmap)
            }
        }


    }

    private fun fetchImage(imageUrl: String):Bitmap? {
        //in kotlin we can return whole method description and kotlin only takes the
        //last element in the respective code block
       return try {
            val connection= URL(imageUrl).openConnection()
           //establishing the connection
            connection.connect()
            val inputStream=connection.getInputStream()
           //using bitmap converter to convert the inputStream into bitmap object
            val bitmap=BitmapFactory.decodeStream(inputStream)
            inputStream.close()
           //if bitmap is not null successful return the bitmap data
            bitmap
        }catch (e:Exception){
            Log.e("MainActivity", "io exception occurred $e")
           //else return null
            null
        }

    }
}