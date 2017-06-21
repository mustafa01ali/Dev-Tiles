package xyz.mustafaali.devqstiles.service

import android.provider.Settings
import android.util.Log

/**
 * Tile Service to toggle USB Debugging.
 */
class ToggleUsbDebuggingService : BaseTileService() {

    val TAG = javaClass.simpleName

    override fun onStartListening() {
        super.onStartListening()
        updateTile()
    }

    override fun onClick() {
        val newValue = if (isFeatureEnabled()) "0" else "1"

        try {
            Settings.Global.putString(contentResolver, Settings.Global.ADB_ENABLED, newValue)
        } catch (se: SecurityException) {
            Log.e(TAG, se.message)
            showPermissionError()
        }
        updateTile()
    }

    override fun isFeatureEnabled(): Boolean {
        return Settings.Global.getString(contentResolver, Settings.Global.ADB_ENABLED) == "1"
    }

}
