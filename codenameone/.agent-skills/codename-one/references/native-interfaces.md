# Native Interfaces

A **Native Interface** in Codename One is a Java interface in `common/` that has a per-platform implementation outside of `common/` — Objective-C (or Swift) for iOS, Java (or Kotlin) for Android, JavaScript for the JS port, plain Java for the desktop simulator. At runtime CN1 wires the Java interface to the right platform implementation. This is how you call out to platform APIs CN1 doesn't expose directly (NFC, native ads, Bluetooth, vendor SDKs).

> **The GPS example below is for illustration only.** Codename One already ships a portable **`com.codename1.location`** API (`LocationManager.getLocationManager().getCurrentLocation()`, `setLocationListener(...)`, geofence + background tracking) — you should never roll your own GPS bridge. Maps are likewise already covered by the **`cn1-google-maps`** cn1lib (Google Maps for iOS and Android) and the built-in `com.codename1.maps.MapComponent` for OpenStreetMap. Use native interfaces for things the framework actually doesn't expose; reach for them last, not first.

## The workflow

1. **Write the Java interface in `common/`** as an extension of `com.codename1.system.NativeInterface`.
2. **Generate stubs** with `mvn cn1:generate-native-interfaces`. The plugin scans `common/target/classes/` for classes that extend `NativeInterface` and emits per-platform stub files into the matching module's source tree.
3. **Run the target platform first, against the empty stubs**, so you can see exactly where the stub lands and what the file looks like before you implement it.
4. **Implement the stubs** — fill in the native code for each platform you care about.
5. **Look up the bridge from Java** with `NativeLookup.create(YourInterface.class)`.

Each step is described below.

## 1. The Java interface

```java
package com.example.myapp.native_;

import com.codename1.system.NativeInterface;

public interface GpsBridge extends NativeInterface {
    boolean hasGps();
    void requestPermission();
    double getLatitude();
    double getLongitude();
}
```

Constraints on the interface:

- Must extend `com.codename1.system.NativeInterface`.
- All method parameters and return values must be **primitives**, `String`, `byte[]`, or `PeerComponent` (for native UI views). No arbitrary Java objects, no collections, no `Object`. This is because the bridge must marshal across language boundaries.
- Callbacks from native code back to Java go through a *separate* mechanism — see *Callbacks* below.

`NativeInterface` itself exposes a built-in `isSupported()` method (every native interface inherits it). Implementations should return `true` if the platform can serve the calls, `false` otherwise — callers branch on `bridge.isSupported()` before invoking real methods.

## 2. Generate the stubs

```bash
mvn -pl common compile                       # the generator needs the interface compiled
mvn -pl common cn1:generate-native-interfaces
```

This creates files like:

```
ios/src/main/objectivec/com_example_myapp_native_GpsBridgeImpl.h
ios/src/main/objectivec/com_example_myapp_native_GpsBridgeImpl.m
android/src/main/java/com/example/myapp/native_/GpsBridgeImpl.java
javase/src/main/java/com/example/myapp/native_/GpsBridgeImpl.java
javascript/src/main/javascript/com_example_myapp_native_GpsBridgeImpl.js
```

The plugin will **not overwrite existing stubs** by default. Pass `-Dcn1.generateNativeInterfaces.overwrite=true` if you really want to regenerate. To emit Swift instead of Objective-C, or Kotlin instead of Java, add `-Dcn1.generateNativeInterfaces.swift=true` / `-Dcn1.generateNativeInterfaces.kotlin=true`.

## 3. Verify the stub layout BEFORE implementing

Before you write any platform code, run the target you care about so you can see the stubs in the generated build:

```bash
# iOS — produces an Xcode project under ios/target/codenameone/ios/dist/. Open it and
# confirm the stub files appear and that the project builds (no missing-symbol errors).
mvn -pl ios package -Dcodename1.platform=ios -Dcodename1.buildTarget=ios-source

# Android — cloud builds emit logs that show the stubs being compiled into the APK.
mvn -pl android package -Dcodename1.platform=android -Dcodename1.buildTarget=android-device -Dautomated=true

# JavaScript — produces a web bundle; open dev tools and confirm the JS impl is included.
mvn -pl javascript package -Dcodename1.platform=javascript -Dcodename1.buildTarget=javascript

# Desktop simulator — just run cn1:run and observe the bridge boots without errors.
mvn -pl common cn1:run
```

This step matters because every platform has a different stub layout, naming convention, and dependency story. Running the empty stubs first is how you learn what the real implementation file will look like — file path, class/file name, method signatures — without spending time on logic that may need to be reworked.

## 4. Implement the stubs

### iOS (Objective-C)

`ios/src/main/objectivec/com_example_myapp_native_GpsBridgeImpl.m`:

```objc
#import "com_example_myapp_native_GpsBridgeImpl.h"
#import <CoreLocation/CoreLocation.h>

@implementation com_example_myapp_native_GpsBridgeImpl

-(BOOL)hasGps {
    return [CLLocationManager locationServicesEnabled] ? YES : NO;
}

-(void)requestPermission {
    static CLLocationManager *mgr;
    if (!mgr) mgr = [[CLLocationManager alloc] init];
    [mgr requestWhenInUseAuthorization];
}

-(double)getLatitude { return /* ... */; }
-(double)getLongitude { return /* ... */; }

-(BOOL)isSupported { return YES; }
@end
```

The CN1 iOS port runs **without ARC** for these `.m` files (`CLANG_ENABLE_OBJC_ARC=NO`). Don't rely on autorelease-pool magic; retain manually or use static singletons for objects whose lifetime needs to outlive a method call. (This is also true for native code authored in `Ports/iOSPort/nativeSources/`.)

iOS Info.plist privacy strings have **dedicated build hint names** — set them directly, don't fall back to `ios.plistInject`. The pattern is `ios.<PListKey>=<value>`:

```properties
codename1.arg.ios.NSCameraUsageDescription=Scan QR codes to pair the device.
codename1.arg.ios.NSLocationWhenInUseUsageDescription=Find nearby branches near your location.
codename1.arg.ios.NSPhotoLibraryUsageDescription=Attach photos to support tickets.
codename1.arg.ios.NSMicrophoneUsageDescription=Record voice notes.
```

App Store builds reject location, camera, microphone, photo, contacts, etc. without the appropriate descriptions. Use `ios.plistInject` only for raw XML keys that don't have a dedicated hint.

If you need a CocoaPod dependency, add `codename1.arg.ios.pods=PodName,...` to `codenameone_settings.properties`.

### Android (Java)

`android/src/main/java/com/example/myapp/native_/GpsBridgeImpl.java`:

```java
package com.example.myapp.native_;

import android.location.LocationManager;
import com.codename1.impl.android.AndroidNativeUtil;

public class GpsBridgeImpl {
    public boolean hasGps() {
        LocationManager lm = (LocationManager) AndroidNativeUtil.getActivity()
                .getSystemService(android.content.Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    public void requestPermission() {
        // androidx.core.app.ActivityCompat.requestPermissions(...) — see permissions section
    }
    public double getLatitude() { /* ... */ return 0.0; }
    public double getLongitude() { /* ... */ return 0.0; }
    public boolean isSupported() { return true; }
}
```

Permissions in the Android manifest are injected via `codename1.arg.android.xPermissions`. For example:

```properties
codename1.arg.android.xPermissions=<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
```

Extra Gradle dependencies go in `codename1.arg.android.gradleDep`. See `references/build-hints.md`.

### JavaScript (TeaVM-friendly JS)

`javascript/src/main/javascript/com_example_myapp_native_GpsBridgeImpl.js`:

```javascript
(function(exports) {
    var com_example_myapp_native_GpsBridgeImpl_ = function() {};

    com_example_myapp_native_GpsBridgeImpl_.prototype.hasGps__ = function() {
        return ('geolocation' in navigator);
    };
    com_example_myapp_native_GpsBridgeImpl_.prototype.requestPermission__ = function() {
        // Browser asks on first getCurrentPosition; no-op here.
    };
    com_example_myapp_native_GpsBridgeImpl_.prototype.getLatitude__ = function() {
        return window._lastGpsLat || 0;
    };
    com_example_myapp_native_GpsBridgeImpl_.prototype.getLongitude__ = function() {
        return window._lastGpsLng || 0;
    };
    com_example_myapp_native_GpsBridgeImpl_.prototype.isSupported__ = function() {
        return 'geolocation' in navigator;
    };

    exports.com_example_myapp_native_GpsBridgeImpl_ = com_example_myapp_native_GpsBridgeImpl_;
})(self);
```

Method names map to `<javaMethodName>__<argumentSignature>` — the generated stub will show you the exact name. Async behavior must be modeled with a callback (see below) because the bridge call returns synchronously.

### Desktop simulator (Java)

`javase/src/main/java/com/example/myapp/native_/GpsBridgeImpl.java`:

```java
package com.example.myapp.native_;

public class GpsBridgeImpl {
    public boolean hasGps() { return false; }              // Simulator: usually no real GPS
    public void requestPermission() {}
    public double getLatitude() { return 37.7749; }        // Hardcode for testing
    public double getLongitude() { return -122.4194; }
    public boolean isSupported() { return true; }
}
```

The desktop stub is where you mock platform behavior so you can iterate on the rest of the app in the simulator without a phone.

## 5. Calling the native interface from Java

```java
import com.codename1.system.NativeLookup;

GpsBridge gps = (GpsBridge) NativeLookup.create(GpsBridge.class);
if (gps != null && gps.isSupported()) {
    gps.requestPermission();
    double lat = gps.getLatitude();
    double lng = gps.getLongitude();
} else {
    // Platform doesn't support GPS, or the implementation failed to register.
}
```

`NativeLookup.create` returns `null` if the platform has no implementation registered. Always null-check, and gate behavior on `isSupported()`.

## Callbacks (native → Java)

Native interface methods are synchronous. For platform APIs that work via callbacks (location updates, push tokens, scan results), the bridge needs to:

1. Take a request from Java (e.g. `startScanning()`).
2. Wire up the native-side callback.
3. When the native callback fires, dispatch back to the Java side.

The conventional pattern is to **expose a separate Java static method** on the bridge class that the native impl can call back into:

```java
// common/src/main/java/com/example/myapp/native_/GpsBridge.java
package com.example.myapp.native_;

import com.codename1.system.NativeInterface;
import com.codename1.ui.CN;

public interface GpsBridge extends NativeInterface {
    void startUpdating();
    void stopUpdating();
}

// common/src/main/java/com/example/myapp/native_/GpsBridgeCallback.java
package com.example.myapp.native_;
import com.codename1.ui.CN;

public final class GpsBridgeCallback {
    public interface Listener { void onLocation(double lat, double lng); }
    private static Listener listener;
    public static void setListener(Listener l) { listener = l; }

    // Native code calls this directly. Marshal back to the EDT so the listener can
    // safely touch the UI.
    public static void onLocation(final double lat, final double lng) {
        CN.callSerially(() -> {
            if (listener != null) listener.onLocation(lat, lng);
        });
    }
}
```

The native impl invokes `GpsBridgeCallback.onLocation(lat, lng)` directly — it can do that because the method is a regular Java static. CN1's cross-compiler emits a reachable entry point for it on every target platform.

On the iOS side:

```objc
// inside the CLLocationManagerDelegate callback
-(void)locationManager:(CLLocationManager *)manager didUpdateLocations:(NSArray<CLLocation*>*)locations {
    CLLocation *loc = [locations lastObject];
    com_example_myapp_native_GpsBridgeCallback_onLocation___double_double(
        threadStateData, loc.coordinate.latitude, loc.coordinate.longitude);
}
```

(The exact symbol name is mangled — the generated stub will show the form for your interface.)

On Android:

```java
// inside LocationListener.onLocationChanged
@Override public void onLocationChanged(Location loc) {
    com.example.myapp.native_.GpsBridgeCallback.onLocation(loc.getLatitude(), loc.getLongitude());
}
```

On JavaScript:

```javascript
navigator.geolocation.watchPosition(function(pos) {
    var c = pos.coords;
    com_example_myapp_native_GpsBridgeCallback_.onLocation__double_double(c.latitude, c.longitude);
});
```

## Common pitfalls

- **Method signature mismatch between the interface and the stub** — happens after you edit the Java interface but forget to regenerate. Re-run `mvn cn1:generate-native-interfaces -Dcn1.generateNativeInterfaces.overwrite=true` and re-apply your platform code.
- **Returning Java objects** — not supported by the bridge marshaler. Return primitives, `String`, `byte[]`, or `PeerComponent` only.
- **`PeerComponent` on iOS without ARC** — peer-component implementations can dangle if you treat the bridge like an ARC-managed Swift method. Retain natively, or wrap returned views in a static holder.
- **Permissions / Info.plist** — the build server happily accepts a native interface that calls a privacy-protected API, but the App Store / Play Store reject it. Set `codename1.arg.ios.plistInject` and `codename1.arg.android.xPermissions` (see `references/build-hints.md`).
- **Forgetting `isSupported()` return** — defaults to `false`, so the Java side thinks the bridge isn't available. Always override.
- **`NativeLookup.create()` returns null in the simulator only** — usually means the `javase/` impl class is missing or in the wrong package.
