package xyz.mustafaali.devqstiles.service

import android.provider.Settings
import timber.log.Timber

class ToggleShowTapsService : BaseTileService() {
    private val SHOW_TOUCHES = "show_touches"

    override fun onClick() {
        val newValue = if (isFeatureEnabled()) 0 else 1

        try {
            Settings.System.putInt(contentResolver, SHOW_TOUCHES, newValue)
        } catch (e: Exception) {
            showPermissionError()
            Timber.e(e, e.message)
        }

        updateTile()
    }

    override fun isFeatureEnabled(): Boolean {
        return try {
            Settings.System.getInt(contentResolver, SHOW_TOUCHES) == 1
        } catch (e: Settings.SettingNotFoundException) {
            Timber.e(e, e.message)
            false
        }
    }
}
