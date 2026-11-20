# Build Hints Reference

Build hints are key/value pairs in `common/codenameone_settings.properties` that are forwarded to the Codename One build server. Every key starts with `codename1.arg.` (the build server strips that prefix). They control native platform behaviour that cannot be expressed in Java/CSS: permissions, frameworks, splash screens, signing, platform SDK versions, etc.

This file is a curated index of the most commonly needed hints. The complete authoritative reference is in the Codename One Developer Guide:

- <https://www.codenameone.com/developer-guide/> — full guide
- <https://www.codenameone.com/blog/build-hints-editor.html> — editing build hints from the simulator's *Build Hints* menu
- <https://www.codenameone.com/blog/build-hint-variables.html> — variable substitution syntax for hints

When in doubt, search the developer guide for the exact key name — there are hundreds of hints and only the ones you actually need are listed here.

## Universal

| Hint | Effect |
| --- | --- |
| `codename1.arg.java.version=17` | **Required.** Picks the JDK 17 build server toolchain. |
| `codename1.arg.build.compile=true` | Run the ahead-of-time / bytecode-to-native compile (recommended for iOS, smaller binaries). |
| `codename1.arg.build.timeout=180` | Build server timeout in minutes. Bump for very large apps. |
| `codename1.arg.var.<name>=...` | Define a custom variable referenced as `${var.name}` elsewhere in hints. |

## iOS

| Hint | Effect |
| --- | --- |
| `codename1.arg.ios.deployment_target=14.0` | Minimum iOS version. Set to the lowest iOS you actually support. |
| `codename1.arg.ios.teamId=ABCDEF1234` | Apple Developer Team ID; used by `ios-source` Xcode projects for code signing. |
| `codename1.arg.ios.includePush=true` | Include APNs entitlements + frameworks for push. |
| `codename1.arg.ios.add_libs=libsqlite3.0.dylib;libxml2.dylib` | Link extra system libraries. |
| `codename1.arg.ios.pods=Firebase/Core,Firebase/Analytics` | CocoaPods to include. |
| `codename1.arg.ios.pods.platform=14.0` | Pod platform target (must be >= deployment_target). |
| `codename1.arg.ios.pods.sources=https://github.com/CocoaPods/Specs.git` | Custom Pod source repos. |
| `codename1.arg.ios.objC=true` | Allow the iOS port to use Objective-C runtime features the strict mode would block. |
| `codename1.arg.ios.NSCameraUsageDescription=...` | Camera privacy description in `Info.plist`. See *iOS privacy strings* below for the pattern. |
| `codename1.arg.ios.NSLocationWhenInUseUsageDescription=...` | Location (in-use) privacy description. |
| `codename1.arg.ios.NSPhotoLibraryUsageDescription=...` | Photo library privacy description. |
| `codename1.arg.ios.NSMicrophoneUsageDescription=...` | Microphone privacy description. |
| `codename1.arg.ios.plistInject=...raw XML...` | Inject raw `<key>…</key><value>…</value>` snippets into `Info.plist` for keys that don't have a dedicated `ios.NS*` hint above. |
| `codename1.arg.ios.glAppDelegateHeader=#import "MyHeader.h"` | Prepend custom imports to the generated AppDelegate. |
| `codename1.arg.ios.statusbar_hidden=true` | Hide the iOS status bar. |
| `codename1.arg.ios.beforeFinishLaunching=...` | Native code inserted before iOS's `application:didFinishLaunchingWithOptions:` returns. |
| `codename1.arg.ios.newStorageLocation=true` | Use modern iOS storage paths (recommended for new apps). |

## Android

| Hint | Effect |
| --- | --- |
| `codename1.arg.android.googlePlayVersion=true` | Build the Google Play–compatible APK/AAB variant. |
| `codename1.arg.android.sdkVersion=34` | Compile-time Android SDK. |
| `codename1.arg.android.targetSDKVersion=34` | Target SDK in the manifest (drives Play Store acceptance). |
| `codename1.arg.android.minSdkVersion=24` | Minimum Android API level. |
| `codename1.arg.android.buildToolsVersion=34.0.0` | Android build-tools version. |
| `codename1.arg.android.xPermissions=<uses-permission android:name="..."/>` | Inject extra `<uses-permission>` lines into the manifest. |
| `codename1.arg.android.xapplication=<receiver .../>` | Inject XML inside the manifest's `<application>` element. |
| `codename1.arg.android.activity.launchMode=singleTask` | Launch mode for the main activity. |
| `codename1.arg.android.statusbar_hidden=true` | Hide the Android status bar. |
| `codename1.arg.android.debug=false` | Whether to build a debug APK in addition to release. |
| `codename1.arg.android.licenseKey=...` | Google Play licensing key. |
| `codename1.arg.android.release=true` | Treat the build as a release (R8/ProGuard on, etc.). |
| `codename1.arg.android.proguardKeep=...` | Extra ProGuard `-keep` rules. |
| `codename1.arg.android.gradleDep=implementation 'com.example:lib:1.0'` | Inject Gradle dependencies. |

## Push notifications

| Hint | Effect |
| --- | --- |
| `gcm.sender_id=1234567890` | Firebase/GCM sender ID for Android push. |
| `codename1.arg.ios.includePush=true` | Pair with the FCM/APNs setup on the iOS side. |

## iOS privacy strings (`Info.plist`)

iOS requires per-feature usage descriptions that the user sees in the system permission prompt. CN1 surfaces these as dedicated `ios.NS<Key>` build hints — set them directly rather than using `ios.plistInject` for these well-known keys:

```properties
codename1.arg.ios.NSCameraUsageDescription=Scan QR codes to pair the device.
codename1.arg.ios.NSLocationWhenInUseUsageDescription=Find nearby branches.
codename1.arg.ios.NSLocationAlwaysAndWhenInUseUsageDescription=Track delivery while the app is backgrounded.
codename1.arg.ios.NSPhotoLibraryUsageDescription=Attach photos to support tickets.
codename1.arg.ios.NSPhotoLibraryAddUsageDescription=Save edited photos back to your library.
codename1.arg.ios.NSMicrophoneUsageDescription=Record voice notes.
codename1.arg.ios.NSContactsUsageDescription=Invite friends from your address book.
codename1.arg.ios.NSSpeechRecognitionUsageDescription=Transcribe voice notes locally.
codename1.arg.ios.NSSiriUsageDescription=Trigger app actions from Siri shortcuts.
```

Any `ios.NS<Key>UsageDescription` key is forwarded into the generated `Info.plist`. App Store builds reject location, camera, microphone, photos, contacts, etc. without the matching description. See [Apple's CocoaKeys reference](https://developer.apple.com/library/content/documentation/General/Reference/InfoPlistKeyReference/Articles/CocoaKeys.html) for the complete key catalog.

`ios.plistInject` remains the escape hatch for raw XML snippets that don't have a dedicated `ios.NS*` hint.

## JavaScript / web

| Hint | Effect |
| --- | --- |
| `codename1.arg.javascript.html5=true` | Emit modern ES output. |
| `codename1.arg.javascript.bundleResources=true` | Inline `theme.res` into the bundle (faster cold start). |

## Variable substitution

Many hint values support `${var.name}` substitution against `codename1.arg.var.*` keys. Useful for keeping a single source of truth for things like the iOS deployment target:

```properties
codename1.arg.var.iosDeploy=14.0
codename1.arg.ios.deployment_target=${var.iosDeploy}
codename1.arg.ios.pods.platform=${var.iosDeploy}
```

## How to discover the right hint

1. Search the [Developer Guide](https://www.codenameone.com/developer-guide/) for the platform feature you need.
2. Or run the simulator (`mvn -pl common cn1:run`) and use the **Build Hints** menu — it lists every hint the plugin understands, with descriptions.
3. Or grep the project's existing `codename1.arg.*` keys in `common/codenameone_settings.properties` to see what's already wired.
