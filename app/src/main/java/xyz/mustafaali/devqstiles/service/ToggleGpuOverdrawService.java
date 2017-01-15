package xyz.mustafaali.devqstiles.service;

import android.content.DialogInterface;
import android.provider.Settings;
import android.service.quicksettings.TileService;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.widget.Toast;

import xyz.mustafaali.devqstiles.R;

public class ToggleGpuOverdrawService extends TileService {
    public static final String DEBUG_OVERDRAW_PROPERTY = "debug.hwui.overdraw";

    private final CharSequence[] choices = {"Off", "Show overdraw areas", "Show areas for Deuteranomaly"};
    private final String[] values = {"false", "show", "show_deuteranomaly"};

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateTile();
    }

    @Override
    public void onClick() {
        showDialog(getDialog(0));
    }

    private String getCurrentValue() {
        String value = Settings.System.getString(getContentResolver(), DEBUG_OVERDRAW_PROPERTY);
//        String value = SystemProperties.get(ThreadedRenderer.DEBUG_OVERDRAW_PROPERTY);
        if (value == null) {
            value = "";
        }
        Log.d(">>>", "Value - " + value);
        return value;
    }

    private AlertDialog getDialog(int selectedIndex) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppTheme_Dialog));
        builder.setTitle(R.string.dialog_animator_duration_title)
                .setSingleChoiceItems(choices, selectedIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateSetting(values[which]);
                        updateTile();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);
        return builder.create();
    }

    private void updateSetting(String newValue) {
        Log.d(">>>", "New value - " + newValue);
        try {
            Settings.System.putString(getContentResolver(), DEBUG_OVERDRAW_PROPERTY, newValue);
        } catch (SecurityException se) {
            String message = getString(R.string.permission_required_toast);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//            Log.e(TAG, message);
        }
    }

    private void updateTile() {
    }

    /*public static class SystemPropPoker extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            String[] services = ServiceManager.listServices();
            for (String service : services) {
                IBinder obj = ServiceManager.checkService(service);
                if (obj != null) {
                    Parcel data = Parcel.obtain();
                    try {
                        obj.transact(IBinder.SYSPROPS_TRANSACTION, data, null, 0);
                    } catch (RemoteException e) {
                    } catch (Exception e) {
                        Log.i("", "Someone wrote a bad service '" + service
                                + "' that doesn't like to be poked: " + e);
                    }
                    data.recycle();
                }
            }
            return null;
        }
    }*/

}
