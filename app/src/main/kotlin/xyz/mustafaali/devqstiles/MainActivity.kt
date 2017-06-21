package xyz.mustafaali.devqstiles;

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Menu
import android.view.MenuItem
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
        when (item.itemId) {
            R.id.menu_share_app -> {
                shareApp()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
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
