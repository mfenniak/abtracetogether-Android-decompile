package ca.albertahealthservices.contacttracing.api;

import ca.albertahealthservices.contacttracing.api.auth.SmsCodeChallengeHandler;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLResourceRequest;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;
import com.worklight.wlclient.api.challengehandler.BaseChallengeHandler;
import java.net.URI;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 3}, d1 = {"\000>\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\007\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002Jm\020\017\032\0020\0202\006\020\021\032\0020\0042\006\020\022\032\0020\0042\b\b\002\020\023\032\0020\0242\n\b\002\020\025\032\004\030\0010\0042\n\b\002\020\026\032\004\030\0010\0272(\b\002\020\030\032\"\022\004\022\0020\004\022\004\022\0020\004\030\0010\031j\020\022\004\022\0020\004\022\004\022\0020\004\030\001`\032H@ø\001\000¢\006\002\020\033R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000R\016\020\006\032\0020\004XT¢\006\002\n\000R\016\020\007\032\0020\004XT¢\006\002\n\000R\016\020\b\032\0020\004XT¢\006\002\n\000R\016\020\t\032\0020\004XT¢\006\002\n\000R\016\020\n\032\0020\004XT¢\006\002\n\000R\022\020\013\032\0020\fX\005¢\006\006\032\004\b\r\020\016\002\004\n\002\b\031¨\006\034"}, d2 = {"Lca/albertahealthservices/contacttracing/api/Request;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "DELETE", "", "GET", "HEAD", "OPTIONS", "POST", "PUT", "TRACE", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "runRequest", "Lca/albertahealthservices/contacttracing/api/Response;", "path", "method", "timeout", "", "scope", "data", "Lorg/json/JSONObject;", "queryParams", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lorg/json/JSONObject;Ljava/util/HashMap;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class Request implements CoroutineScope {
  public static final String DELETE = "DELETE";
  
  public static final String GET = "GET";
  
  public static final String HEAD = "HEAD";
  
  public static final Request INSTANCE = new Request();
  
  public static final String OPTIONS = "OPTIONS";
  
  public static final String POST = "POST";
  
  public static final String PUT = "PUT";
  
  public static final String TRACE = "TRACE";
  
  static {
    WLClient.getInstance().registerChallengeHandler((BaseChallengeHandler)new SmsCodeChallengeHandler());
  }
  
  public CoroutineContext getCoroutineContext() {
    return this.$$delegate_0.getCoroutineContext();
  }
  
  public final Object runRequest(String paramString1, String paramString2, int paramInt, String paramString3, JSONObject paramJSONObject, HashMap<String, String> paramHashMap, Continuation<? super Response> paramContinuation) {
    SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(paramContinuation));
    Continuation continuation = (Continuation)safeContinuation;
    WLResourceRequest wLResourceRequest = new WLResourceRequest(new URI(paramString1), paramString2, paramInt, paramString3);
    CentralLog.Companion companion = CentralLog.Companion;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("method=");
    stringBuilder.append(wLResourceRequest.getMethod());
    stringBuilder.append(" - url=");
    stringBuilder.append(wLResourceRequest.getUrl());
    companion.d("Request", stringBuilder.toString());
    if (paramHashMap != null)
      wLResourceRequest.setQueryParameters(paramHashMap); 
    Request$runRequest$2$listener$1 request$runRequest$2$listener$1 = new Request$runRequest$2$listener$1(wLResourceRequest, continuation);
    if (paramJSONObject == null) {
      wLResourceRequest.send(request$runRequest$2$listener$1);
    } else {
      wLResourceRequest.send(paramJSONObject, request$runRequest$2$listener$1);
    } 
    Object object = safeContinuation.getOrThrow();
    if (object == IntrinsicsKt.getCOROUTINE_SUSPENDED())
      DebugProbesKt.probeCoroutineSuspended(paramContinuation); 
    return object;
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\035\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\022\020\002\032\0020\0032\b\020\004\032\004\030\0010\005H\026J\022\020\006\032\0020\0032\b\020\004\032\004\030\0010\007H\026¨\006\b"}, d2 = {"ca/albertahealthservices/contacttracing/api/Request$runRequest$2$listener$1", "Lcom/worklight/wlclient/api/WLResponseListener;", "onFailure", "", "response", "Lcom/worklight/wlclient/api/WLFailResponse;", "onSuccess", "Lcom/worklight/wlclient/api/WLResponse;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Request$runRequest$2$listener$1 implements WLResponseListener {
    Request$runRequest$2$listener$1(WLResourceRequest param1WLResourceRequest, Continuation param1Continuation) {}
    
    public void onFailure(WLFailResponse param1WLFailResponse) {
      // Byte code:
      //   0: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   3: astore_2
      //   4: new java/lang/StringBuilder
      //   7: dup
      //   8: invokespecial <init> : ()V
      //   11: astore_3
      //   12: aload_3
      //   13: ldc 'Request.onFailure url='
      //   15: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   18: pop
      //   19: aload_3
      //   20: aload_0
      //   21: getfield $request : Lcom/worklight/wlclient/api/WLResourceRequest;
      //   24: invokevirtual getUrl : ()Ljava/net/URL;
      //   27: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   30: pop
      //   31: aload_3
      //   32: ldc ' -  response='
      //   34: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   37: pop
      //   38: aload_3
      //   39: aload_1
      //   40: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   43: pop
      //   44: aload_2
      //   45: ldc 'Request'
      //   47: aload_3
      //   48: invokevirtual toString : ()Ljava/lang/String;
      //   51: invokevirtual d : (Ljava/lang/String;Ljava/lang/String;)V
      //   54: getstatic ca/albertahealthservices/contacttracing/logging/WFLog.Companion : Lca/albertahealthservices/contacttracing/logging/WFLog$Companion;
      //   57: astore_3
      //   58: new java/lang/StringBuilder
      //   61: dup
      //   62: invokespecial <init> : ()V
      //   65: astore_2
      //   66: aload_2
      //   67: ldc 'Request.onFailure url='
      //   69: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   72: pop
      //   73: aload_2
      //   74: aload_0
      //   75: getfield $request : Lcom/worklight/wlclient/api/WLResourceRequest;
      //   78: invokevirtual getUrl : ()Ljava/net/URL;
      //   81: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   84: pop
      //   85: aload_2
      //   86: ldc ' -  response='
      //   88: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   91: pop
      //   92: aload_2
      //   93: aload_1
      //   94: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   97: pop
      //   98: aload_3
      //   99: aload_2
      //   100: invokevirtual toString : ()Ljava/lang/String;
      //   103: invokevirtual logError : (Ljava/lang/String;)V
      //   106: aload_1
      //   107: ifnull -> 164
      //   110: aload_1
      //   111: invokevirtual getErrorMsg : ()Ljava/lang/String;
      //   114: ifnull -> 164
      //   117: getstatic ca/albertahealthservices/contacttracing/logging/CentralLog.Companion : Lca/albertahealthservices/contacttracing/logging/CentralLog$Companion;
      //   120: astore_3
      //   121: new java/lang/StringBuilder
      //   124: dup
      //   125: invokespecial <init> : ()V
      //   128: astore_2
      //   129: aload_2
      //   130: aload_1
      //   131: invokevirtual getErrorStatusCode : ()Ljava/lang/String;
      //   134: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   137: pop
      //   138: aload_2
      //   139: ldc ' - '
      //   141: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   144: pop
      //   145: aload_2
      //   146: aload_1
      //   147: invokevirtual getErrorMsg : ()Ljava/lang/String;
      //   150: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   153: pop
      //   154: aload_3
      //   155: ldc 'Request'
      //   157: aload_2
      //   158: invokevirtual toString : ()Ljava/lang/String;
      //   161: invokevirtual d : (Ljava/lang/String;Ljava/lang/String;)V
      //   164: aload_0
      //   165: getfield $cont : Lkotlin/coroutines/Continuation;
      //   168: astore #4
      //   170: aconst_null
      //   171: astore #5
      //   173: aload_1
      //   174: ifnull -> 188
      //   177: aload_1
      //   178: invokevirtual getStatus : ()I
      //   181: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   184: astore_3
      //   185: goto -> 190
      //   188: aconst_null
      //   189: astore_3
      //   190: aload_1
      //   191: ifnull -> 202
      //   194: aload_1
      //   195: invokevirtual getResponseText : ()Ljava/lang/String;
      //   198: astore_2
      //   199: goto -> 204
      //   202: aconst_null
      //   203: astore_2
      //   204: aload_1
      //   205: ifnull -> 214
      //   208: aload_1
      //   209: invokevirtual getResponseJSON : ()Lorg/json/JSONObject;
      //   212: astore #5
      //   214: getstatic ca/albertahealthservices/contacttracing/api/ErrorCode.INSTANCE : Lca/albertahealthservices/contacttracing/api/ErrorCode;
      //   217: astore #6
      //   219: getstatic ca/albertahealthservices/contacttracing/TracerApp.Companion : Lca/albertahealthservices/contacttracing/TracerApp$Companion;
      //   222: invokevirtual getAppContext : ()Landroid/content/Context;
      //   225: astore #7
      //   227: ldc ''
      //   229: astore #8
      //   231: aload_1
      //   232: ifnull -> 249
      //   235: aload_1
      //   236: invokevirtual getErrorStatusCode : ()Ljava/lang/String;
      //   239: astore #9
      //   241: aload #9
      //   243: ifnull -> 249
      //   246: goto -> 253
      //   249: ldc ''
      //   251: astore #9
      //   253: aload #6
      //   255: aload #7
      //   257: aload #9
      //   259: invokevirtual getStringForErrorCode : (Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
      //   262: astore #7
      //   264: aload #8
      //   266: astore #9
      //   268: aload_1
      //   269: ifnull -> 288
      //   272: aload_1
      //   273: invokevirtual getErrorStatusCode : ()Ljava/lang/String;
      //   276: astore_1
      //   277: aload #8
      //   279: astore #9
      //   281: aload_1
      //   282: ifnull -> 288
      //   285: aload_1
      //   286: astore #9
      //   288: new ca/albertahealthservices/contacttracing/api/Response
      //   291: dup
      //   292: aload_3
      //   293: aload_2
      //   294: aload #5
      //   296: aload #7
      //   298: aload #9
      //   300: invokespecial <init> : (Ljava/lang/Integer;Ljava/lang/String;Lorg/json/JSONObject;Ljava/lang/String;Ljava/lang/String;)V
      //   303: astore_3
      //   304: getstatic kotlin/Result.Companion : Lkotlin/Result$Companion;
      //   307: astore_1
      //   308: aload #4
      //   310: aload_3
      //   311: invokestatic constructor-impl : (Ljava/lang/Object;)Ljava/lang/Object;
      //   314: invokeinterface resumeWith : (Ljava/lang/Object;)V
      //   319: return
    }
    
    public void onSuccess(WLResponse param1WLResponse) {
      Integer integer;
      CentralLog.Companion companion = CentralLog.Companion;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Request.onSuccess url=");
      stringBuilder.append(this.$request.getUrl());
      stringBuilder.append(" - response=");
      JSONObject jSONObject = null;
      if (param1WLResponse != null) {
        integer = (Integer)param1WLResponse.getResponseJSON();
      } else {
        integer = null;
      } 
      stringBuilder.append(integer);
      companion.d("Request", stringBuilder.toString());
      Continuation continuation = this.$cont;
      if (param1WLResponse != null) {
        integer = Integer.valueOf(param1WLResponse.getStatus());
      } else {
        integer = null;
      } 
      if (param1WLResponse != null) {
        String str = param1WLResponse.getResponseText();
      } else {
        stringBuilder = null;
      } 
      if (param1WLResponse != null)
        jSONObject = param1WLResponse.getResponseJSON(); 
      Response response = new Response(integer, (String)stringBuilder, jSONObject, null, null, 24, null);
      Result.Companion companion1 = Result.Companion;
      continuation.resumeWith(Result.constructor-impl(response));
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/api/Request.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */