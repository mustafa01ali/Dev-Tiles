package xyz.mustafaali.devqstiles.service

import android.graphics.drawable.Icon
import android.service.quicksettings.TileService
import xyz.mustafaali.devqstiles.util.AnimationScaler

/**
 * A {@link TileService} for toggling Window Animation Scale, Transition Animation Scale, and Animator Duration Scale.
 */
class ToggleAnimationService : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        updateTile()
    }

    override fun onClick() {
        AnimationScaler.toggleAnimationScale(this)
        updateTile()
    }

    private fun updateTile() {
        val scale = AnimationScaler.getAnimationScale(contentResolver)
        val tile = qsTile
        tile.icon = Icon.createWithResource(applicationContext, AnimationScaler.getIcon(scale))
        tile.updateTile()
    }

}