package ca.albertahealthservices.contacttracing.permissions;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import ca.albertahealthservices.contacttracing.Utils;
import java.util.Arrays;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

@Metadata(bv = {1, 0, 3}, d1 = {"\0002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\020\021\n\002\020\016\n\000\n\002\020\025\n\002\b\004\030\000 \0212\0020\001:\001\021B\005¢\006\002\020\002J\022\020\003\032\0020\0042\b\020\005\032\004\030\0010\006H\024J+\020\007\032\0020\0042\006\020\b\032\0020\t2\f\020\n\032\b\022\004\022\0020\f0\0132\006\020\r\032\0020\016H\026¢\006\002\020\017J\b\020\020\032\0020\004H\003¨\006\022"}, d2 = {"Lca/albertahealthservices/contacttracing/permissions/RequestFileWritePermission;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "requestWritePermissionAndExecute", "Companion", "app_release"}, k = 1, mv = {1, 1, 16})
public final class RequestFileWritePermission extends AppCompatActivity {
  public static final Companion Companion = new Companion(null);
  
  private static final int RC_FILE_WRITE = 743;
  
  private HashMap _$_findViewCache;
  
  @AfterPermissionGranted(743)
  private final void requestWritePermissionAndExecute() {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = "android.permission.WRITE_EXTERNAL_STORAGE";
    Context context = (Context)this;
    if (EasyPermissions.hasPermissions(context, Arrays.<String>copyOf(arrayOfString, 1))) {
      Utils.INSTANCE.startBluetoothMonitoringService(context);
      finish();
    } else {
      EasyPermissions.requestPermissions((Activity)this, getString(2131820706), 743, Arrays.<String>copyOf(arrayOfString, 1));
    } 
  }
  
  public void _$_clearFindViewByIdCache() {
    HashMap hashMap = this._$_findViewCache;
    if (hashMap != null)
      hashMap.clear(); 
  }
  
  public View _$_findCachedViewById(int paramInt) {
    if (this._$_findViewCache == null)
      this._$_findViewCache = new HashMap<>(); 
    View view1 = (View)this._$_findViewCache.get(Integer.valueOf(paramInt));
    View view2 = view1;
    if (view1 == null) {
      view2 = findViewById(paramInt);
      this._$_findViewCache.put(Integer.valueOf(paramInt), view2);
    } 
    return view2;
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    requestWritePermissionAndExecute();
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    Intrinsics.checkParameterIsNotNull(paramArrayOfString, "permissions");
    Intrinsics.checkParameterIsNotNull(paramArrayOfint, "grantResults");
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
    EasyPermissions.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint, new Object[] { this });
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000¨\006\005"}, d2 = {"Lca/albertahealthservices/contacttracing/permissions/RequestFileWritePermission$Companion;", "", "()V", "RC_FILE_WRITE", "", "app_release"}, k = 1, mv = {1, 1, 16})
  public static final class Companion {
    private Companion() {}
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/permissions/RequestFileWritePermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */