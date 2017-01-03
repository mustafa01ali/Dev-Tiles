package xyz.mustafaali.devqstiles.service;

import android.content.ContentResolver;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;
import android.widget.Toast;

import xyz.mustafaali.devqstiles.R;

public abstract class BaseTileService extends TileService {

    protected ContentResolver contentResolver;

    @Override
    public void onStartListening() {
        super.onStartListening();
        contentResolver = getContentResolver();
        updateTile();
    }

    protected abstract boolean isFeatureEnabled();

    protected void updateTile() {
        final Tile tile = getQsTile();

        if (isFeatureEnabled()) {
            tile.setState(Tile.STATE_ACTIVE);
        } else {
            tile.setState(Tile.STATE_INACTIVE);
        }

        tile.updateTile();
    }

    protected void showPermissionError() {
        String message = getString(R.string.permission_required_toast);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        Log.e("Dev Tile Service", message);
    }
}
