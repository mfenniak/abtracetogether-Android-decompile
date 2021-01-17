package ca.albertahealthservices.contacttracing.streetpass.persistence;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

public final class StreetPassRecordDao_Impl implements StreetPassRecordDao {
  private final RoomDatabase __db;
  
  private final EntityInsertionAdapter<StreetPassRecord> __insertionAdapterOfStreetPassRecord;
  
  private final SharedSQLiteStatement __preparedStmtOfNukeDb;
  
  private final SharedSQLiteStatement __preparedStmtOfPurgeOldRecords;
  
  public StreetPassRecordDao_Impl(RoomDatabase paramRoomDatabase) {
    this.__db = paramRoomDatabase;
    this.__insertionAdapterOfStreetPassRecord = new EntityInsertionAdapter<StreetPassRecord>(paramRoomDatabase) {
        public void bind(SupportSQLiteStatement param1SupportSQLiteStatement, StreetPassRecord param1StreetPassRecord) {
          param1SupportSQLiteStatement.bindLong(1, param1StreetPassRecord.getId());
          param1SupportSQLiteStatement.bindLong(2, param1StreetPassRecord.getTimestamp());
          param1SupportSQLiteStatement.bindLong(3, param1StreetPassRecord.getV());
          if (param1StreetPassRecord.getMsg() == null) {
            param1SupportSQLiteStatement.bindNull(4);
          } else {
            param1SupportSQLiteStatement.bindString(4, param1StreetPassRecord.getMsg());
          } 
          if (param1StreetPassRecord.getOrg() == null) {
            param1SupportSQLiteStatement.bindNull(5);
          } else {
            param1SupportSQLiteStatement.bindString(5, param1StreetPassRecord.getOrg());
          } 
          if (param1StreetPassRecord.getModelP() == null) {
            param1SupportSQLiteStatement.bindNull(6);
          } else {
            param1SupportSQLiteStatement.bindString(6, param1StreetPassRecord.getModelP());
          } 
          if (param1StreetPassRecord.getModelC() == null) {
            param1SupportSQLiteStatement.bindNull(7);
          } else {
            param1SupportSQLiteStatement.bindString(7, param1StreetPassRecord.getModelC());
          } 
          param1SupportSQLiteStatement.bindLong(8, param1StreetPassRecord.getRssi());
          if (param1StreetPassRecord.getTxPower() == null) {
            param1SupportSQLiteStatement.bindNull(9);
          } else {
            param1SupportSQLiteStatement.bindLong(9, param1StreetPassRecord.getTxPower().intValue());
          } 
        }
        
        public String createQuery() {
          return "INSERT OR IGNORE INTO `record_table` (`id`,`timestamp`,`v`,`msg`,`org`,`modelP`,`modelC`,`rssi`,`txPower`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
        }
      };
    this.__preparedStmtOfNukeDb = new SharedSQLiteStatement(paramRoomDatabase) {
        public String createQuery() {
          return "DELETE FROM record_table";
        }
      };
    this.__preparedStmtOfPurgeOldRecords = new SharedSQLiteStatement(paramRoomDatabase) {
        public String createQuery() {
          return "DELETE FROM record_table WHERE timestamp < ?";
        }
      };
  }
  
  private StreetPassRecord __entityCursorConverter_caAlbertahealthservicesContacttracingStreetpassPersistenceStreetPassRecord(Cursor paramCursor) {
    String str1;
    String str2;
    String str3;
    String str4;
    int i = paramCursor.getColumnIndex("id");
    int j = paramCursor.getColumnIndex("timestamp");
    int k = paramCursor.getColumnIndex("v");
    int m = paramCursor.getColumnIndex("msg");
    int n = paramCursor.getColumnIndex("org");
    int i1 = paramCursor.getColumnIndex("modelP");
    int i2 = paramCursor.getColumnIndex("modelC");
    int i3 = paramCursor.getColumnIndex("rssi");
    int i4 = paramCursor.getColumnIndex("txPower");
    int i5 = 0;
    if (k == -1) {
      k = 0;
    } else {
      k = paramCursor.getInt(k);
    } 
    Integer integer = null;
    if (m == -1) {
      str1 = null;
    } else {
      str1 = paramCursor.getString(m);
    } 
    if (n == -1) {
      str2 = null;
    } else {
      str2 = paramCursor.getString(n);
    } 
    if (i1 == -1) {
      str3 = null;
    } else {
      str3 = paramCursor.getString(i1);
    } 
    if (i2 == -1) {
      str4 = null;
    } else {
      str4 = paramCursor.getString(i2);
    } 
    if (i3 != -1)
      i5 = paramCursor.getInt(i3); 
    if (i4 != -1 && !paramCursor.isNull(i4))
      integer = Integer.valueOf(paramCursor.getInt(i4)); 
    StreetPassRecord streetPassRecord = new StreetPassRecord(k, str1, str2, str3, str4, i5, integer);
    if (i != -1)
      streetPassRecord.setId(paramCursor.getInt(i)); 
    if (j != -1)
      streetPassRecord.setTimestamp(paramCursor.getLong(j)); 
    return streetPassRecord;
  }
  
  public List<StreetPassRecord> getCurrentRecords() {
    RoomSQLiteQuery roomSQLiteQuery = RoomSQLiteQuery.acquire("SELECT * from record_table ORDER BY timestamp ASC", 0);
    this.__db.assertNotSuspendingTransaction();
    Cursor cursor = DBUtil.query(this.__db, (SupportSQLiteQuery)roomSQLiteQuery, false, null);
    try {
      int i = CursorUtil.getColumnIndexOrThrow(cursor, "id");
      int j = CursorUtil.getColumnIndexOrThrow(cursor, "timestamp");
      int k = CursorUtil.getColumnIndexOrThrow(cursor, "v");
      int m = CursorUtil.getColumnIndexOrThrow(cursor, "msg");
      int n = CursorUtil.getColumnIndexOrThrow(cursor, "org");
      int i1 = CursorUtil.getColumnIndexOrThrow(cursor, "modelP");
      int i2 = CursorUtil.getColumnIndexOrThrow(cursor, "modelC");
      int i3 = CursorUtil.getColumnIndexOrThrow(cursor, "rssi");
      int i4 = CursorUtil.getColumnIndexOrThrow(cursor, "txPower");
      ArrayList<StreetPassRecord> arrayList = new ArrayList();
      this(cursor.getCount());
      while (cursor.moveToNext()) {
        Integer integer;
        int i5 = cursor.getInt(k);
        String str1 = cursor.getString(m);
        String str2 = cursor.getString(n);
        String str3 = cursor.getString(i1);
        String str4 = cursor.getString(i2);
        int i6 = cursor.getInt(i3);
        if (cursor.isNull(i4)) {
          integer = null;
        } else {
          integer = Integer.valueOf(cursor.getInt(i4));
        } 
        StreetPassRecord streetPassRecord = new StreetPassRecord();
        this(i5, str1, str2, str3, str4, i6, integer);
        streetPassRecord.setId(cursor.getInt(i));
        streetPassRecord.setTimestamp(cursor.getLong(j));
        arrayList.add(streetPassRecord);
      } 
      return arrayList;
    } finally {
      cursor.close();
      roomSQLiteQuery.release();
    } 
  }
  
  public LiveData<StreetPassRecord> getMostRecentRecord() {
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * from record_table ORDER BY timestamp DESC LIMIT 1", 0);
    InvalidationTracker invalidationTracker = this.__db.getInvalidationTracker();
    Callable<StreetPassRecord> callable = new Callable<StreetPassRecord>() {
        public StreetPassRecord call() throws Exception {
          RoomDatabase roomDatabase = StreetPassRecordDao_Impl.this.__db;
          RoomSQLiteQuery roomSQLiteQuery = _statement;
          null = null;
          Integer integer = null;
          Cursor cursor = DBUtil.query(roomDatabase, (SupportSQLiteQuery)roomSQLiteQuery, false, null);
          try {
            StreetPassRecord streetPassRecord;
            int i = CursorUtil.getColumnIndexOrThrow(cursor, "id");
            int j = CursorUtil.getColumnIndexOrThrow(cursor, "timestamp");
            int k = CursorUtil.getColumnIndexOrThrow(cursor, "v");
            int m = CursorUtil.getColumnIndexOrThrow(cursor, "msg");
            int n = CursorUtil.getColumnIndexOrThrow(cursor, "org");
            int i1 = CursorUtil.getColumnIndexOrThrow(cursor, "modelP");
            int i2 = CursorUtil.getColumnIndexOrThrow(cursor, "modelC");
            int i3 = CursorUtil.getColumnIndexOrThrow(cursor, "rssi");
            int i4 = CursorUtil.getColumnIndexOrThrow(cursor, "txPower");
            if (cursor.moveToFirst()) {
              k = cursor.getInt(k);
              String str2 = cursor.getString(m);
              String str1 = cursor.getString(n);
              String str3 = cursor.getString(i1);
              String str4 = cursor.getString(i2);
              i2 = cursor.getInt(i3);
              if (cursor.isNull(i4)) {
                null = integer;
              } else {
                null = Integer.valueOf(cursor.getInt(i4));
              } 
              StreetPassRecord streetPassRecord1 = new StreetPassRecord();
              this(k, str2, str1, str3, str4, i2, null);
              streetPassRecord1.setId(cursor.getInt(i));
              streetPassRecord1.setTimestamp(cursor.getLong(j));
              streetPassRecord = streetPassRecord1;
            } 
            return streetPassRecord;
          } finally {
            cursor.close();
          } 
        }
        
        protected void finalize() {
          _statement.release();
        }
      };
    return invalidationTracker.createLiveData(new String[] { "record_table" }, false, callable);
  }
  
  public LiveData<List<StreetPassRecord>> getRecords() {
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT * from record_table ORDER BY timestamp ASC", 0);
    InvalidationTracker invalidationTracker = this.__db.getInvalidationTracker();
    Callable<List<StreetPassRecord>> callable = new Callable<List<StreetPassRecord>>() {
        public List<StreetPassRecord> call() throws Exception {
          Cursor cursor = DBUtil.query(StreetPassRecordDao_Impl.this.__db, (SupportSQLiteQuery)_statement, false, null);
          try {
            int i = CursorUtil.getColumnIndexOrThrow(cursor, "id");
            int j = CursorUtil.getColumnIndexOrThrow(cursor, "timestamp");
            int k = CursorUtil.getColumnIndexOrThrow(cursor, "v");
            int m = CursorUtil.getColumnIndexOrThrow(cursor, "msg");
            int n = CursorUtil.getColumnIndexOrThrow(cursor, "org");
            int i1 = CursorUtil.getColumnIndexOrThrow(cursor, "modelP");
            int i2 = CursorUtil.getColumnIndexOrThrow(cursor, "modelC");
            int i3 = CursorUtil.getColumnIndexOrThrow(cursor, "rssi");
            int i4 = CursorUtil.getColumnIndexOrThrow(cursor, "txPower");
            ArrayList<StreetPassRecord> arrayList = new ArrayList();
            this(cursor.getCount());
            while (cursor.moveToNext()) {
              Integer integer;
              int i5 = cursor.getInt(k);
              String str1 = cursor.getString(m);
              String str2 = cursor.getString(n);
              String str3 = cursor.getString(i1);
              String str4 = cursor.getString(i2);
              int i6 = cursor.getInt(i3);
              if (cursor.isNull(i4)) {
                integer = null;
              } else {
                integer = Integer.valueOf(cursor.getInt(i4));
              } 
              StreetPassRecord streetPassRecord = new StreetPassRecord();
              this(i5, str1, str2, str3, str4, i6, integer);
              streetPassRecord.setId(cursor.getInt(i));
              streetPassRecord.setTimestamp(cursor.getLong(j));
              arrayList.add(streetPassRecord);
            } 
            return arrayList;
          } finally {
            cursor.close();
          } 
        }
        
        protected void finalize() {
          _statement.release();
        }
      };
    return invalidationTracker.createLiveData(new String[] { "record_table" }, false, callable);
  }
  
  public List<StreetPassRecord> getRecordsViaQuery(SupportSQLiteQuery paramSupportSQLiteQuery) {
    this.__db.assertNotSuspendingTransaction();
    Cursor cursor = DBUtil.query(this.__db, paramSupportSQLiteQuery, false, null);
    try {
      ArrayList<StreetPassRecord> arrayList = new ArrayList();
      this(cursor.getCount());
      while (cursor.moveToNext())
        arrayList.add(__entityCursorConverter_caAlbertahealthservicesContacttracingStreetpassPersistenceStreetPassRecord(cursor)); 
      return arrayList;
    } finally {
      cursor.close();
    } 
  }
  
  public Object insert(final StreetPassRecord record, Continuation<? super Unit> paramContinuation) {
    return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() {
          public Unit call() throws Exception {
            StreetPassRecordDao_Impl.this.__db.beginTransaction();
            try {
              StreetPassRecordDao_Impl.this.__insertionAdapterOfStreetPassRecord.insert(record);
              StreetPassRecordDao_Impl.this.__db.setTransactionSuccessful();
              return Unit.INSTANCE;
            } finally {
              StreetPassRecordDao_Impl.this.__db.endTransaction();
            } 
          }
        },  paramContinuation);
  }
  
  public void nukeDb() {
    this.__db.assertNotSuspendingTransaction();
    SupportSQLiteStatement supportSQLiteStatement = this.__preparedStmtOfNukeDb.acquire();
    this.__db.beginTransaction();
    try {
      supportSQLiteStatement.executeUpdateDelete();
      this.__db.setTransactionSuccessful();
      return;
    } finally {
      this.__db.endTransaction();
      this.__preparedStmtOfNukeDb.release(supportSQLiteStatement);
    } 
  }
  
  public Object purgeOldRecords(final long before, Continuation<? super Unit> paramContinuation) {
    return CoroutinesRoom.execute(this.__db, true, new Callable<Unit>() {
          public Unit call() throws Exception {
            SupportSQLiteStatement supportSQLiteStatement = StreetPassRecordDao_Impl.this.__preparedStmtOfPurgeOldRecords.acquire();
            supportSQLiteStatement.bindLong(1, before);
            StreetPassRecordDao_Impl.this.__db.beginTransaction();
            try {
              supportSQLiteStatement.executeUpdateDelete();
              StreetPassRecordDao_Impl.this.__db.setTransactionSuccessful();
              return Unit.INSTANCE;
            } finally {
              StreetPassRecordDao_Impl.this.__db.endTransaction();
              StreetPassRecordDao_Impl.this.__preparedStmtOfPurgeOldRecords.release(supportSQLiteStatement);
            } 
          }
        }paramContinuation);
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecordDao_Impl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */