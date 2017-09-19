package xyz.mustafaali.devqstiles.service

import android.os.BatteryManager
import android.provider.Settings
import timber.log.Timber

/**
 * Tile Service to keep the screen on when the device is connected to a USB port on a computer.
 *
 * If the device is connected to a wall charger, the screen will turn off.
 */
class ToggleKeepScreenOnService : BaseTileService() {
    override fun onClick() {
        val newValue = if (isFeatureEnabled()) 0 else BatteryManager.BATTERY_PLUGGED_USB

        try {
            Settings.Global.putInt(contentResolver, Settings.Global.STAY_ON_WHILE_PLUGGED_IN, newValue)
        } catch (se: SecurityException) {
            showPermissionError()
        }
        updateTile()
    }

    override fun isFeatureEnabled(): Boolean {
        try {
            return Settings.Global.getInt(contentResolver, Settings.Global.STAY_ON_WHILE_PLUGGED_IN) == BatteryManager.BATTERY_PLUGGED_USB
        } catch (e: Settings.SettingNotFoundException) {
            Timber.e(e, e.message)
        }
        return false
    }

}
