# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Hide app structure by obfuscating all code (default behavior when minifyEnabled is true)
# No special rule needed here — R8 will obfuscate names unless marked with -keep

##############################################################
# Application Security: Preserve Critical Components & Enable Obfuscation
#
# This configuration protects important app components from being stripped
# or renamed during the build process. It keeps essential Android classes
# like Activities, Services, and BroadcastReceivers from being removed,
# ensures that dependency injection works properly, and preserves the
# Application class. These rules help shrink, optimise, and obfuscate
# the rest of the codebase, making reverse engineering more difficult
# while maintaining correct app behavior.
##############################################################

# Keep all constructors annotated with @Inject to support dependency injection
-keepclassmembers class * {
    @javax.inject.Inject <init>(...);
}

# Keep essential Android components that are declared in the AndroidManifest.xml
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep the custom Application class, which is the app’s entry point
-keep class com.example.harpjourneyapp.MyApp { *; }
