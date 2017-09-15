package xyz.mustafaali.devqstiles;

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.activity_main.*


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
            R.id.menu_share_app -> {
                shareApp()
                true
            }
            R.id.menu_rate_app -> {
                openStoreListing()
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
        var intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("market://details?id=xyz.mustafaali.devqstiles")
        try {
            startActivity(intent)
        } catch(e: ActivityNotFoundException) {
            Log.e("asdf", "Couldn't launch activity, maybe PlayStore is not installed")
        }
    }

    fun initUi() {
        copyButton.setOnClickListener({ sharePermissionsCommand() })
        featuresDescriptionTextView.text = Html.fromHtml(getString(R.string.features_description), Html.FROM_HTML_MODE_COMPACT)
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
}
