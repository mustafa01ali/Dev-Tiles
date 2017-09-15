package xyz.mustafaali.devqstiles;

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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

    private fun openEmailClient() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf("mustafaali.apps@gmail.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "[DevTiles] Feature Request")
        startActivity(intent)
    }

    private fun initUi() {
        copyButton.setOnClickListener({ sharePermissionsCommand() })
        featuresDescriptionTextView.text = Html.fromHtml(getString(R.string.features_description), Html.FROM_HTML_MODE_COMPACT)

        featuresRecyclerView.layoutManager = LinearLayoutManager(this)
        featuresRecyclerView.setHasFixedSize(true)
        featuresRecyclerView.adapter = FeaturesAdapter(listOf(Feature("One", "Desc", 1))) {
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
        }

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
