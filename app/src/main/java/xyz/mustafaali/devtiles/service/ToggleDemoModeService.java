package xyz.mustafaali.devtiles.service;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

/**
 * Tile service to toggle demo mode
 */
public class ToggleDemoModeService extends BaseTileService {

    //FIXME
    private static final String DEVICE_DEMO_MODE = "sysui_tuner_demo_on";
    private static final String DEMO_MODE_ON = "sysui_tuner_demo_on";

    private static final String[] STATUS_ICONS = {
            "volume",
            "bluetooth",
            "location",
            "alarm",
            "zen",
            "sync",
            "tty",
            "eri",
            "mute",
            "speakerphone",
            "managed_profile",
    };

    public interface DemoMode {
        public static final String DEMO_MODE_ALLOWED = "sysui_demo_allowed";

        void dispatchDemoCommand(String command, Bundle args);

        public static final String ACTION_DEMO = "com.android.systemui.demo";

        public static final String EXTRA_COMMAND = "command";
        public static final String COMMAND_ENTER = "enter";
        public static final String COMMAND_EXIT = "exit";
        public static final String COMMAND_CLOCK = "clock";
        public static final String COMMAND_BATTERY = "battery";
        public static final String COMMAND_NETWORK = "network";
        public static final String COMMAND_BARS = "bars";
        public static final String COMMAND_STATUS = "status";
        public static final String COMMAND_NOTIFICATIONS = "notifications";
        public static final String COMMAND_VOLUME = "volume";

    }

    @Override
    public void onClick() {
        int newValue;
        if (isFeatureEnabled()) {
            stopDemoMode();
            newValue = 0;
        } else {
            startDemoMode();
            newValue = 1;
        }

//        int newValue = isFeatureEnabled() ? 0 : 1;
//        try {
//            Settings.Global.putInt(contentResolver, DEVICE_DEMO_MODE, newValue);
//        } catch (SecurityException se) {
//            showPermissionError();
//        }
        updateTile();
    }

    @Override
    protected boolean isFeatureEnabled() {
        return Settings.Global.getInt(contentResolver, DEVICE_DEMO_MODE, 0) != 0;
    }

    private void startDemoMode() {
        Intent intent = new Intent(DemoMode.ACTION_DEMO);

        intent.putExtra(DemoMode.EXTRA_COMMAND, DemoMode.COMMAND_ENTER);
        sendBroadcast(intent);

        intent.putExtra(DemoMode.EXTRA_COMMAND, DemoMode.COMMAND_CLOCK);
        intent.putExtra("hhmm", "0500");
        sendBroadcast(intent);

        intent.putExtra(DemoMode.EXTRA_COMMAND, DemoMode.COMMAND_NETWORK);
        intent.putExtra("wifi", "show");
        intent.putExtra("mobile", "show");
        intent.putExtra("sims", "1");
        intent.putExtra("nosim", "false");
        intent.putExtra("level", "4");
        intent.putExtra("datatypel", "");
        sendBroadcast(intent);

        // Need to send this after so that the sim controller already exists.
        intent.putExtra("fully", "true");
        sendBroadcast(intent);

        intent.putExtra(DemoMode.EXTRA_COMMAND, DemoMode.COMMAND_BATTERY);
        intent.putExtra("level", "100");
        intent.putExtra("plugged", "false");
        sendBroadcast(intent);

        intent.putExtra(DemoMode.EXTRA_COMMAND, DemoMode.COMMAND_STATUS);
        for (String icon : STATUS_ICONS) {
            intent.putExtra(icon, "hide");
        }
        sendBroadcast(intent);

        intent.putExtra(DemoMode.EXTRA_COMMAND, DemoMode.COMMAND_NOTIFICATIONS);
        intent.putExtra("visible", "false");
        sendBroadcast(intent);

        setGlobal(DEMO_MODE_ON, 1);
    }

    private void stopDemoMode() {
        Intent intent = new Intent(DemoMode.ACTION_DEMO);
        intent.putExtra(DemoMode.EXTRA_COMMAND, DemoMode.COMMAND_EXIT);
        sendBroadcast(intent);
        setGlobal(DEMO_MODE_ON, 0);
    }

    private void setGlobal(String key, int value) {
        Settings.Global.putInt(contentResolver, key, value);
    }
}
