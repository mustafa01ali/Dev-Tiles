package xyz.mustafaali.devqstiles;

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import xyz.mustafaali.devqstiles.model.Feature
import xyz.mustafaali.devqstiles.ui.FeaturesAdapter


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_help -> {
                openHelpVideo()
                true
            }
            R.id.menu_share_app -> {
                shareApp()
                true
            }
            R.id.menu_rate_app -> {
                openStoreListing()
                true
            }
            R.id.menu_request_feature -> {
                openEmailClient()
                true
            }
            R.id.menu_oss_licenses -> {
                startActivity(Intent(this, OssLicensesMenuActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openStoreListing() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("market://details?id=xyz.mustafaali.devqstiles")
        try {
            startActivity(intent)
        } catch(e: ActivityNotFoundException) {
            Timber.e("Couldn't launch activity, maybe PlayStore is not installed")
        }
    }

    private fun openHelpVideo() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=tdSAobQq1nQ")))
    }

    private fun openEmailClient() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf("mustafaali.apps@gmail.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "[DevTiles] Feature Request")
        startActivity(intent)
    }

    private fun initUi() {
        copyButton.setOnClickListener({ sharePermissionsCommand() })
        featuresRecyclerView.layoutManager = LinearLayoutManager(this)
        featuresRecyclerView.setHasFixedSize(true)
        featuresRecyclerView.adapter = FeaturesAdapter(getFeaturesList()) {}
    }

    private fun shareApp() {
        share(R.string.msg_share_app)
    }

    private fun sharePermissionsCommand() {
        share(R.string.permission_command)
    }

    private fun share(resId: Int) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(resId))
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    private fun getFeaturesList(): List<Feature> {
        return listOf(
                Feature("Toggle USB Debugging", "Enable/disable USB debugging from your notification drawer", R.drawable.ic_toggle_usb_debugging),
                Feature("Keep Screen On", "Keep screen on when connected via USB, but turn it off when connected to a charger", R.drawable.ic_toggle_keep_screen_on),
                Feature("Show Touches", "Show touch points when you touch the screen, ideal for demos", R.drawable.ic_toggle_show_taps),
                Feature("Demo Mode", "Cleans up the status bar for those perfect screenshots", R.drawable.ic_toggle_demo_mode),
                Feature("Change Animator Duration", "Change the default animator duration to easily debug animations", R.drawable.ic_animator_duration),
                Feature("Toggle Animation Scale", "Enable/disable all animations with one click, perfect for running Espresso tests", R.drawable.ic_animation)
        )
    }
}
