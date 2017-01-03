package xyz.mustafaali.devqstiles.service;

import android.os.BatteryManager;
import android.provider.Settings;
import android.util.Log;

/**
 * Tile Service to keep the screen on when the device is connected to a USB port on a computer.
 *
 * If the device is connected to a wall charger, the screen will turn off.
 */
public class ToggleKeepScreenOnService extends BaseTileService {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onClick() {
        int newValue = isFeatureEnabled() ? 0 : BatteryManager.BATTERY_PLUGGED_USB;

        try {
            Settings.Global.putInt(contentResolver, Settings.Global.STAY_ON_WHILE_PLUGGED_IN, newValue);
        } catch (SecurityException se) {
            showPermissionError();
        }
        updateTile();
    }

    @Override
    protected boolean isFeatureEnabled() {
        try {
            return Settings.Global.getInt(contentResolver, Settings.Global.STAY_ON_WHILE_PLUGGED_IN) == BatteryManager.BATTERY_PLUGGED_USB;
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

}
