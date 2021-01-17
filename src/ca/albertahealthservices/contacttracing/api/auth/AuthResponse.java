package ca.albertahealthservices.contacttracing.api.auth;

import com.worklight.wlclient.auth.AccessToken;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000(\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\f\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\003\b\b\030\0002\0020\001B%\022\b\020\002\032\004\030\0010\003\022\n\b\002\020\004\032\004\030\0010\005\022\b\b\002\020\006\032\0020\005¢\006\002\020\007J\013\020\r\032\004\030\0010\003HÆ\003J\013\020\016\032\004\030\0010\005HÆ\003J\t\020\017\032\0020\005HÆ\003J+\020\020\032\0020\0002\n\b\002\020\002\032\004\030\0010\0032\n\b\002\020\004\032\004\030\0010\0052\b\b\002\020\006\032\0020\005HÆ\001J\023\020\021\032\0020\0222\b\020\023\032\004\030\0010\001HÖ\003J\t\020\024\032\0020\025HÖ\001J\006\020\026\032\0020\022J\t\020\027\032\0020\005HÖ\001R\023\020\002\032\004\030\0010\003¢\006\b\n\000\032\004\b\b\020\tR\021\020\006\032\0020\005¢\006\b\n\000\032\004\b\n\020\013R\023\020\004\032\004\030\0010\005¢\006\b\n\000\032\004\b\f\020\013¨\006\030"}, d2 = {"Lca/albertahealthservices/contacttracing/api/auth/AuthResponse;", "", "accessToken", "Lcom/worklight/wlclient/auth/AccessToken;", "errorCode", "", "error", "(Lcom/worklight/wlclient/auth/AccessToken;Ljava/lang/String;Ljava/lang/String;)V", "getAccessToken", "()Lcom/worklight/wlclient/auth/AccessToken;", "getError", "()Ljava/lang/String;", "getErrorCode", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "isSuccess", "toString", "app_release"}, k = 1, mv = {1, 1, 16})
public final class AuthResponse {
  private final AccessToken accessToken;
  
  private final String error;
  
  private final String errorCode;
  
  public AuthResponse(AccessToken paramAccessToken, String paramString1, String paramString2) {
    this.accessToken = paramAccessToken;
    this.errorCode = paramString1;
    this.error = paramString2;
  }
  
  public final AccessToken component1() {
    return this.accessToken;
  }
  
  public final String component2() {
    return this.errorCode;
  }
  
  public final String component3() {
    return this.error;
  }
  
  public final AuthResponse copy(AccessToken paramAccessToken, String paramString1, String paramString2) {
    Intrinsics.checkParameterIsNotNull(paramString2, "error");
    return new AuthResponse(paramAccessToken, paramString1, paramString2);
  }
  
  public boolean equals(Object paramObject) {
    if (this != paramObject) {
      if (paramObject instanceof AuthResponse) {
        paramObject = paramObject;
        if (Intrinsics.areEqual(this.accessToken, ((AuthResponse)paramObject).accessToken) && Intrinsics.areEqual(this.errorCode, ((AuthResponse)paramObject).errorCode) && Intrinsics.areEqual(this.error, ((AuthResponse)paramObject).error))
          return true; 
      } 
      return false;
    } 
    return true;
  }
  
  public final AccessToken getAccessToken() {
    return this.accessToken;
  }
  
  public final String getError() {
    return this.error;
  }
  
  public final String getErrorCode() {
    return this.errorCode;
  }
  
  public int hashCode() {
    byte b1;
    byte b2;
    AccessToken accessToken = this.accessToken;
    int i = 0;
    if (accessToken != null) {
      b1 = accessToken.hashCode();
    } else {
      b1 = 0;
    } 
    String str = this.errorCode;
    if (str != null) {
      b2 = str.hashCode();
    } else {
      b2 = 0;
    } 
    str = this.error;
    if (str != null)
      i = str.hashCode(); 
    return (b1 * 31 + b2) * 31 + i;
  }
  
  public final boolean isSuccess() {
    boolean bool;
    if (this.errorCode == null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("AuthResponse(accessToken=");
    stringBuilder.append(this.accessToken);
    stringBuilder.append(", errorCode=");
    stringBuilder.append(this.errorCode);
    stringBuilder.append(", error=");
    stringBuilder.append(this.error);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/api/auth/AuthResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */