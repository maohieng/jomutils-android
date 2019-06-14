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

# Needed by google-api-client to keep generic types and @Key annotations accessed via reflection

# Fix for connecting appending release build
-keepattributes Signature, Exceptions, RuntimeVisibleAnnotations,AnnotationDefault

-keepclassmembers class * {
  @com.google.api.client.util.Key <fields>;
}

-keep class com.google.** { *;}
-keep interface com.google.** { *;}
-dontwarn com.google.**

# Needed by google-http-client-android when linking against an older platform version
-dontwarn com.google.api.client.extensions.android.**

# Needed by google-api-client-android when linking against an older platform version
-dontwarn com.google.api.client.googleapis.extensions.android.**

# Fix for build release
# https://github.com/krschultz/android-proguard-snippets/blob/master/libraries/proguard-guava.pro
-dontwarn sun.misc.Unsafe
-dontwarn java.lang.ClassValue
-dontwarn com.google.j2objc.annotations.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

-dontwarn com.google.common.escape.*
-dontwarn com.google.android.gms.auth.*
-dontwarn com.google.errorprone.annotations.*
-dontwarn com.google.errorprone.annotations.concurrent.LazyInit
# https://github.com/krschultz/android-proguard-snippets/blob/master/libraries/proguard-square-okio.pro
-dontwarn java.nio.file.*
