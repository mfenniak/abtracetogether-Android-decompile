package ca.albertahealthservices.contacttracing.api;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 3}, d1 = {"\000&\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\016\n\000\n\002\030\002\n\002\b\024\n\002\020\013\n\002\b\005\b\b\030\0002\0020\001B7\022\b\020\002\032\004\030\0010\003\022\b\020\004\032\004\030\0010\005\022\b\020\006\032\004\030\0010\007\022\b\b\002\020\b\032\0020\005\022\b\b\002\020\t\032\0020\005¢\006\002\020\nJ\020\020\024\032\004\030\0010\003HÆ\003¢\006\002\020\021J\013\020\025\032\004\030\0010\005HÆ\003J\013\020\026\032\004\030\0010\007HÆ\003J\t\020\027\032\0020\005HÆ\003J\t\020\030\032\0020\005HÆ\003JF\020\031\032\0020\0002\n\b\002\020\002\032\004\030\0010\0032\n\b\002\020\004\032\004\030\0010\0052\n\b\002\020\006\032\004\030\0010\0072\b\b\002\020\b\032\0020\0052\b\b\002\020\t\032\0020\005HÆ\001¢\006\002\020\032J\023\020\033\032\0020\0342\b\020\035\032\004\030\0010\001HÖ\003J\t\020\036\032\0020\003HÖ\001J\006\020\037\032\0020\034J\t\020 \032\0020\005HÖ\001R\023\020\006\032\004\030\0010\007¢\006\b\n\000\032\004\b\013\020\fR\021\020\b\032\0020\005¢\006\b\n\000\032\004\b\r\020\016R\021\020\t\032\0020\005¢\006\b\n\000\032\004\b\017\020\016R\025\020\002\032\004\030\0010\003¢\006\n\n\002\020\022\032\004\b\020\020\021R\023\020\004\032\004\030\0010\005¢\006\b\n\000\032\004\b\023\020\016¨\006!"}, d2 = {"Lca/albertahealthservices/contacttracing/api/Response;", "", "status", "", "text", "", "data", "Lorg/json/JSONObject;", "error", "errorCode", "(Ljava/lang/Integer;Ljava/lang/String;Lorg/json/JSONObject;Ljava/lang/String;Ljava/lang/String;)V", "getData", "()Lorg/json/JSONObject;", "getError", "()Ljava/lang/String;", "getErrorCode", "getStatus", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getText", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Lorg/json/JSONObject;Ljava/lang/String;Ljava/lang/String;)Lca/albertahealthservices/contacttracing/api/Response;", "equals", "", "other", "hashCode", "isSuccess", "toString", "app_release"}, k = 1, mv = {1, 1, 16})
public final class Response {
  private final JSONObject data;
  
  private final String error;
  
  private final String errorCode;
  
  private final Integer status;
  
  private final String text;
  
  public Response(Integer paramInteger, String paramString1, JSONObject paramJSONObject, String paramString2, String paramString3) {
    this.status = paramInteger;
    this.text = paramString1;
    this.data = paramJSONObject;
    this.error = paramString2;
    this.errorCode = paramString3;
  }
  
  public final Integer component1() {
    return this.status;
  }
  
  public final String component2() {
    return this.text;
  }
  
  public final JSONObject component3() {
    return this.data;
  }
  
  public final String component4() {
    return this.error;
  }
  
  public final String component5() {
    return this.errorCode;
  }
  
  public final Response copy(Integer paramInteger, String paramString1, JSONObject paramJSONObject, String paramString2, String paramString3) {
    Intrinsics.checkParameterIsNotNull(paramString2, "error");
    Intrinsics.checkParameterIsNotNull(paramString3, "errorCode");
    return new Response(paramInteger, paramString1, paramJSONObject, paramString2, paramString3);
  }
  
  public boolean equals(Object paramObject) {
    if (this != paramObject) {
      if (paramObject instanceof Response) {
        paramObject = paramObject;
        if (Intrinsics.areEqual(this.status, ((Response)paramObject).status) && Intrinsics.areEqual(this.text, ((Response)paramObject).text) && Intrinsics.areEqual(this.data, ((Response)paramObject).data) && Intrinsics.areEqual(this.error, ((Response)paramObject).error) && Intrinsics.areEqual(this.errorCode, ((Response)paramObject).errorCode))
          return true; 
      } 
      return false;
    } 
    return true;
  }
  
  public final JSONObject getData() {
    return this.data;
  }
  
  public final String getError() {
    return this.error;
  }
  
  public final String getErrorCode() {
    return this.errorCode;
  }
  
  public final Integer getStatus() {
    return this.status;
  }
  
  public final String getText() {
    return this.text;
  }
  
  public int hashCode() {
    byte b1;
    byte b2;
    byte b3;
    byte b4;
    Integer integer = this.status;
    int i = 0;
    if (integer != null) {
      b1 = integer.hashCode();
    } else {
      b1 = 0;
    } 
    String str2 = this.text;
    if (str2 != null) {
      b2 = str2.hashCode();
    } else {
      b2 = 0;
    } 
    JSONObject jSONObject = this.data;
    if (jSONObject != null) {
      b3 = jSONObject.hashCode();
    } else {
      b3 = 0;
    } 
    String str1 = this.error;
    if (str1 != null) {
      b4 = str1.hashCode();
    } else {
      b4 = 0;
    } 
    str1 = this.errorCode;
    if (str1 != null)
      i = str1.hashCode(); 
    return (((b1 * 31 + b2) * 31 + b3) * 31 + b4) * 31 + i;
  }
  
  public final boolean isSuccess() {
    boolean bool;
    if (Intrinsics.areEqual(this.error, "") && this.status != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Response(status=");
    stringBuilder.append(this.status);
    stringBuilder.append(", text=");
    stringBuilder.append(this.text);
    stringBuilder.append(", data=");
    stringBuilder.append(this.data);
    stringBuilder.append(", error=");
    stringBuilder.append(this.error);
    stringBuilder.append(", errorCode=");
    stringBuilder.append(this.errorCode);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/api/Response.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */