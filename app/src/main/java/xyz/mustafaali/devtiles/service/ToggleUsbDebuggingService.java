package xyz.mustafaali.devtiles.service;

import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;
import android.widget.Toast;

import xyz.mustafaali.devtiles.R;

/**
 * Tile Service to toggle USB Debugging.
 */
public class ToggleUsbDebuggingService extends TileService {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateTile();
    }

    @Override
    public void onClick() {
        String newValue = isUsbDebuggingEnabled() ? "0" : "1";

        try {
            Settings.Global.putString(getContentResolver(), Settings.Global.ADB_ENABLED, newValue);
        } catch (SecurityException se) {
            String message = getString(R.string.permission_required_toast);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            Log.e(TAG, message);
        }

        updateTile();
    }

    private boolean isUsbDebuggingEnabled() {
        return Settings.Global.getString(getContentResolver(), Settings.Global.ADB_ENABLED).equals("1");
    }

    private void updateTile() {
        final Tile tile = getQsTile();

        if (isUsbDebuggingEnabled()) {
            tile.setState(Tile.STATE_ACTIVE);
        } else {
            tile.setState(Tile.STATE_INACTIVE);
        }

        tile.updateTile();

    }
}
