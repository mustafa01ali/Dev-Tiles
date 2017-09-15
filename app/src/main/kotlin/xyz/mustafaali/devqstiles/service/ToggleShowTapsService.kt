package xyz.mustafaali.devqstiles.service

import android.provider.Settings
import android.util.Log

class ToggleShowTapsService : BaseTileService() {
    private val TAG = this.javaClass.simpleName
    private val SHOW_TOUCHES = "show_touches"

    override fun onClick() {
        val newValue = if (isFeatureEnabled()) 0 else 1

        try {
            Settings.System.putInt(contentResolver, SHOW_TOUCHES, newValue)
        } catch (e: Exception) {
            showPermissionError()
            Log.e(TAG, e.message)
        }

        updateTile()
    }

    override fun isFeatureEnabled(): Boolean {
        return try {
            Settings.System.getInt(contentResolver, SHOW_TOUCHES) == 1
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            false
        }
    }
}
