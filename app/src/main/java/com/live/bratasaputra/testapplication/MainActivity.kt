package com.live.bratasaputra.testapplication

import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.live.bratasaputra.testapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var appBarConfiguration: AppBarConfiguration
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    printAllFirebaseId()

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setSupportActionBar(binding.appBarMain.toolbar)

    binding.appBarMain.fab.setOnClickListener { view ->
      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show()
      FirebaseAnalytics.getInstance(this).logEvent("button_clicked_${BuildConfig.BUILD_TYPE}", Bundle().apply {
        this.putString("button_name", "main_fab")
      })
    }
    val drawerLayout: DrawerLayout = binding.drawerLayout
    val navView: NavigationView = binding.navView
    val navController = findNavController(R.id.nav_host_fragment_content_main)
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    appBarConfiguration = AppBarConfiguration(setOf(
        R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
    setupActionBarWithNavController(navController, appBarConfiguration)
    navView.setupWithNavController(navController)

    handleDynamicLink()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.main, menu)
    return true
  }

  override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_main)
    return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
  }

  private fun handleDynamicLink() {
    FirebaseDynamicLinks.getInstance()
      .getDynamicLink(intent)
      .addOnSuccessListener {
        Log.d("Firebase", "Deeplink is ${it?.link}")
      }
      .addOnFailureListener {
        Log.e("Firebase", "getDynamicLInk:onFailure", it)
      }
  }

  private fun printAllFirebaseId() {
    Thread {
      Log.d(
        "Firebase",
        "FirebaseInstanceId: ${FirebaseAnalytics.getInstance(this).firebaseInstanceId}"
      )
    }.start()
    Thread {
      FirebaseInstallations.getInstance().id.addOnCompleteListener {
        Log.d("Firebase", "FirebaseInstallationId: ${it.result}")
      }
    }.start()
    Thread {
      FirebaseAnalytics.getInstance(this).appInstanceId.addOnCompleteListener {
        Log.d(
          "Firebase",
          "FirebaseAppInstanceId: ${it.result}"
        )
      }
    }.start()
  }
}