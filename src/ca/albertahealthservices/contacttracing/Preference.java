package ca.albertahealthservices.contacttracing;

import android.content.Context;
import android.content.SharedPreferences;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000>\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\f\n\002\030\002\n\000\n\002\020\b\n\000\n\002\020\t\n\002\b\007\n\002\020\013\n\000\n\002\020\002\n\002\b\017\n\002\030\002\n\002\b\002\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\016\020\017\032\0020\0042\006\020\020\032\0020\021J\016\020\022\032\0020\0232\006\020\020\032\0020\021J\016\020\024\032\0020\0252\006\020\020\032\0020\021J\016\020\026\032\0020\0252\006\020\020\032\0020\021J\016\020\027\032\0020\0252\006\020\020\032\0020\021J\016\020\030\032\0020\0252\006\020\020\032\0020\021J\016\020\031\032\0020\0042\006\020\020\032\0020\021J\020\020\032\032\0020\0042\b\020\020\032\004\030\0010\021J\016\020\033\032\0020\0232\006\020\020\032\0020\021J\016\020\034\032\0020\0352\006\020\020\032\0020\021J\026\020\036\032\0020\0372\006\020\020\032\0020\0212\006\020 \032\0020\004J\026\020!\032\0020\0372\006\020\020\032\0020\0212\006\020\"\032\0020\023J\026\020#\032\0020\0372\006\020\020\032\0020\0212\006\020$\032\0020\025J\026\020%\032\0020\0372\006\020\020\032\0020\0212\006\020\"\032\0020\035J\026\020&\032\0020\0372\006\020\020\032\0020\0212\006\020$\032\0020\025J\026\020'\032\0020\0372\006\020\020\032\0020\0212\006\020(\032\0020\025J\026\020)\032\0020\0372\006\020\020\032\0020\0212\006\020$\032\0020\025J\026\020*\032\0020\0372\006\020\020\032\0020\0212\006\020\"\032\0020\004J\026\020+\032\0020\0372\006\020\020\032\0020\0212\006\020\"\032\0020\004J\026\020,\032\0020\0372\006\020\020\032\0020\0212\006\020\"\032\0020\023J\026\020-\032\0020\0372\006\020\020\032\0020\0212\006\020.\032\0020/J\026\0200\032\0020\0372\006\020\020\032\0020\0212\006\020.\032\0020/R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000R\016\020\006\032\0020\004XT¢\006\002\n\000R\016\020\007\032\0020\004XT¢\006\002\n\000R\016\020\b\032\0020\004XT¢\006\002\n\000R\016\020\t\032\0020\004XT¢\006\002\n\000R\016\020\n\032\0020\004XT¢\006\002\n\000R\016\020\013\032\0020\004XT¢\006\002\n\000R\016\020\f\032\0020\004XT¢\006\002\n\000R\016\020\r\032\0020\004XT¢\006\002\n\000R\016\020\016\032\0020\004XT¢\006\002\n\000¨\0061"}, d2 = {"Lca/albertahealthservices/contacttracing/Preference;", "", "()V", "ANNOUNCEMENT", "", "CHECK_POINT", "EXPIRY_TIME", "IS_ONBOARDED", "LAST_FETCH_TIME", "LAST_PURGE_TIME", "NEXT_FETCH_TIME", "PHONE_NUMBER", "PREF_ID", "UUID", "UUID_RETRY_ATTEMPTS", "getAnnouncement", "context", "Landroid/content/Context;", "getCheckpoint", "", "getExpiryTimeInMillis", "", "getLastFetchTimeInMillis", "getLastPurgeTime", "getNextFetchTimeInMillis", "getPhoneNumber", "getUUID", "getUUIDRetryAttempts", "isOnBoarded", "", "putAnnouncement", "", "announcement", "putCheckpoint", "value", "putExpiryTimeInMillis", "time", "putIsOnBoarded", "putLastFetchTimeInMillis", "putLastPurgeTime", "lastPurgeTime", "putNextFetchTimeInMillis", "putPhoneNumber", "putUUID", "putUUIDRetryAttempts", "registerListener", "listener", "Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;", "unregisterListener", "app_release"}, k = 1, mv = {1, 1, 16})
public final class Preference {
  private static final String ANNOUNCEMENT = "ANNOUNCEMENT";
  
  private static final String CHECK_POINT = "CHECK_POINT";
  
  private static final String EXPIRY_TIME = "EXPIRY_TIME";
  
  public static final Preference INSTANCE = new Preference();
  
  private static final String IS_ONBOARDED = "IS_ONBOARDED";
  
  private static final String LAST_FETCH_TIME = "LAST_FETCH_TIME";
  
  private static final String LAST_PURGE_TIME = "LAST_PURGE_TIME";
  
  private static final String NEXT_FETCH_TIME = "NEXT_FETCH_TIME";
  
  private static final String PHONE_NUMBER = "PHONE_NUMBER";
  
  private static final String PREF_ID = "Tracer_pref";
  
  private static final String UUID = "UUID";
  
  private static final String UUID_RETRY_ATTEMPTS = "UUID_RETRY_ATTEMPTS";
  
  public final String getAnnouncement(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    SharedPreferences sharedPreferences = paramContext.getSharedPreferences("Tracer_pref", 0);
    String str1 = "";
    String str2 = sharedPreferences.getString("ANNOUNCEMENT", "");
    if (str2 != null)
      str1 = str2; 
    return str1;
  }
  
  public final int getCheckpoint(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    return paramContext.getSharedPreferences("Tracer_pref", 0).getInt("CHECK_POINT", 0);
  }
  
  public final long getExpiryTimeInMillis(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    return paramContext.getSharedPreferences("Tracer_pref", 0).getLong("EXPIRY_TIME", 0L);
  }
  
  public final long getLastFetchTimeInMillis(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    return paramContext.getSharedPreferences("Tracer_pref", 0).getLong("LAST_FETCH_TIME", 0L);
  }
  
  public final long getLastPurgeTime(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    return paramContext.getSharedPreferences("Tracer_pref", 0).getLong("LAST_PURGE_TIME", 0L);
  }
  
  public final long getNextFetchTimeInMillis(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    return paramContext.getSharedPreferences("Tracer_pref", 0).getLong("NEXT_FETCH_TIME", 0L);
  }
  
  public final String getPhoneNumber(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    SharedPreferences sharedPreferences = paramContext.getSharedPreferences("Tracer_pref", 0);
    String str1 = "";
    String str2 = sharedPreferences.getString("PHONE_NUMBER", "");
    if (str2 != null)
      str1 = str2; 
    return str1;
  }
  
  public final String getUUID(Context paramContext) {
    String str1 = "";
    String str2 = str1;
    if (paramContext != null) {
      SharedPreferences sharedPreferences = paramContext.getSharedPreferences("Tracer_pref", 0);
      str2 = str1;
      if (sharedPreferences != null) {
        String str = sharedPreferences.getString("UUID", "");
        str2 = str1;
        if (str != null)
          str2 = str; 
      } 
    } 
    return str2;
  }
  
  public final int getUUIDRetryAttempts(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    return paramContext.getSharedPreferences("Tracer_pref", 0).getInt("UUID_RETRY_ATTEMPTS", 0);
  }
  
  public final boolean isOnBoarded(Context paramContext) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    return paramContext.getSharedPreferences("Tracer_pref", 0).getBoolean("IS_ONBOARDED", false);
  }
  
  public final void putAnnouncement(Context paramContext, String paramString) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramString, "announcement");
    paramContext.getSharedPreferences("Tracer_pref", 0).edit().putString("ANNOUNCEMENT", paramString).apply();
  }
  
  public final void putCheckpoint(Context paramContext, int paramInt) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    paramContext.getSharedPreferences("Tracer_pref", 0).edit().putInt("CHECK_POINT", paramInt).apply();
  }
  
  public final void putExpiryTimeInMillis(Context paramContext, long paramLong) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    paramContext.getSharedPreferences("Tracer_pref", 0).edit().putLong("EXPIRY_TIME", paramLong).apply();
  }
  
  public final void putIsOnBoarded(Context paramContext, boolean paramBoolean) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    paramContext.getSharedPreferences("Tracer_pref", 0).edit().putBoolean("IS_ONBOARDED", paramBoolean).apply();
  }
  
  public final void putLastFetchTimeInMillis(Context paramContext, long paramLong) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    paramContext.getSharedPreferences("Tracer_pref", 0).edit().putLong("LAST_FETCH_TIME", paramLong).apply();
  }
  
  public final void putLastPurgeTime(Context paramContext, long paramLong) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    paramContext.getSharedPreferences("Tracer_pref", 0).edit().putLong("LAST_PURGE_TIME", paramLong).apply();
  }
  
  public final void putNextFetchTimeInMillis(Context paramContext, long paramLong) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    paramContext.getSharedPreferences("Tracer_pref", 0).edit().putLong("NEXT_FETCH_TIME", paramLong).apply();
  }
  
  public final void putPhoneNumber(Context paramContext, String paramString) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramString, "value");
    paramContext.getSharedPreferences("Tracer_pref", 0).edit().putString("PHONE_NUMBER", paramString).apply();
  }
  
  public final void putUUID(Context paramContext, String paramString) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramString, "value");
    paramContext.getSharedPreferences("Tracer_pref", 0).edit().putString("UUID", paramString).apply();
  }
  
  public final void putUUIDRetryAttempts(Context paramContext, int paramInt) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    paramContext.getSharedPreferences("Tracer_pref", 0).edit().putInt("UUID_RETRY_ATTEMPTS", paramInt).apply();
  }
  
  public final void registerListener(Context paramContext, SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramOnSharedPreferenceChangeListener, "listener");
    paramContext.getSharedPreferences("Tracer_pref", 0).registerOnSharedPreferenceChangeListener(paramOnSharedPreferenceChangeListener);
  }
  
  public final void unregisterListener(Context paramContext, SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramOnSharedPreferenceChangeListener, "listener");
    paramContext.getSharedPreferences("Tracer_pref", 0).unregisterOnSharedPreferenceChangeListener(paramOnSharedPreferenceChangeListener);
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/Preference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */