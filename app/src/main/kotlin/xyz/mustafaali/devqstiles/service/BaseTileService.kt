package xyz.mustafaali.devqstiles.service

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast
import xyz.mustafaali.devqstiles.R

abstract class BaseTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        updateTile()
    }

    protected abstract fun isFeatureEnabled(): Boolean

    protected fun updateTile() {
        qsTile?.state = if (isFeatureEnabled()) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile?.updateTile()
    }

    protected fun showPermissionError() {
        val message = getString(R.string.permission_required_toast)
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}
