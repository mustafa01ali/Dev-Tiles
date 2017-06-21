#!/usr/bin/env bash
echo "Granting permissions..."
adb shell pm grant xyz.mustafaali.devqstiles android.permission.WRITE_SECURE_SETTINGS
adb shell pm grant xyz.mustafaali.devqstiles android.permission.DUMP
echo "Done!"