package xyz.mustafaali.devqstiles.service;

import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import xyz.mustafaali.devqstiles.R;

/**
 * Tile Service to toggle USB Debugging.
 */
public class ToggleUsbDebuggingService extends BaseTileService {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateTile();
    }

    @Override
    public void onClick() {
        String newValue = isFeatureEnabled() ? "0" : "1";

        try {
            Settings.Global.putString(contentResolver, Settings.Global.ADB_ENABLED, newValue);
        } catch (SecurityException se) {
            String message = getString(R.string.permission_required_toast);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            Log.e(TAG, message);
        }
        updateTile();
    }

    @Override
    protected boolean isFeatureEnabled() {
        return Settings.Global.getString(contentResolver, Settings.Global.ADB_ENABLED).equals("1");
    }

}
