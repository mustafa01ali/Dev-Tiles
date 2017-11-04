package xyz.mustafaali.devqstiles.service

import android.provider.Settings
import timber.log.Timber

/**
 * Tile Service to toggle USB Debugging.
 */
class ToggleUsbDebuggingService : BaseTileService() {

    override fun onStartListening() {
        super.onStartListening()
        updateTile()
    }

    override fun onClick() {
        val newValue = if (isFeatureEnabled()) "0" else "1"

        try {
            Settings.Global.putString(contentResolver, Settings.Global.ADB_ENABLED, newValue)
        } catch (se: SecurityException) {
            Timber.e(se, se.message)
            showPermissionError()
        }
        updateTile()
    }

    override fun isFeatureEnabled(): Boolean {
        return Settings.Global.getString(contentResolver, Settings.Global.ADB_ENABLED) == "1"
    }
}
