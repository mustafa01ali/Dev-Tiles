package xyz.mustafaali.devqstiles.util

import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import android.support.annotation.DrawableRes
import android.support.annotation.FloatRange
import android.widget.Toast
import timber.log.Timber
import xyz.mustafaali.devqstiles.R

/**
 * A helper class for working with the system animator duration scale.
 */
object AnimatorDurationScaler {

    @DrawableRes
    fun getIcon(scale: Float): Int {
        if (scale <= 0f) {
            return R.drawable.ic_animator_duration_off
        } else if (scale <= 0.5f) {
            return R.drawable.ic_animator_duration_half_x
        } else if (scale <= 1f) {
            return R.drawable.ic_animator_duration_1x
        } else if (scale <= 1.5f) {
            return R.drawable.ic_animator_duration_1_5x
        } else if (scale <= 2f) {
            return R.drawable.ic_animator_duration_2x
        } else if (scale <= 5f) {
            return R.drawable.ic_animator_duration_5x
        } else if (scale <= 10f) {
            return R.drawable.ic_animator_duration_10x
        }
        return R.drawable.ic_animator_duration
    }

    fun getAnimatorScale(contentResolver: ContentResolver): Float {
        var scale = 1f
        try {
            scale = Settings.Global.getFloat(contentResolver,
                    Settings.Global.ANIMATOR_DURATION_SCALE)
        } catch (e: Settings.SettingNotFoundException) {
            Timber.e(e, "Could not read Animator Duration Scale setting")
        }

        return scale
    }

    fun setAnimatorScale(
            context: Context,
            @FloatRange(from = 0.0, to = 10.0) scale: Float): Boolean {
        return try {
            Settings.Global.putFloat(
                    context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, scale)
            true
        } catch (se: SecurityException) {
            val message = context.getString(R.string.permission_required_toast)
            Toast.makeText(context.applicationContext, message, Toast.LENGTH_LONG).show()
            Timber.e(se, message)
            false
        }

    }
}
