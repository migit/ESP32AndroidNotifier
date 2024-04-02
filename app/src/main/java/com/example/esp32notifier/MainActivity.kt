package com.example.esp32notifier
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.esp32notifier.ui.theme.ESP32NotifierTheme
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    private val API_ENDPOINT = "API URL GOES HERE"
    private val NOTIFICATION_CHANNEL_ID = "1"
    private val NOTIFICATION_CHANNEL_NAME = "Default"
    private var previousId = "0"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            createNotificationChannel()
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    getSensorData()
                    handler.postDelayed(this, 5000) // 5 seconds
                }
            }, 5000) // 5 seconds
            ESP32NotifierTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }


    private  fun getSensorData(){
        // create a new thread to avoid network operations on the main thread
        Thread {
            // specify the API endpoint URL
            val url = URL(API_ENDPOINT)
            // create an HttpURLConnection instance
            val connection = url.openConnection() as HttpURLConnection
            // set the request method to GET
            connection.requestMethod = "GET"
            // get the response code
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // read the response data
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                // parse the JSON data into an array of strings
                val jsonArray = JSONArray(response.toString())
                // val stringArray = Array(jsonArray.length()) { i -> jsonArray.getString(i) }
                // parse the JSON data into an array of JSON objects
                val jsonObjectArray =
                    Array<JSONObject>(jsonArray.length()) { i -> jsonArray.getJSONObject(i) }
                // get the last object in the array
                val lastObject = jsonObjectArray.last()
                // get the value of the "id" field of the last object
                val title = lastObject.getString("title")
                val content = lastObject.getString("content")
                val id = lastObject.getString("id")
                // print the last element to the console
                Log.d("LAST element", lastObject.toString())
                if (id != previousId) {
                    showNotification(title, content)
                    previousId = id
                }
                // print the JSON data to the console
                Log.d("API Response", jsonArray.toString())
            } else {
                // handle the error response
                Log.e("API Error", "Response code: $responseCode")
            }
            // disconnect the connection
            connection.disconnect()
        }.start()
    }

private fun showNotification(title: String, cont: String) {

    val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title)
        .setContentText(cont)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(longArrayOf(0, 1000, 1000, 1000, 1000))
        .setDefaults(NotificationCompat.DEFAULT_LIGHTS or NotificationCompat.DEFAULT_SOUND)
    with(NotificationManagerCompat.from(this)) {
        notify(1, notificationBuilder.build())
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel() {
    val channel = NotificationChannel(
        NOTIFICATION_CHANNEL_ID,
        NOTIFICATION_CHANNEL_NAME,
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        description = "Default Notification Channel"
        enableLights(true)
        lightColor = Color.GREEN
    }

    val notificationManager: NotificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ESP32NotifierTheme {
        Greeting("Android")
    }
}
}
