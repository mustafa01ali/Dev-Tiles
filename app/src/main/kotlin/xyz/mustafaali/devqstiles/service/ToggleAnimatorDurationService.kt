package xyz.mustafaali.devqstiles.service

import android.graphics.drawable.Icon
import android.service.quicksettings.TileService
import android.support.v7.app.AlertDialog
import android.support.v7.view.ContextThemeWrapper
import xyz.mustafaali.devqstiles.R
import xyz.mustafaali.devqstiles.util.AnimatorDurationScaler
import xyz.mustafaali.devqstiles.util.AnimatorDurationScaler.getAnimatorScale
import xyz.mustafaali.devqstiles.util.AnimatorDurationScaler.getIcon

/**
 * A {@link TileService quick settings tile} for scaling animation durations. Toggles between 0.5x and
 * 10x animator duration scales.
 */
class ToggleAnimatorDurationService : TileService() {

    val choices = arrayOf(
            "Animation off",
            "Animation scale .5x",
            "Animation scale 1x",
            "Animation scale 1.5x",
            "Animation scale 2x",
            "Animation scale 5x",
            "Animation scale 10x"
    )

    val scales = listOf(
            0f,
            0.5f,
            1f,
            1.5f,
            2f,
            5f,
            10f)

    override fun onStartListening() {
        super.onStartListening()
        updateTile()
    }

    override fun onClick() {
        val current = getAnimatorScale(contentResolver)
        showDialog(getDialog(scales.indexOf(current)))
    }

    private fun getDialog(selectedIndex: Int): AlertDialog {
        val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AppTheme_Dialog))
        builder.setTitle(R.string.dialog_animator_duration_title)
                .setSingleChoiceItems(choices, selectedIndex, { dialog, which ->
                    AnimatorDurationScaler.setAnimatorScale(this@ToggleAnimatorDurationService, scales[which])
                    updateTile()
                    dialog.dismiss()
                })
                .setNegativeButton(android.R.string.cancel, null)
        return builder.create()
    }

    private fun updateTile() {
        val scale = getAnimatorScale(contentResolver)
        val tile = qsTile
        tile.icon = Icon.createWithResource(applicationContext, getIcon(scale))
        tile.updateTile()
    }
}


