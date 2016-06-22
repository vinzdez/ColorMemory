# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android-SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


#default values

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep class colormemory.vicente.com.colormemory.**
-keepclassmembers class colormemory.vicente.com.colormemory.** { *; }
-keep interface colormemory.vicente.com.colormemory.**
-keepclassmembers interface colormemory.vicente.com.colormemory.** { *; }

-keep public class * extends android.app.Activity
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}



# Preserve all native method names and the names of their classes.

-keepclasseswithmembernames class * {
    native <methods>;
}

# Preserve the special static methods that are required in all enumeration
# classes.

-keepclassmembers class * extends java.lang.Enum {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}



# Realm

-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }
-dontwarn javax.**
-dontwarn io.realm.**

# ButterKnife

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-keep class android.net.http.** { *; }
-dontwarn android.net.http.**
-dontnote android.net.http.**
-dontnote org.apache.http.**
-dontnote android.support.v4.app.NotificationCompatJellybean

-keep class com.google.vending.licensing.ILicensingService
-dontnote com.google.vending.licensing.ILicensingService
-dontwarn com.google.common.**
-dontnote com.google.common.base.FinalizableReferenceQueue
-dontnote com.google.common.base.FinalizableReference
-dontnote com.google.common.reflect.**
-dontwarn android.support.**
-dontnote android.support.**
