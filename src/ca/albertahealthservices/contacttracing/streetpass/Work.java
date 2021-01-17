package ca.albertahealthservices.contacttracing.streetpass;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.os.Build;
import ca.albertahealthservices.contacttracing.logging.CentralLog;
import com.google.gson.Gson;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty;

@Metadata(bv = {1, 0, 3}, d1 = {"\000l\n\002\030\002\n\002\020\017\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\002\b\r\n\002\020\013\n\002\b\005\n\002\030\002\n\002\b\005\n\002\020\t\n\002\b\013\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\004\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\004\030\0002\b\022\004\022\0020\0000\001:\003ABCB\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007¢\006\002\020\bJ\021\0205\032\002062\006\0207\032\0020\000H\002J\006\0208\032\0020\032J\006\0209\032\0020\032J\032\020:\032\0020;2\006\020<\032\0020=2\n\020>\032\0060?R\0020@R\016\020\t\032\0020\nXD¢\006\002\n\000R\036\020\013\032\0060\fR\0020\000X\016¢\006\016\n\000\032\004\b\r\020\016\"\004\b\017\020\020R\032\020\004\032\0020\005X\016¢\006\016\n\000\032\004\b\021\020\022\"\004\b\023\020\024R\032\020\002\032\0020\003X\016¢\006\016\n\000\032\004\b\025\020\026\"\004\b\027\020\030R\032\020\031\032\0020\032X\016¢\006\016\n\000\032\004\b\033\020\034\"\004\b\035\020\036R\034\020\037\032\004\030\0010 X\016¢\006\016\n\000\032\004\b!\020\"\"\004\b#\020$R\016\020\006\032\0020\007X\004¢\006\002\n\000R+\020'\032\0020&2\006\020%\032\0020&8F@FX\002¢\006\022\n\004\b,\020-\032\004\b(\020)\"\004\b*\020+R\032\020.\032\0020&X\016¢\006\016\n\000\032\004\b/\020)\"\004\b0\020+R\021\0201\032\00202¢\006\b\n\000\032\004\b3\0204¨\006D"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/Work;", "", "device", "Landroid/bluetooth/BluetoothDevice;", "connectable", "Lca/albertahealthservices/contacttracing/streetpass/ConnectablePeripheral;", "onWorkTimeoutListener", "Lca/albertahealthservices/contacttracing/streetpass/Work$OnWorkTimeoutListener;", "(Landroid/bluetooth/BluetoothDevice;Lca/albertahealthservices/contacttracing/streetpass/ConnectablePeripheral;Lca/albertahealthservices/contacttracing/streetpass/Work$OnWorkTimeoutListener;)V", "TAG", "", "checklist", "Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;", "getChecklist", "()Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;", "setChecklist", "(Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;)V", "getConnectable", "()Lca/albertahealthservices/contacttracing/streetpass/ConnectablePeripheral;", "setConnectable", "(Lca/albertahealthservices/contacttracing/streetpass/ConnectablePeripheral;)V", "getDevice", "()Landroid/bluetooth/BluetoothDevice;", "setDevice", "(Landroid/bluetooth/BluetoothDevice;)V", "finished", "", "getFinished", "()Z", "setFinished", "(Z)V", "gatt", "Landroid/bluetooth/BluetoothGatt;", "getGatt", "()Landroid/bluetooth/BluetoothGatt;", "setGatt", "(Landroid/bluetooth/BluetoothGatt;)V", "<set-?>", "", "timeStamp", "getTimeStamp", "()J", "setTimeStamp", "(J)V", "timeStamp$delegate", "Lkotlin/properties/ReadWriteProperty;", "timeout", "getTimeout", "setTimeout", "timeoutRunnable", "Ljava/lang/Runnable;", "getTimeoutRunnable", "()Ljava/lang/Runnable;", "compareTo", "", "other", "isCriticalsCompleted", "isSafelyCompleted", "startWork", "", "context", "Landroid/content/Context;", "gattCallback", "Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker$CentralGattCallback;", "Lca/albertahealthservices/contacttracing/streetpass/StreetPassWorker;", "Check", "OnWorkTimeoutListener", "WorkCheckList", "app_release"}, k = 1, mv = {1, 1, 16})
public final class Work implements Comparable<Work> {
  private final String TAG;
  
  private WorkCheckList checklist;
  
  private ConnectablePeripheral connectable;
  
  private BluetoothDevice device;
  
  private boolean finished;
  
  private BluetoothGatt gatt;
  
  private final OnWorkTimeoutListener onWorkTimeoutListener;
  
  private final ReadWriteProperty timeStamp$delegate;
  
  private long timeout;
  
  private final Runnable timeoutRunnable;
  
  public Work(BluetoothDevice paramBluetoothDevice, ConnectablePeripheral paramConnectablePeripheral, OnWorkTimeoutListener paramOnWorkTimeoutListener) {
    this.device = paramBluetoothDevice;
    this.connectable = paramConnectablePeripheral;
    this.onWorkTimeoutListener = paramOnWorkTimeoutListener;
    this.timeStamp$delegate = Delegates.INSTANCE.notNull();
    this.checklist = new WorkCheckList();
    this.TAG = "Work";
    this.timeoutRunnable = new Work$timeoutRunnable$1();
    setTimeStamp(System.currentTimeMillis());
  }
  
  public int compareTo(Work paramWork) {
    Intrinsics.checkParameterIsNotNull(paramWork, "other");
    return getTimeStamp() cmp paramWork.getTimeStamp();
  }
  
  public final WorkCheckList getChecklist() {
    return this.checklist;
  }
  
  public final ConnectablePeripheral getConnectable() {
    return this.connectable;
  }
  
  public final BluetoothDevice getDevice() {
    return this.device;
  }
  
  public final boolean getFinished() {
    return this.finished;
  }
  
  public final BluetoothGatt getGatt() {
    return this.gatt;
  }
  
  public final long getTimeStamp() {
    return ((Number)this.timeStamp$delegate.getValue(this, $$delegatedProperties[0])).longValue();
  }
  
  public final long getTimeout() {
    return this.timeout;
  }
  
  public final Runnable getTimeoutRunnable() {
    return this.timeoutRunnable;
  }
  
  public final boolean isCriticalsCompleted() {
    boolean bool;
    if ((this.checklist.getConnected().getStatus() && this.checklist.getReadCharacteristic().getStatus() && this.checklist.getWriteCharacteristic().getStatus()) || this.checklist.getSkipped().getStatus()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final boolean isSafelyCompleted() {
    boolean bool;
    if ((this.checklist.getConnected().getStatus() && this.checklist.getReadCharacteristic().getStatus() && this.checklist.getWriteCharacteristic().getStatus() && this.checklist.getDisconnected().getStatus()) || this.checklist.getSkipped().getStatus()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final void setChecklist(WorkCheckList paramWorkCheckList) {
    Intrinsics.checkParameterIsNotNull(paramWorkCheckList, "<set-?>");
    this.checklist = paramWorkCheckList;
  }
  
  public final void setConnectable(ConnectablePeripheral paramConnectablePeripheral) {
    Intrinsics.checkParameterIsNotNull(paramConnectablePeripheral, "<set-?>");
    this.connectable = paramConnectablePeripheral;
  }
  
  public final void setDevice(BluetoothDevice paramBluetoothDevice) {
    Intrinsics.checkParameterIsNotNull(paramBluetoothDevice, "<set-?>");
    this.device = paramBluetoothDevice;
  }
  
  public final void setFinished(boolean paramBoolean) {
    this.finished = paramBoolean;
  }
  
  public final void setGatt(BluetoothGatt paramBluetoothGatt) {
    this.gatt = paramBluetoothGatt;
  }
  
  public final void setTimeStamp(long paramLong) {
    this.timeStamp$delegate.setValue(this, $$delegatedProperties[0], Long.valueOf(paramLong));
  }
  
  public final void setTimeout(long paramLong) {
    this.timeout = paramLong;
  }
  
  public final void startWork(Context paramContext, StreetPassWorker.CentralGattCallback paramCentralGattCallback) {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramCentralGattCallback, "gattCallback");
    if (Build.VERSION.SDK_INT >= 23) {
      this.gatt = this.device.connectGatt(paramContext, false, paramCentralGattCallback, 2);
    } else {
      this.gatt = this.device.connectGatt(paramContext, false, paramCentralGattCallback);
    } 
    if (this.gatt == null) {
      CentralLog.Companion companion = CentralLog.Companion;
      String str = this.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to connect to ");
      stringBuilder.append(this.device.getAddress());
      companion.e(str, stringBuilder.toString());
    } 
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\002\b\005\n\002\020\t\n\002\b\005\b\004\030\0002\0020\001B\005¢\006\002\020\002R\032\020\003\032\0020\004X\016¢\006\016\n\000\032\004\b\005\020\006\"\004\b\007\020\bR\032\020\t\032\0020\nX\016¢\006\016\n\000\032\004\b\013\020\f\"\004\b\r\020\016¨\006\017"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/Work$Check;", "", "(Lca/albertahealthservices/contacttracing/streetpass/Work;)V", "status", "", "getStatus", "()Z", "setStatus", "(Z)V", "timePerformed", "", "getTimePerformed", "()J", "setTimePerformed", "(J)V", "app_release"}, k = 1, mv = {1, 1, 16})
  public final class Check {
    private boolean status;
    
    private long timePerformed;
    
    public final boolean getStatus() {
      return this.status;
    }
    
    public final long getTimePerformed() {
      return this.timePerformed;
    }
    
    public final void setStatus(boolean param1Boolean) {
      this.status = param1Boolean;
    }
    
    public final void setTimePerformed(long param1Long) {
      this.timePerformed = param1Long;
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\026\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\000\bf\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H&¨\006\006"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/Work$OnWorkTimeoutListener;", "", "onWorkTimeout", "", "work", "Lca/albertahealthservices/contacttracing/streetpass/Work;", "app_release"}, k = 1, mv = {1, 1, 16})
  public static interface OnWorkTimeoutListener {
    void onWorkTimeout(Work param1Work);
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\036\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\027\n\002\020\016\n\000\b\004\030\0002\0020\001B\005¢\006\002\020\002J\b\020\034\032\0020\035H\026R\036\020\003\032\0060\004R\0020\005X\016¢\006\016\n\000\032\004\b\006\020\007\"\004\b\b\020\tR\036\020\n\032\0060\004R\0020\005X\016¢\006\016\n\000\032\004\b\013\020\007\"\004\b\f\020\tR\036\020\r\032\0060\004R\0020\005X\016¢\006\016\n\000\032\004\b\016\020\007\"\004\b\017\020\tR\036\020\020\032\0060\004R\0020\005X\016¢\006\016\n\000\032\004\b\021\020\007\"\004\b\022\020\tR\036\020\023\032\0060\004R\0020\005X\016¢\006\016\n\000\032\004\b\024\020\007\"\004\b\025\020\tR\036\020\026\032\0060\004R\0020\005X\016¢\006\016\n\000\032\004\b\027\020\007\"\004\b\030\020\tR\036\020\031\032\0060\004R\0020\005X\016¢\006\016\n\000\032\004\b\032\020\007\"\004\b\033\020\t¨\006\036"}, d2 = {"Lca/albertahealthservices/contacttracing/streetpass/Work$WorkCheckList;", "", "(Lca/albertahealthservices/contacttracing/streetpass/Work;)V", "connected", "Lca/albertahealthservices/contacttracing/streetpass/Work$Check;", "Lca/albertahealthservices/contacttracing/streetpass/Work;", "getConnected", "()Lca/albertahealthservices/contacttracing/streetpass/Work$Check;", "setConnected", "(Lca/albertahealthservices/contacttracing/streetpass/Work$Check;)V", "disconnected", "getDisconnected", "setDisconnected", "mtuChanged", "getMtuChanged", "setMtuChanged", "readCharacteristic", "getReadCharacteristic", "setReadCharacteristic", "skipped", "getSkipped", "setSkipped", "started", "getStarted", "setStarted", "writeCharacteristic", "getWriteCharacteristic", "setWriteCharacteristic", "toString", "", "app_release"}, k = 1, mv = {1, 1, 16})
  public final class WorkCheckList {
    private Work.Check connected = new Work.Check();
    
    private Work.Check disconnected = new Work.Check();
    
    private Work.Check mtuChanged = new Work.Check();
    
    private Work.Check readCharacteristic = new Work.Check();
    
    private Work.Check skipped = new Work.Check();
    
    private Work.Check started = new Work.Check();
    
    private Work.Check writeCharacteristic = new Work.Check();
    
    public final Work.Check getConnected() {
      return this.connected;
    }
    
    public final Work.Check getDisconnected() {
      return this.disconnected;
    }
    
    public final Work.Check getMtuChanged() {
      return this.mtuChanged;
    }
    
    public final Work.Check getReadCharacteristic() {
      return this.readCharacteristic;
    }
    
    public final Work.Check getSkipped() {
      return this.skipped;
    }
    
    public final Work.Check getStarted() {
      return this.started;
    }
    
    public final Work.Check getWriteCharacteristic() {
      return this.writeCharacteristic;
    }
    
    public final void setConnected(Work.Check param1Check) {
      Intrinsics.checkParameterIsNotNull(param1Check, "<set-?>");
      this.connected = param1Check;
    }
    
    public final void setDisconnected(Work.Check param1Check) {
      Intrinsics.checkParameterIsNotNull(param1Check, "<set-?>");
      this.disconnected = param1Check;
    }
    
    public final void setMtuChanged(Work.Check param1Check) {
      Intrinsics.checkParameterIsNotNull(param1Check, "<set-?>");
      this.mtuChanged = param1Check;
    }
    
    public final void setReadCharacteristic(Work.Check param1Check) {
      Intrinsics.checkParameterIsNotNull(param1Check, "<set-?>");
      this.readCharacteristic = param1Check;
    }
    
    public final void setSkipped(Work.Check param1Check) {
      Intrinsics.checkParameterIsNotNull(param1Check, "<set-?>");
      this.skipped = param1Check;
    }
    
    public final void setStarted(Work.Check param1Check) {
      Intrinsics.checkParameterIsNotNull(param1Check, "<set-?>");
      this.started = param1Check;
    }
    
    public final void setWriteCharacteristic(Work.Check param1Check) {
      Intrinsics.checkParameterIsNotNull(param1Check, "<set-?>");
      this.writeCharacteristic = param1Check;
    }
    
    public String toString() {
      String str = (new Gson()).toJson(this);
      Intrinsics.checkExpressionValueIsNotNull(str, "Gson().toJson(this)");
      return str;
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\b\n\000\n\002\020\002\n\000\020\000\032\0020\001H\n¢\006\002\b\002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 1, 16})
  static final class Work$timeoutRunnable$1 implements Runnable {
    public final void run() {
      Work.this.onWorkTimeoutListener.onWorkTimeout(Work.this);
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/Work.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */