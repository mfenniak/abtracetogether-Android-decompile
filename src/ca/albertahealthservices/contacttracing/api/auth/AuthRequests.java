package ca.albertahealthservices.contacttracing.api.auth;

import android.content.Context;
import ca.albertahealthservices.contacttracing.TracerApp;
import ca.albertahealthservices.contacttracing.api.ErrorCode;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import ca.albertahealthservices.contacttracing.logging.WFLog;
import com.worklight.wlclient.api.WLAccessTokenListener;
import com.worklight.wlclient.api.WLAuthorizationManager;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.challengehandler.BaseChallengeHandler;
import com.worklight.wlclient.auth.AccessToken;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\"\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\016\n\002\b\002\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\033\020\007\032\0020\b2\b\b\002\020\t\032\0020\nH@ø\001\000¢\006\002\020\013R\022\020\003\032\0020\004X\005¢\006\006\032\004\b\005\020\006\002\004\n\002\b\031¨\006\f"}, d2 = {"Lca/albertahealthservices/contacttracing/api/auth/AuthRequests;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "obtainAccessToken", "Lca/albertahealthservices/contacttracing/api/auth/AuthResponse;", "scope", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class AuthRequests implements CoroutineScope {
  public static final AuthRequests INSTANCE = new AuthRequests();
  
  static {
    WLClient.getInstance().registerChallengeHandler((BaseChallengeHandler)new SmsCodeChallengeHandler());
  }
  
  public CoroutineContext getCoroutineContext() {
    return this.$$delegate_0.getCoroutineContext();
  }
  
  public final Object obtainAccessToken(String paramString, Continuation<? super AuthResponse> paramContinuation) {
    SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(paramContinuation));
    Continuation continuation = (Continuation)safeContinuation;
    WLAuthorizationManager.getInstance().obtainAccessToken(paramString, new AuthRequests$obtainAccessToken$2$1(continuation));
    Object object = safeContinuation.getOrThrow();
    if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED())
      DebugProbesKt.probeCoroutineSuspended(paramContinuation); 
    return object;
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\037\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H\026J\020\020\006\032\0020\0032\006\020\007\032\0020\bH\026¨\006\t"}, d2 = {"ca/albertahealthservices/contacttracing/api/auth/AuthRequests$obtainAccessToken$2$1", "Lcom/worklight/wlclient/api/WLAccessTokenListener;", "onFailure", "", "response", "Lcom/worklight/wlclient/api/WLFailResponse;", "onSuccess", "accessToken", "Lcom/worklight/wlclient/auth/AccessToken;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class AuthRequests$obtainAccessToken$2$1 implements WLAccessTokenListener {
    AuthRequests$obtainAccessToken$2$1(Continuation param1Continuation) {}
    
    public void onFailure(WLFailResponse param1WLFailResponse) {
      Intrinsics.checkParameterIsNotNull(param1WLFailResponse, "response");
      WFLog.Companion.logError("Error retrieving Access Token");
      if (param1WLFailResponse.getErrorMsg() != null) {
        CentralLog.Companion companion1 = CentralLog.Companion;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(param1WLFailResponse.getErrorStatusCode());
        stringBuilder.append(" - ");
        stringBuilder.append(param1WLFailResponse.getErrorMsg());
        companion1.d("Request", stringBuilder.toString());
      } 
      Continuation continuation = this.$cont;
      String str2 = param1WLFailResponse.getErrorStatusCode();
      ErrorCode errorCode = ErrorCode.INSTANCE;
      Context context = TracerApp.Companion.getAppContext();
      String str1 = param1WLFailResponse.getErrorStatusCode();
      if (str1 == null)
        str1 = ""; 
      AuthResponse authResponse = new AuthResponse(null, str2, errorCode.getStringForErrorCode(context, str1));
      Result.Companion companion = Result.Companion;
      continuation.resumeWith(Result.constructor-impl(authResponse));
    }
    
    public void onSuccess(AccessToken param1AccessToken) {
      Intrinsics.checkParameterIsNotNull(param1AccessToken, "accessToken");
      Continuation continuation = this.$cont;
      AuthResponse authResponse = new AuthResponse(param1AccessToken, null, null, 6, null);
      Result.Companion companion = Result.Companion;
      continuation.resumeWith(Result.constructor-impl(authResponse));
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/api/auth/AuthRequests.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */