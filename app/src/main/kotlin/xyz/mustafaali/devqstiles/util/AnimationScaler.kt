package xyz.mustafaali.devqstiles.util

import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import android.support.annotation.DrawableRes
import android.widget.Toast
import timber.log.Timber
import xyz.mustafaali.devqstiles.R

/**
 * A utility class for working with system Window Animation Scale, Transition Animation Scale, and Animator Duration Scale.
 */
object AnimationScaler {

    @DrawableRes
    fun getIcon(scale: Float): Int {
        when {
            scale <= 0f -> return R.drawable.ic_animation_off
            else -> return R.drawable.ic_animation_on
        }
    }

    fun toggleAnimationScale(context: Context): Boolean {
        val animationScale = if (getAnimationScale(context.contentResolver) == 1.0f) 0.0f else 1.0f
        return try {
            Settings.Global.putFloat(
                    context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, animationScale)
            Settings.Global.putFloat(
                    context.contentResolver, Settings.Global.WINDOW_ANIMATION_SCALE, animationScale)
            Settings.Global.putFloat(
                    context.contentResolver, Settings.Global.TRANSITION_ANIMATION_SCALE, animationScale)
            true
        } catch (se: SecurityException) {
            val message = context.getString(R.string.permission_required_toast)
            Toast.makeText(context.applicationContext, message, Toast.LENGTH_LONG).show()
            Timber.e(se, message)
            false
        }
    }

    fun getAnimationScale(contentResolver: ContentResolver?): Float {
        var scale = 1f
        try {
            scale = maxOf(
                    Settings.Global.getFloat(contentResolver,
                            Settings.Global.ANIMATOR_DURATION_SCALE),
                    Settings.Global.getFloat(contentResolver,
                            Settings.Global.WINDOW_ANIMATION_SCALE),
                    Settings.Global.getFloat(contentResolver,
                            Settings.Global.TRANSITION_ANIMATION_SCALE))
        } catch (e: Settings.SettingNotFoundException) {
            Timber.e(e, "Could not read Animation Scale setting")
        }

        return if (scale >= 1) 1.0f else 0.0f
    }
}
