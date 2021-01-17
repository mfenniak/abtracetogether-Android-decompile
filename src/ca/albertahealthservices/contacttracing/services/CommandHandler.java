package ca.albertahealthservices.contacttracing.services;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\000<\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\004\n\002\020\002\n\002\b\003\n\002\030\002\n\000\n\002\020\013\n\002\b\003\n\002\020\t\n\002\b\003\n\002\030\002\n\002\b\003\030\0002\0020\001B\023\022\f\020\002\032\b\022\004\022\0020\0040\003¢\006\002\020\005J\006\020\b\032\0020\tJ\006\020\n\032\0020\tJ\022\020\013\032\0020\t2\b\020\f\032\004\030\0010\rH\026J\006\020\016\032\0020\017J\006\020\020\032\0020\017J\016\020\021\032\0020\t2\006\020\022\032\0020\023J\016\020\024\032\0020\t2\006\020\022\032\0020\023J\016\020\025\032\0020\t2\006\020\026\032\0020\027J\026\020\025\032\0020\t2\006\020\026\032\0020\0272\006\020\030\032\0020\023J\006\020\031\032\0020\tR\027\020\002\032\b\022\004\022\0020\0040\003¢\006\b\n\000\032\004\b\006\020\007¨\006\032"}, d2 = {"Lca/albertahealthservices/contacttracing/services/CommandHandler;", "Landroid/os/Handler;", "service", "Ljava/lang/ref/WeakReference;", "Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService;", "(Ljava/lang/ref/WeakReference;)V", "getService", "()Ljava/lang/ref/WeakReference;", "cancelNextAdvertise", "", "cancelNextScan", "handleMessage", "msg", "Landroid/os/Message;", "hasAdvertiseScheduled", "", "hasScanScheduled", "scheduleNextAdvertise", "timeInMillis", "", "scheduleNextScan", "sendCommandMsg", "cmd", "Lca/albertahealthservices/contacttracing/services/BluetoothMonitoringService$Command;", "delay", "startBluetoothMonitoringService", "app_release"}, k = 1, mv = {1, 1, 16})
public final class CommandHandler extends Handler {
  private final WeakReference<BluetoothMonitoringService> service;
  
  public CommandHandler(WeakReference<BluetoothMonitoringService> paramWeakReference) {
    this.service = paramWeakReference;
  }
  
  public final void cancelNextAdvertise() {
    removeMessages(BluetoothMonitoringService.Command.ACTION_ADVERTISE.getIndex());
  }
  
  public final void cancelNextScan() {
    removeMessages(BluetoothMonitoringService.Command.ACTION_SCAN.getIndex());
  }
  
  public final WeakReference<BluetoothMonitoringService> getService() {
    return this.service;
  }
  
  public void handleMessage(Message paramMessage) {
    if (paramMessage != null) {
      int i = paramMessage.what;
      BluetoothMonitoringService bluetoothMonitoringService = this.service.get();
      if (bluetoothMonitoringService != null)
        bluetoothMonitoringService.runService(BluetoothMonitoringService.Command.Companion.findByValue(i)); 
    } 
  }
  
  public final boolean hasAdvertiseScheduled() {
    return hasMessages(BluetoothMonitoringService.Command.ACTION_ADVERTISE.getIndex());
  }
  
  public final boolean hasScanScheduled() {
    return hasMessages(BluetoothMonitoringService.Command.ACTION_SCAN.getIndex());
  }
  
  public final void scheduleNextAdvertise(long paramLong) {
    cancelNextAdvertise();
    sendCommandMsg(BluetoothMonitoringService.Command.ACTION_ADVERTISE, paramLong);
  }
  
  public final void scheduleNextScan(long paramLong) {
    cancelNextScan();
    sendCommandMsg(BluetoothMonitoringService.Command.ACTION_SCAN, paramLong);
  }
  
  public final void sendCommandMsg(BluetoothMonitoringService.Command paramCommand) {
    Intrinsics.checkParameterIsNotNull(paramCommand, "cmd");
    Message message = obtainMessage(paramCommand.getIndex());
    message.arg1 = paramCommand.getIndex();
    sendMessage(message);
  }
  
  public final void sendCommandMsg(BluetoothMonitoringService.Command paramCommand, long paramLong) {
    Intrinsics.checkParameterIsNotNull(paramCommand, "cmd");
    sendMessageDelayed(Message.obtain(this, paramCommand.getIndex()), paramLong);
  }
  
  public final void startBluetoothMonitoringService() {
    sendCommandMsg(BluetoothMonitoringService.Command.ACTION_START);
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/services/CommandHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */