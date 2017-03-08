# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in G:\AndroidEnvironment\sdk_1427719283328\sdk/tools/proguard/proguard-android.txt
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

######picasso########
# Keep Picasso
-dontwarn com.squareup.okhttp.**

# Add this global rule
-keepattributes Signature

# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models. Modify to fit the structure
# of your app.
-keepclassmembers class com.yourcompany.models.** {
  *;
}

########update checker#########
-keep public class org.jsoup.** {
    public *;
}

-keep class javax.** { *; }
-keep class org.** { *; }
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v7.app.** { *; }
-keep interface android.support.v7.app.** { *; }
# When layoutManager xml attribute is used, RecyclerView inflates
#LayoutManagers' constructors using reflection.
-keep public class * extends android.support.v7.widget.RecyclerView$LayoutManager {
    public <init>(...);
}

-keep class android.support.v7.widget.RoundRectDrawable { *; }
