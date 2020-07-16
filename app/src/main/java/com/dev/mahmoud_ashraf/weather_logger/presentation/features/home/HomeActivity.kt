package com.dev.mahmoud_ashraf.weather_logger.presentation.features.home

import android.location.Location
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.dev.mahmoud_ashraf.weather_logger.R
import com.dev.mahmoud_ashraf.weather_logger.presentation.core.LocationApi
import timber.log.Timber

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.toolbar))




        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->

            LocationApi(
                this,
                callbacks = object :
                    LocationApi.Callbacks {
                    override fun onSuccess(location: Location) {
                        Timber.e("done " + location.latitude)
                    }

                    override fun onFailed(locationFailedEnum: LocationApi.LocationFailedEnum) {
                        Timber.e("error " + locationFailedEnum.name)
                    }

                })

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}