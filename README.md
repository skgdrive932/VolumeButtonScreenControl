# Volume Button Screen Control

Android app that uses an Accessibility Service to react to hardware volume buttons.

## Features

- Volume Up: wakes the screen.
- Volume Down: locks/turns off the screen.
- No root required.
- Android 8.0+.

## Setup

1. Open the project in Android Studio.
2. Build and install it on your Android phone.
3. Open **Volume Screen Control**.
4. Tap **Open Accessibility Settings**.
5. Enable the app's Accessibility Service.
6. Press Volume Up or Volume Down.

## Important

Android device manufacturers may handle hardware keys differently. On some phones, Volume Up may also change media/ringer volume depending on the device and OS version.

The app uses Android Accessibility Service because ordinary apps cannot directly control the lock screen state.

## License

MIT
