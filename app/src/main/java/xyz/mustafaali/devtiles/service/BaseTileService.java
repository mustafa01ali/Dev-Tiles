package xyz.mustafaali.devtiles.service;

import android.service.quicksettings.TileService;

public abstract class BaseTileService extends TileService {

    protected abstract void updateTile();

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateTile();
    }
}
