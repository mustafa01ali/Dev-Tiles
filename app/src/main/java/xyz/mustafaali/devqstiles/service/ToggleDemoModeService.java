package xyz.mustafaali.devqstiles.service;

import android.content.Intent;
import android.provider.Settings;

/**
 * Tile service to toggle demo mode
 */
public class ToggleDemoModeService extends BaseTileService {

    private final String DEMO_MODE_ALLOWED = "sysui_demo_allowed";
    private final String DEMO_MODE_ON = "sysui_tuner_demo_on";

    private final String[] STATUS_ICONS = {
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

    private interface DemoMode {
        String ACTION_DEMO = "com.android.systemui.demo";
        String EXTRA_COMMAND = "command";
        String COMMAND_ENTER = "enter";
        String COMMAND_EXIT = "exit";
        String COMMAND_CLOCK = "clock";
        String COMMAND_BATTERY = "battery";
        String COMMAND_NETWORK = "network";
        String COMMAND_BARS = "bars";
        String COMMAND_STATUS = "status";
        String COMMAND_NOTIFICATIONS = "notifications";
        String COMMAND_VOLUME = "volume";
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        try {
            if (Settings.Global.getInt(contentResolver, DEMO_MODE_ALLOWED) != 0) {
                setGlobal(DEMO_MODE_ALLOWED, 1);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick() {
        if (isFeatureEnabled()) {
            stopDemoMode();
        } else {
            startDemoMode();
        }
        updateTile();
    }

    @Override
    protected boolean isFeatureEnabled() {
        return Settings.Global.getInt(contentResolver, DEMO_MODE_ON, 0) != 0;
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
        try {
            Settings.Global.putInt(contentResolver, key, value);
        } catch (SecurityException se) {
            showPermissionError();
        }
    }
}