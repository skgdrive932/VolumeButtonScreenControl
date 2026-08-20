# VolumeButtonScreenControl

Control the screen using the hardware volume buttons through Android Accessibility Service.

## Behavior
- **Volume Up:** wake the screen
- **Volume Down:** lock/turn the screen off

## Setup
1. Install the APK.
2. Open the app.
3. Tap **Open Accessibility Settings**.
4. Enable **Volume Screen Control** under Installed apps/Downloaded apps.
5. Return to the app. A toast saying the service is active should appear.
6. Test Volume Up and Volume Down.

## Important
The Accessibility Service must remain enabled. Some Android manufacturers may restrict background services; if the service stops, disable and re-enable it in Accessibility settings.

## Troubleshooting
If volume changes instead of controlling the screen, the Accessibility Service is not receiving hardware key events. Re-enable the service and restart the phone.
