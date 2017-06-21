package xyz.mustafaali.devqstiles.service

import android.provider.Settings
import android.util.Log
import android.widget.Toast

import xyz.mustafaali.devqstiles.R

class ToggleShowTapsService : BaseTileService() {
    val TAG = this.javaClass.simpleName
    val SHOW_TOUCHES = "show_touches";

    override fun onClick() {
        val newValue = if (isFeatureEnabled) 0 else 1

        try {
            Settings.System.putInt(contentResolver, SHOW_TOUCHES, newValue)
        } catch (se: SecurityException) {
            val message = getString(R.string.permission_required_toast)
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            Log.e(TAG, message)
        }

        updateTile();
    }

    override fun isFeatureEnabled(): Boolean {
        try {
            return Settings.System.getInt(contentResolver, SHOW_TOUCHES) == 1
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            return false
        }
    }
}
