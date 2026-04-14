# Flybuys Loyalty App ProGuard Rules

# Keep Amplitude SDK classes
-keep class com.amplitude.** { *; }
-dontwarn com.amplitude.**

# Keep data model classes for serialization
-keep class com.flybuys.loyaltyapp.data.model.** { *; }

# Keep analytics event classes
-keep class com.flybuys.loyaltyapp.analytics.** { *; }

# Keep Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# General Android rules
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
