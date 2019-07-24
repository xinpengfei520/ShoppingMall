# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android_developer_tools\android-sdk-windows/tools/proguard/proguard-android.txt
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

#-------------------------------------- 基本不用动区域 start ------------------------------------------

#--------------------------------- 基本指令区 start ----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,*JavascriptInterface*,InnerClasses,Signature,SourceFile,LineNumberTable
#-ignorewarning
#--------------------------------- 基本指令区 start ----------------------------------

#--------------------------------- 默认保留区 start ----------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep class **.R$* {*;}

-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}
#-------------------------------- 默认保留区 end ------------------------------------------

#--------------------------------- webview start ------------------------------------
# 保留 JavascriptInterface 中的方法
-keepclassmembers class * {@android.webkit.JavascriptInterface <methods>;}

-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
    public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}

#--------------------------------- webview end ------------------------------------

#------------------------------------- 基本不用动区域 end -------------------------------------------

#--------------------------------- 第三方库 start ------------------------------------
# okhttp3
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

# okio
-dontwarn okio.**
-keep class okio.**{*;}

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

# Application classes that will be serialized/deserialized over Gson
-keep class com.atguigu.shoppingmall.domain.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

##---------------End: proguard configuration for Gson  ----------

# Retrofit2
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8

# BaseRecyclerViewAdapterHelper
-keep class com.chad.library.adapter.** { *; }
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}

# volley
-dontwarn com.android.volley.**
-keep class com.android.volley.** {*;}

# alipay
-keep class com.alipay.** {*;}
-dontwarn com.alipay.**

# fastjson
-keep class com.alibaba.fastjson.**{ *;}
-dontwarn com.alibaba.fastjson.**

# glide
-keep class com.bumptech.glide.**{ *;}
-dontwarn com.bumptech.glide.**

# apache http
-keep class org.apache.http.**{ *;}
-dontwarn org.apache.http.**

# ssl
-dontwarn android.net.**
-keep class android.net.SSLCertificateSocketFactory{*;}

# Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

# banner
# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
}

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

#科大讯飞
-keep class com.iflytek.** { *; }

# sharesdk
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}

#--------------------------------- 第三方库 end ------------------------------------


#--------------------------------- 项目个性化配置 start ------------------------------------

#--------------------------------- 项目个性化配置 end --------------------------------------