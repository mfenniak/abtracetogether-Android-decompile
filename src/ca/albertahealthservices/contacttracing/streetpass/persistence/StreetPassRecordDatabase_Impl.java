package ca.albertahealthservices.contacttracing.streetpass.persistence;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import ca.albertahealthservices.contacttracing.status.persistence.StatusRecordDao;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public final class StreetPassRecordDatabase_Impl extends StreetPassRecordDatabase {
  private volatile StatusRecordDao _statusRecordDao;
  
  private volatile StreetPassRecordDao _streetPassRecordDao;
  
  public void clearAllTables() {
    assertNotMainThread();
    SupportSQLiteDatabase supportSQLiteDatabase = getOpenHelper().getWritableDatabase();
    try {
      beginTransaction();
      supportSQLiteDatabase.execSQL("DELETE FROM `record_table`");
      supportSQLiteDatabase.execSQL("DELETE FROM `status_table`");
      setTransactionSuccessful();
      return;
    } finally {
      endTransaction();
      supportSQLiteDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!supportSQLiteDatabase.inTransaction())
        supportSQLiteDatabase.execSQL("VACUUM"); 
    } 
  }
  
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, new HashMap<>(0), new HashMap<>(0), new String[] { "record_table", "status_table" });
  }
  
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration paramDatabaseConfiguration) {
    RoomOpenHelper roomOpenHelper = new RoomOpenHelper(paramDatabaseConfiguration, new RoomOpenHelper.Delegate(1) {
          public void createAllTables(SupportSQLiteDatabase param1SupportSQLiteDatabase) {
            param1SupportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `record_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `v` INTEGER NOT NULL, `msg` TEXT NOT NULL, `org` TEXT NOT NULL, `modelP` TEXT NOT NULL, `modelC` TEXT NOT NULL, `rssi` INTEGER NOT NULL, `txPower` INTEGER)");
            param1SupportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `status_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `msg` TEXT NOT NULL)");
            param1SupportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
            param1SupportSQLiteDatabase.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '9a95fc8ad88c160bf76c0ba4747db316')");
          }
          
          public void dropAllTables(SupportSQLiteDatabase param1SupportSQLiteDatabase) {
            param1SupportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `record_table`");
            param1SupportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `status_table`");
            if (StreetPassRecordDatabase_Impl.this.mCallbacks != null) {
              byte b = 0;
              int i = StreetPassRecordDatabase_Impl.this.mCallbacks.size();
              while (b < i) {
                ((RoomDatabase.Callback)StreetPassRecordDatabase_Impl.this.mCallbacks.get(b)).onDestructiveMigration(param1SupportSQLiteDatabase);
                b++;
              } 
            } 
          }
          
          protected void onCreate(SupportSQLiteDatabase param1SupportSQLiteDatabase) {
            if (StreetPassRecordDatabase_Impl.this.mCallbacks != null) {
              byte b = 0;
              int i = StreetPassRecordDatabase_Impl.this.mCallbacks.size();
              while (b < i) {
                ((RoomDatabase.Callback)StreetPassRecordDatabase_Impl.this.mCallbacks.get(b)).onCreate(param1SupportSQLiteDatabase);
                b++;
              } 
            } 
          }
          
          public void onOpen(SupportSQLiteDatabase param1SupportSQLiteDatabase) {
            StreetPassRecordDatabase_Impl.access$602(StreetPassRecordDatabase_Impl.this, param1SupportSQLiteDatabase);
            StreetPassRecordDatabase_Impl.this.internalInitInvalidationTracker(param1SupportSQLiteDatabase);
            if (StreetPassRecordDatabase_Impl.this.mCallbacks != null) {
              byte b = 0;
              int i = StreetPassRecordDatabase_Impl.this.mCallbacks.size();
              while (b < i) {
                ((RoomDatabase.Callback)StreetPassRecordDatabase_Impl.this.mCallbacks.get(b)).onOpen(param1SupportSQLiteDatabase);
                b++;
              } 
            } 
          }
          
          public void onPostMigrate(SupportSQLiteDatabase param1SupportSQLiteDatabase) {}
          
          public void onPreMigrate(SupportSQLiteDatabase param1SupportSQLiteDatabase) {
            DBUtil.dropFtsSyncTriggers(param1SupportSQLiteDatabase);
          }
          
          protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase param1SupportSQLiteDatabase) {
            StringBuilder stringBuilder;
            HashMap<Object, Object> hashMap2 = new HashMap<>(9);
            hashMap2.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
            hashMap2.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, 1));
            hashMap2.put("v", new TableInfo.Column("v", "INTEGER", true, 0, null, 1));
            hashMap2.put("msg", new TableInfo.Column("msg", "TEXT", true, 0, null, 1));
            hashMap2.put("org", new TableInfo.Column("org", "TEXT", true, 0, null, 1));
            hashMap2.put("modelP", new TableInfo.Column("modelP", "TEXT", true, 0, null, 1));
            hashMap2.put("modelC", new TableInfo.Column("modelC", "TEXT", true, 0, null, 1));
            hashMap2.put("rssi", new TableInfo.Column("rssi", "INTEGER", true, 0, null, 1));
            hashMap2.put("txPower", new TableInfo.Column("txPower", "INTEGER", false, 0, null, 1));
            TableInfo tableInfo4 = new TableInfo("record_table", hashMap2, new HashSet(0), new HashSet(0));
            TableInfo tableInfo3 = TableInfo.read(param1SupportSQLiteDatabase, "record_table");
            if (!tableInfo4.equals(tableInfo3)) {
              stringBuilder = new StringBuilder();
              stringBuilder.append("record_table(ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecord).\n Expected:\n");
              stringBuilder.append(tableInfo4);
              stringBuilder.append("\n Found:\n");
              stringBuilder.append(tableInfo3);
              return new RoomOpenHelper.ValidationResult(false, stringBuilder.toString());
            } 
            HashMap<Object, Object> hashMap1 = new HashMap<>(3);
            hashMap1.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
            hashMap1.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, 1));
            hashMap1.put("msg", new TableInfo.Column("msg", "TEXT", true, 0, null, 1));
            TableInfo tableInfo2 = new TableInfo("status_table", hashMap1, new HashSet(0), new HashSet(0));
            TableInfo tableInfo1 = TableInfo.read((SupportSQLiteDatabase)stringBuilder, "status_table");
            if (!tableInfo2.equals(tableInfo1)) {
              StringBuilder stringBuilder1 = new StringBuilder();
              stringBuilder1.append("status_table(ca.albertahealthservices.contacttracing.status.persistence.StatusRecord).\n Expected:\n");
              stringBuilder1.append(tableInfo2);
              stringBuilder1.append("\n Found:\n");
              stringBuilder1.append(tableInfo1);
              return new RoomOpenHelper.ValidationResult(false, stringBuilder1.toString());
            } 
            return new RoomOpenHelper.ValidationResult(true, null);
          }
        }"9a95fc8ad88c160bf76c0ba4747db316", "336ef522c4c1738f91b0cd0ef01687c8");
    SupportSQLiteOpenHelper.Configuration configuration = SupportSQLiteOpenHelper.Configuration.builder(paramDatabaseConfiguration.context).name(paramDatabaseConfiguration.name).callback((SupportSQLiteOpenHelper.Callback)roomOpenHelper).build();
    return paramDatabaseConfiguration.sqliteOpenHelperFactory.create(configuration);
  }
  
  public StreetPassRecordDao recordDao() {
    // Byte code:
    //   0: aload_0
    //   1: getfield _streetPassRecordDao : Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDao;
    //   4: ifnull -> 12
    //   7: aload_0
    //   8: getfield _streetPassRecordDao : Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDao;
    //   11: areturn
    //   12: aload_0
    //   13: monitorenter
    //   14: aload_0
    //   15: getfield _streetPassRecordDao : Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDao;
    //   18: ifnonnull -> 35
    //   21: new ca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDao_Impl
    //   24: astore_1
    //   25: aload_1
    //   26: aload_0
    //   27: invokespecial <init> : (Landroidx/room/RoomDatabase;)V
    //   30: aload_0
    //   31: aload_1
    //   32: putfield _streetPassRecordDao : Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDao;
    //   35: aload_0
    //   36: getfield _streetPassRecordDao : Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDao;
    //   39: astore_1
    //   40: aload_0
    //   41: monitorexit
    //   42: aload_1
    //   43: areturn
    //   44: astore_1
    //   45: aload_0
    //   46: monitorexit
    //   47: aload_1
    //   48: athrow
    // Exception table:
    //   from	to	target	type
    //   14	35	44	finally
    //   35	42	44	finally
    //   45	47	44	finally
  }
  
  public StatusRecordDao statusDao() {
    // Byte code:
    //   0: aload_0
    //   1: getfield _statusRecordDao : Lca/albertahealthservices/contacttracing/status/persistence/StatusRecordDao;
    //   4: ifnull -> 12
    //   7: aload_0
    //   8: getfield _statusRecordDao : Lca/albertahealthservices/contacttracing/status/persistence/StatusRecordDao;
    //   11: areturn
    //   12: aload_0
    //   13: monitorenter
    //   14: aload_0
    //   15: getfield _statusRecordDao : Lca/albertahealthservices/contacttracing/status/persistence/StatusRecordDao;
    //   18: ifnonnull -> 35
    //   21: new ca/albertahealthservices/contacttracing/status/persistence/StatusRecordDao_Impl
    //   24: astore_1
    //   25: aload_1
    //   26: aload_0
    //   27: invokespecial <init> : (Landroidx/room/RoomDatabase;)V
    //   30: aload_0
    //   31: aload_1
    //   32: putfield _statusRecordDao : Lca/albertahealthservices/contacttracing/status/persistence/StatusRecordDao;
    //   35: aload_0
    //   36: getfield _statusRecordDao : Lca/albertahealthservices/contacttracing/status/persistence/StatusRecordDao;
    //   39: astore_1
    //   40: aload_0
    //   41: monitorexit
    //   42: aload_1
    //   43: areturn
    //   44: astore_1
    //   45: aload_0
    //   46: monitorexit
    //   47: aload_1
    //   48: athrow
    // Exception table:
    //   from	to	target	type
    //   14	35	44	finally
    //   35	42	44	finally
    //   45	47	44	finally
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDatabase_Impl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */