package com.gamal.Pixabay

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal
import java.net.URL
import javax.net.ssl.HttpsURLConnection
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var isConnected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment)
        navController.setGraph(R.navigation.main_nav)

    }
    override fun onResume() {
        super.onResume()
        checkInternetConnection()

    }

    private fun checkInternetConnection() {
        if (isNetworkAvailable()) {
            var connection: HttpsURLConnection? = null
            try {
                connection =
                    URL("https://clients3.google.com/generate_204").openConnection() as HttpsURLConnection
                connection.setRequestProperty("User-Agent", "Android")
                connection.setRequestProperty("Connection", "close")
                connection.connectTimeout = 1000
                connection.connect()
                isConnected = connection.responseCode == 204 && connection.contentLength == 0
                isConnected = true
                connection.disconnect()
            } catch (e: Exception) {
                isConnected = false
                connection?.disconnect()
                showInternetDialog()
            }
        } else {
            isConnected = false
            showInternetDialog()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        // For Android Q and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }  else {
            connectivityManager.allNetworkInfo?.forEach { networkInfo ->
                if (networkInfo.isConnected) return true
            }
        }


        return false

    }

    private fun showInternetDialog() {
        NoInternetDialogSignal.Builder(this, lifecycle).apply {
            dialogProperties.apply {

                cancelable = false
                noInternetConnectionTitle = getString(R.string.No_Internet)
                noInternetConnectionMessage =getString(R.string.Check_your_Internet_connection_and_try_again)
                showInternetOnButtons = true
                wifiOnButtonText = getString(R.string.Wifi)
                mobileDataOnButtonText =  getString(R.string.Mobile_data)
                onAirplaneModeTitle = getString(R.string.No_Internet)
                onAirplaneModeMessage =getString(R.string.You_have_turned_on_the_airplane_mode)
                pleaseTurnOffText =getString(R.string.Please_turn_off)
                airplaneModeOffButtonText =getString(R.string. Airplane_mode)
                showAirplaneModeOffButtons = true
            }
        }.build()
    }
}
