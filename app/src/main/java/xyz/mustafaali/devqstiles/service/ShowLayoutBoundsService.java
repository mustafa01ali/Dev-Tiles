package xyz.mustafaali.devqstiles.service;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import xyz.mustafaali.devqstiles.R;

/**
 * Tile service to toggle the "Show Layout Bounds" dev feature. Though Google has added a tile for this in 7.1,
 * some OEMs (Samsung), decided for some reason to not include it. @#$%@$#^
 */
public class ShowLayoutBoundsService extends TileService {

    public static final String DEBUG_LAYOUT_PROPERTY = "debug.layout";
    private Object instance;
    private Method getBoolean;
    private Method setBoolean;

    @Override
    public void onStartListening() {
        super.onStartListening();
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            instance = clazz.newInstance();
            getBoolean = clazz.getDeclaredMethod("getBoolean", String.class, boolean.class);
            setBoolean = clazz.getDeclaredMethod("set", String.class, String.class);
            update();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            Toast.makeText(getApplicationContext(), R.string.msg_generic_error, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    protected boolean isFeatureEnabled() {
        boolean isEnabled = false;
        try {
            isEnabled = (Boolean) getBoolean.invoke(instance, DEBUG_LAYOUT_PROPERTY, false);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Toast.makeText(getApplicationContext(), R.string.msg_generic_error, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return isEnabled;
    }

    @Override
    public void onClick() {
        System.out.println("Feature enabled? " + isFeatureEnabled());
        try {
            setBoolean.invoke(instance, DEBUG_LAYOUT_PROPERTY, isFeatureEnabled() ? "false" : "true");
        } catch (IllegalAccessException | InvocationTargetException e) {
            Toast.makeText(getApplicationContext(), R.string.msg_generic_error, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        update();
    }

    private void update() {
        Tile tile = getQsTile();
        tile.setState(isFeatureEnabled() ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.updateTile();
    }
}
