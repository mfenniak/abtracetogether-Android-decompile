package ca.albertahealthservices.contacttracing.api.auth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.challengehandler.SecurityCheckChallengeHandler;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 3}, d1 = {"\0004\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\013\n\000\n\002\020\002\n\002\b\003\n\002\030\002\n\002\b\b\030\000 \0272\0020\001:\001\027B\005¢\006\002\020\002J\020\020\013\032\0020\f2\b\b\002\020\r\032\0020\nJ\020\020\016\032\0020\f2\006\020\017\032\0020\020H\026J\020\020\021\032\0020\f2\006\020\022\032\0020\020H\026J\020\020\023\032\0020\f2\006\020\024\032\0020\020H\026J\020\020\025\032\0020\f2\b\020\026\032\004\030\0010\bR\016\020\003\032\0020\004X\004¢\006\002\n\000R\016\020\005\032\0020\006X\004¢\006\002\n\000R\016\020\007\032\0020\bX\016¢\006\002\n\000R\016\020\t\032\0020\nX\016¢\006\002\n\000¨\006\030"}, d2 = {"Lca/albertahealthservices/contacttracing/api/auth/SmsCodeChallengeHandler;", "Lcom/worklight/wlclient/api/challengehandler/SecurityCheckChallengeHandler;", "()V", "broadcastManager", "Landroidx/localbroadcastmanager/content/LocalBroadcastManager;", "context", "Landroid/content/Context;", "errorMsg", "", "isChallenged", "", "cancelChallenge", "", "skipCancelledBroadcast", "handleChallenge", "jsonObject", "Lorg/json/JSONObject;", "handleFailure", "error", "handleSuccess", "identity", "verifySmsCode", "otp", "Companion", "app_release"}, k = 1, mv = {1, 1, 16})
public final class SmsCodeChallengeHandler extends SecurityCheckChallengeHandler {
  public static final String ACTION_CANCEL_CHALLENGE = "ACTION_CANCEL_CHALLENGE";
  
  public static final String ACTION_CHALLENGE_CANCELLED = "ACTION_CHALLENGE_CANCELLED";
  
  public static final String ACTION_VERIFY_SMS_CODE = "ACTION_VERIFY_SMS_CODE";
  
  public static final String ACTION_VERIFY_SMS_CODE_FAIL = "ACTION_VERIFY_SMS_CODE_FAIL";
  
  public static final String ACTION_VERIFY_SMS_CODE_REQUIRED = "ACTION_VERIFY_SMS_CODE_REQUIRED";
  
  public static final String ACTION_VERIFY_SMS_CODE_SUCCESS = "ACTION_VERIFY_SMS_CODE_SUCCESS";
  
  public static final Companion Companion = new Companion(null);
  
  public static final String EXTRA_SKIP_CANCELLED_BROADCAST = "EXTRA_SKIP_CANCELLED_BROADCAST";
  
  private final LocalBroadcastManager broadcastManager;
  
  private final Context context;
  
  private String errorMsg = "";
  
  private boolean isChallenged;
  
  public SmsCodeChallengeHandler() {
    super("smsOtpService");
    WLClient wLClient = WLClient.getInstance();
    Intrinsics.checkExpressionValueIsNotNull(wLClient, "WLClient.getInstance()");
    Context context = wLClient.getContext();
    Intrinsics.checkExpressionValueIsNotNull(context, "WLClient.getInstance().context");
    this.context = context;
    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
    Intrinsics.checkExpressionValueIsNotNull(localBroadcastManager, "LocalBroadcastManager.getInstance(context)");
    this.broadcastManager = localBroadcastManager;
    localBroadcastManager.registerReceiver(new BroadcastReceiver() {
          public void onReceive(Context param1Context, Intent param1Intent) {
            Intrinsics.checkParameterIsNotNull(param1Intent, "intent");
            try {
              SmsCodeChallengeHandler.this.verifySmsCode(param1Intent.getStringExtra("otp"));
            } catch (JSONException jSONException) {
              jSONException.printStackTrace();
            } 
          }
        }new IntentFilter("ACTION_VERIFY_SMS_CODE"));
    this.broadcastManager.registerReceiver(new BroadcastReceiver() {
          public void onReceive(Context param1Context, Intent param1Intent) {
            Intrinsics.checkParameterIsNotNull(param1Intent, "intent");
            SmsCodeChallengeHandler.this.cancelChallenge(param1Intent.getBooleanExtra("EXTRA_SKIP_CANCELLED_BROADCAST", false));
          }
        }new IntentFilter("ACTION_CANCEL_CHALLENGE"));
  }
  
  public final void cancelChallenge(boolean paramBoolean) {
    try {
      cancel();
      if (!paramBoolean) {
        Intent intent = new Intent();
        this();
        intent.setAction("ACTION_CHALLENGE_CANCELLED");
        this.broadcastManager.sendBroadcast(intent);
      } 
      CentralLog.Companion.d("smsOTP", "canceledChallenge");
    } catch (NullPointerException nullPointerException) {
      CentralLog.Companion.d("smsOTP", "no challenge to cancel");
    } 
  }
  
  public void handleChallenge(JSONObject paramJSONObject) {
    Intrinsics.checkParameterIsNotNull(paramJSONObject, "jsonObject");
    CentralLog.Companion companion = CentralLog.Companion;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Challenge Received - ");
    stringBuilder.append(paramJSONObject);
    companion.d("smsOTP", stringBuilder.toString());
    this.isChallenged = true;
    try {
      String str;
      if (paramJSONObject.isNull("errorMsg")) {
        str = "";
      } else {
        str = str.getString("errorMsg");
        Intrinsics.checkExpressionValueIsNotNull(str, "jsonObject.getString(\"errorMsg\")");
      } 
      this.errorMsg = str;
    } catch (JSONException jSONException) {
      jSONException.printStackTrace();
    } 
    Intent intent = new Intent();
    intent.setAction("ACTION_VERIFY_SMS_CODE_REQUIRED");
    intent.putExtra("errorMsg", this.errorMsg);
    this.broadcastManager.sendBroadcast(intent);
  }
  
  public void handleFailure(JSONObject paramJSONObject) {
    Intrinsics.checkParameterIsNotNull(paramJSONObject, "error");
    super.handleFailure(paramJSONObject);
    CentralLog.Companion companion = CentralLog.Companion;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("handleFailure - ");
    stringBuilder.append(paramJSONObject);
    companion.d("smsOTP", stringBuilder.toString());
    this.isChallenged = false;
    if (paramJSONObject.isNull("failure")) {
      this.errorMsg = "Failed to veify . Please try again later.";
    } else {
      try {
        String str = paramJSONObject.getString("failure");
        Intrinsics.checkExpressionValueIsNotNull(str, "error.getString(\"failure\")");
        this.errorMsg = str;
      } catch (JSONException jSONException) {
        jSONException.printStackTrace();
      } 
    } 
    Intent intent = new Intent();
    intent.setAction("ACTION_VERIFY_SMS_CODE_FAIL");
    intent.putExtra("errorMsg", this.errorMsg);
    this.broadcastManager.sendBroadcast(intent);
  }
  
  public void handleSuccess(JSONObject paramJSONObject) {
    Intrinsics.checkParameterIsNotNull(paramJSONObject, "identity");
    super.handleSuccess(paramJSONObject);
    this.isChallenged = false;
    Intent intent = new Intent();
    intent.setAction("ACTION_VERIFY_SMS_CODE_SUCCESS");
    this.broadcastManager.sendBroadcast(intent);
    CentralLog.Companion.d("smsOTP", "handleSuccess");
  }
  
  public final void verifySmsCode(String paramString) {
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("code", paramString);
    submitChallengeAnswer(jSONObject);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\007\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000R\016\020\006\032\0020\004XT¢\006\002\n\000R\016\020\007\032\0020\004XT¢\006\002\n\000R\016\020\b\032\0020\004XT¢\006\002\n\000R\016\020\t\032\0020\004XT¢\006\002\n\000R\016\020\n\032\0020\004XT¢\006\002\n\000¨\006\013"}, d2 = {"Lca/albertahealthservices/contacttracing/api/auth/SmsCodeChallengeHandler$Companion;", "", "()V", "ACTION_CANCEL_CHALLENGE", "", "ACTION_CHALLENGE_CANCELLED", "ACTION_VERIFY_SMS_CODE", "ACTION_VERIFY_SMS_CODE_FAIL", "ACTION_VERIFY_SMS_CODE_REQUIRED", "ACTION_VERIFY_SMS_CODE_SUCCESS", "EXTRA_SKIP_CANCELLED_BROADCAST", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Companion {
    private Companion() {}
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/api/auth/SmsCodeChallengeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */