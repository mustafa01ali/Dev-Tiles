package xyz.mustafaali.devqstiles;

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView

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
        if (item.itemId == R.id.menu_share_app) {
            shareApp()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    fun initUi() {
        val copyButton = findViewById(R.id.btn_copy) as Button
        copyButton.setOnClickListener({ sharePermissionsCommand() })

        val featureDescription = findViewById(R.id.tv_features_description) as TextView
        featureDescription.text = Html.fromHtml(getString(R.string.features_description), Html.FROM_HTML_MODE_COMPACT)
    }

    private fun shareApp() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND;
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.msg_share_app))
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    private fun sharePermissionsCommand() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.permission_command))
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }
}
