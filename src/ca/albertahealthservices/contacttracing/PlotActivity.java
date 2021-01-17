package ca.albertahealthservices.contacttracing;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import ca.albertahealthservices.contacttracing.fragment.ExportData;
import ca.albertahealthservices.contacttracing.status.persistence.StatusRecord;
import ca.albertahealthservices.contacttracing.status.persistence.StatusRecordStorage;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecord;
import ca.albertahealthservices.contacttracing.streetpass.persistence.StreetPassRecordStorage;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;

@Metadata(bv = {1, 0, 3}, d1 = {"\000\036\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020\002\n\000\n\002\030\002\n\000\030\0002\0020\001B\005¢\006\002\020\002J\022\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\025R\016\020\003\032\0020\004X\016¢\006\002\n\000¨\006\t"}, d2 = {"Lca/albertahealthservices/contacttracing/PlotActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "TAG", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_release"}, k = 1, mv = {1, 1, 16})
public final class PlotActivity extends AppCompatActivity {
  private String TAG = "PlotActivity";
  
  private HashMap _$_findViewCache;
  
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
    setContentView(2131492894);
    WebView webView = (WebView)_$_findCachedViewById(R.id.webView);
    Intrinsics.checkExpressionValueIsNotNull(webView, "webView");
    webView.setWebViewClient(new WebViewClient());
    webView = (WebView)_$_findCachedViewById(R.id.webView);
    Intrinsics.checkExpressionValueIsNotNull(webView, "webView");
    WebSettings webSettings = webView.getSettings();
    Intrinsics.checkExpressionValueIsNotNull(webSettings, "webView.settings");
    webSettings.setJavaScriptEnabled(true);
    int i = getIntent().getIntExtra("time_period", 1);
    Observable observable1 = Observable.create(new PlotActivity$onCreate$observableStreetRecords$1());
    Intrinsics.checkExpressionValueIsNotNull(observable1, "Observable.create<List<S….onNext(result)\n        }");
    Observable observable2 = Observable.create(new PlotActivity$onCreate$observableStatusRecords$1());
    Intrinsics.checkExpressionValueIsNotNull(observable2, "Observable.create<List<S….onNext(result)\n        }");
    Disposable disposable = Observable.zip((ObservableSource)observable1, (ObservableSource)observable2, PlotActivity$onCreate$zipResult$1.INSTANCE).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new PlotActivity$onCreate$zipResult$2(i));
    String str = this.TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("zipResult: ");
    stringBuilder.append(disposable);
    Log.d(str, stringBuilder.toString());
    ((WebView)_$_findCachedViewById(R.id.webView)).loadData("Loading...", "text/html", "UTF-8");
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\030\n\000\n\002\020\002\n\000\n\002\030\002\n\002\020 \n\002\030\002\n\002\b\002\020\000\032\0020\0012 \020\002\032\034\022\030\022\026\022\004\022\0020\005 \006*\n\022\004\022\0020\005\030\0010\0040\0040\003H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "", "it", "Lio/reactivex/ObservableEmitter;", "", "Lca/albertahealthservices/contacttracing/status/persistence/StatusRecord;", "kotlin.jvm.PlatformType", "subscribe"}, k = 3, mv = {1, 1, 16})
  static final class PlotActivity$onCreate$observableStatusRecords$1<T> implements ObservableOnSubscribe<T> {
    public final void subscribe(ObservableEmitter<List<StatusRecord>> param1ObservableEmitter) {
      Intrinsics.checkParameterIsNotNull(param1ObservableEmitter, "it");
      param1ObservableEmitter.onNext((new StatusRecordStorage((Context)PlotActivity.this)).getAllRecords());
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\030\n\000\n\002\020\002\n\000\n\002\030\002\n\002\020 \n\002\030\002\n\002\b\002\020\000\032\0020\0012 \020\002\032\034\022\030\022\026\022\004\022\0020\005 \006*\n\022\004\022\0020\005\030\0010\0040\0040\003H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "", "it", "Lio/reactivex/ObservableEmitter;", "", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;", "kotlin.jvm.PlatformType", "subscribe"}, k = 3, mv = {1, 1, 16})
  static final class PlotActivity$onCreate$observableStreetRecords$1<T> implements ObservableOnSubscribe<T> {
    public final void subscribe(ObservableEmitter<List<StreetPassRecord>> param1ObservableEmitter) {
      Intrinsics.checkParameterIsNotNull(param1ObservableEmitter, "it");
      param1ObservableEmitter.onNext((new StreetPassRecordStorage((Context)PlotActivity.this)).getAllRecords());
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\030\n\000\n\002\030\002\n\000\n\002\020 \n\002\030\002\n\000\n\002\030\002\n\000\020\000\032\0020\0012\f\020\002\032\b\022\004\022\0020\0040\0032\f\020\005\032\b\022\004\022\0020\0060\003H\n¢\006\002\b\007"}, d2 = {"<anonymous>", "Lca/albertahealthservices/contacttracing/fragment/ExportData;", "records", "", "Lca/albertahealthservices/contacttracing/streetpass/persistence/StreetPassRecord;", "status", "Lca/albertahealthservices/contacttracing/status/persistence/StatusRecord;", "apply"}, k = 3, mv = {1, 1, 16})
  static final class PlotActivity$onCreate$zipResult$1<T1, T2, R> implements BiFunction<List<? extends StreetPassRecord>, List<? extends StatusRecord>, ExportData> {
    public static final PlotActivity$onCreate$zipResult$1 INSTANCE = new PlotActivity$onCreate$zipResult$1();
    
    public final ExportData apply(List<StreetPassRecord> param1List, List<StatusRecord> param1List1) {
      Intrinsics.checkParameterIsNotNull(param1List, "records");
      Intrinsics.checkParameterIsNotNull(param1List1, "status");
      return new ExportData(param1List, param1List1);
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\020\000\032\0020\0012\016\020\002\032\n \004*\004\030\0010\0030\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "exportedData", "Lca/albertahealthservices/contacttracing/fragment/ExportData;", "kotlin.jvm.PlatformType", "accept"}, k = 3, mv = {1, 1, 16})
  static final class PlotActivity$onCreate$zipResult$2<T> implements Consumer<ExportData> {
    PlotActivity$onCreate$zipResult$2(int param1Int) {}
    
    public final void accept(ExportData param1ExportData) {
      if (param1ExportData.getRecordList().isEmpty())
        return; 
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      long l1 = ((StreetPassRecord)CollectionsKt.sortedWith(param1ExportData.getRecordList(), new PlotActivity$onCreate$zipResult$2$$special$$inlined$sortedByDescending$1()).get(0)).getTimestamp();
      long l2 = 1000L;
      l1 = l1 / l2 + 60L;
      String str1 = simpleDateFormat.format(new Date(l1 * l2));
      long l3 = l1 - (this.$displayTimePeriod * 3600);
      String str2 = simpleDateFormat.format(new Date(l3 * l2));
      List list = param1ExportData.getRecordList();
      ArrayList<StreetPassRecord> arrayList = new ArrayList();
      Iterator<Object> iterator = list.iterator();
      while (true) {
        boolean bool = iterator.hasNext();
        int i = 1;
        if (bool) {
          StreetPassRecord streetPassRecord1 = (StreetPassRecord)iterator.next();
          StreetPassRecord streetPassRecord2 = streetPassRecord1;
          if (streetPassRecord2.getTimestamp() / l2 < l3 || streetPassRecord2.getTimestamp() / l2 > l1)
            i = 0; 
          if (i)
            arrayList.add(streetPassRecord1); 
          continue;
        } 
        arrayList = arrayList;
        bool = arrayList.isEmpty();
        String str4 = "UTF-8";
        String str3 = "text/html";
        if ((bool ^ true) != 0) {
          ArrayList<StreetPassRecord> arrayList1 = arrayList;
          LinkedHashMap<Object, Object> linkedHashMap1 = new LinkedHashMap<>();
          for (StreetPassRecord streetPassRecord : arrayList1) {
            String str = ((StreetPassRecord)streetPassRecord).getModelC();
            Object object2 = linkedHashMap1.get(str);
            Object object1 = object2;
            if (object2 == null) {
              object1 = new ArrayList();
              linkedHashMap1.put(str, object1);
            } 
            ((List<StreetPassRecord>)object1).add(streetPassRecord);
          } 
          LinkedHashMap<Object, Object> linkedHashMap2 = new LinkedHashMap<>();
          for (StreetPassRecord streetPassRecord : arrayList1) {
            String str = ((StreetPassRecord)streetPassRecord).getModelP();
            Object object2 = linkedHashMap2.get(str);
            Object object1 = object2;
            if (object2 == null) {
              object1 = new ArrayList();
              linkedHashMap2.put(str, object1);
            } 
            ((List<StreetPassRecord>)object1).add(streetPassRecord);
          } 
          Set set2 = CollectionsKt.union(linkedHashMap1.keySet(), CollectionsKt.toList(linkedHashMap2.keySet()));
          String str6 = PlotActivity.this.TAG;
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("allModels: ");
          stringBuilder1.append(set2);
          Log.d(str6, stringBuilder1.toString());
          List list1 = CollectionsKt.sortedWith(set2, new PlotActivity$onCreate$zipResult$2$sortedModelList$1(linkedHashMap1, linkedHashMap2));
          List list2 = list1;
          ArrayList<String> arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
          Iterator<String> iterator1 = list2.iterator();
          String str5 = str2;
          Set set1 = set2;
          while (true) {
            bool = iterator1.hasNext();
            String str13 = "";
            if (bool) {
              String str14;
              String str15;
              String str18;
              String str17 = iterator1.next();
              i = list1.indexOf(str17) + 1;
              boolean bool1 = linkedHashMap1.containsKey(str17);
              bool = linkedHashMap2.containsKey(str17);
              List list3 = (List)linkedHashMap1.get(str17);
              ArrayList<Integer> arrayList5 = null;
              if (list3 != null) {
                list3 = list3;
                ArrayList<String> arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                Iterator<StreetPassRecord> iterator4 = list3.iterator();
                while (iterator4.hasNext())
                  arrayList6.add(simpleDateFormat.format(new Date(((StreetPassRecord)iterator4.next()).getTimestamp()))); 
                str14 = CollectionsKt.joinToString$default(arrayList6, "\", \"", "[\"", "\"]", 0, null, null, 56, null);
              } else {
                list3 = null;
              } 
              List<Integer> list4 = (List)linkedHashMap1.get(str17);
              if (list4 != null) {
                List<Integer> list7 = list4;
                list4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list7, 10));
                Iterator<StreetPassRecord> iterator4 = list7.iterator();
                while (iterator4.hasNext())
                  list4.add(Integer.valueOf(((StreetPassRecord)iterator4.next()).getRssi())); 
                str15 = CollectionsKt.joinToString$default(list4, ", ", "[", "]", 0, null, null, 56, null);
              } else {
                list4 = null;
              } 
              List list5 = (List)linkedHashMap2.get(str17);
              if (list5 != null) {
                list5 = list5;
                ArrayList<String> arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list5, 10));
                Iterator<StreetPassRecord> iterator4 = list5.iterator();
                while (iterator4.hasNext())
                  arrayList6.add(simpleDateFormat.format(new Date(((StreetPassRecord)iterator4.next()).getTimestamp()))); 
                String str = CollectionsKt.joinToString$default(arrayList6, "\", \"", "[\"", "\"]", 0, null, null, 56, null);
              } else {
                list5 = null;
              } 
              List list6 = (List)linkedHashMap2.get(str17);
              if (list6 != null) {
                list6 = list6;
                arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list6, 10));
                Iterator<StreetPassRecord> iterator4 = list6.iterator();
                while (iterator4.hasNext())
                  arrayList5.add(Integer.valueOf(((StreetPassRecord)iterator4.next()).getRssi())); 
                str18 = CollectionsKt.joinToString$default(arrayList5, ", ", "[", "]", 0, null, null, 56, null);
              } 
              StringBuilder stringBuilder5 = new StringBuilder();
              stringBuilder5.append("var data");
              stringBuilder5.append(i);
              stringBuilder5.append(" = [];");
              String str16 = stringBuilder5.toString();
              if (!bool1) {
                str14 = "";
              } else {
                StringBuilder stringBuilder6 = new StringBuilder();
                stringBuilder6.append("\n                            var data");
                stringBuilder6.append(i);
                stringBuilder6.append("a = {\n                              name: 'central',\n                              x: ");
                stringBuilder6.append(str14);
                stringBuilder6.append(",\n                              y: ");
                stringBuilder6.append((String)list4);
                stringBuilder6.append(",\n                              xaxis: 'x");
                stringBuilder6.append(i);
                stringBuilder6.append("',\n                              yaxis: 'y");
                stringBuilder6.append(i);
                stringBuilder6.append("',\n                              mode: 'markers',\n                              type: 'scatter',\n                              line: {color: 'blue'}\n                            };\n                            data");
                stringBuilder6.append(i);
                stringBuilder6.append(" = data");
                stringBuilder6.append(i);
                stringBuilder6.append(".concat(data");
                stringBuilder6.append(i);
                stringBuilder6.append("a);\n                        ");
                str14 = StringsKt.trimIndent(stringBuilder6.toString());
              } 
              if (!bool) {
                str15 = str13;
              } else {
                StringBuilder stringBuilder6 = new StringBuilder();
                stringBuilder6.append("\n                            var data");
                stringBuilder6.append(i);
                stringBuilder6.append("b = {\n                              name: 'peripheral',\n                              x: ");
                stringBuilder6.append((String)list5);
                stringBuilder6.append(",\n                              y: ");
                stringBuilder6.append(str18);
                stringBuilder6.append(",\n                              xaxis: 'x");
                stringBuilder6.append(i);
                stringBuilder6.append("',\n                              yaxis: 'y");
                stringBuilder6.append(i);
                stringBuilder6.append("',\n                              mode: 'markers',\n                              type: 'scatter',\n                              line: {color: 'red'}\n                            };\n                            data");
                stringBuilder6.append(i);
                stringBuilder6.append(" = data");
                stringBuilder6.append(i);
                stringBuilder6.append(".concat(data");
                stringBuilder6.append(i);
                stringBuilder6.append("b);\n                        ");
                str15 = StringsKt.trimIndent(stringBuilder6.toString());
              } 
              StringBuilder stringBuilder4 = new StringBuilder();
              stringBuilder4.append(str16);
              stringBuilder4.append(str14);
              stringBuilder4.append(str15);
              arrayList2.add(stringBuilder4.toString());
              continue;
            } 
            str9 = str5;
            String str12 = CollectionsKt.joinToString$default(arrayList2, "\n", null, null, 0, null, null, 62, null);
            ArrayList<String> arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<String> iterator3 = list2.iterator();
            while (iterator3.hasNext()) {
              i = list1.indexOf(iterator3.next()) + 1;
              if (i < 20) {
                StringBuilder stringBuilder4 = new StringBuilder();
                stringBuilder4.append("\n                            data = data.concat(data");
                stringBuilder4.append(i);
                stringBuilder4.append(");\n                        ");
                String str = StringsKt.trimIndent(stringBuilder4.toString());
              } else {
                str5 = "";
              } 
              arrayList3.add(str5);
            } 
            String str11 = CollectionsKt.joinToString$default(arrayList3, "\n", null, null, 0, null, null, 62, null);
            ArrayList<String> arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<String> iterator2 = list2.iterator();
            str5 = str1;
            str1 = str9;
            while (iterator2.hasNext()) {
              i = list1.indexOf(iterator2.next()) + 1;
              if (i < 20) {
                StringBuilder stringBuilder4 = new StringBuilder();
                stringBuilder4.append("\n                                  xaxis");
                stringBuilder4.append(i);
                stringBuilder4.append(": {\n                                    type: 'date',\n                                    tickformat: '%H:%M:%S',\n                                    range: ['");
                stringBuilder4.append(str1);
                stringBuilder4.append("', '");
                stringBuilder4.append(str5);
                stringBuilder4.append("'],\n                                    dtick: ");
                stringBuilder4.append(this.$displayTimePeriod * 5);
                stringBuilder4.append(" * 60 * 1000\n                                  }\n                        ");
                String str = StringsKt.trimIndent(stringBuilder4.toString());
              } else {
                str9 = "";
              } 
              arrayList4.add(str9);
            } 
            String str7 = CollectionsKt.joinToString$default(arrayList4, ",\n", null, null, 0, null, null, 62, null);
            arrayList4 = new ArrayList<>(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (String str9 : list2) {
              i = list1.indexOf(str9) + 1;
              if (i < 20) {
                StringBuilder stringBuilder4 = new StringBuilder();
                stringBuilder4.append("\n                                  yaxis");
                stringBuilder4.append(i);
                stringBuilder4.append(": {\n                                    range: [-100, -30],\n                                    ticks: 'outside',\n                                    dtick: 10,\n                                    title: {\n                                      text: \"");
                stringBuilder4.append(str9);
                stringBuilder4.append("\"\n                                    }\n                                  }\n                        ");
                str9 = StringsKt.trimIndent(stringBuilder4.toString());
              } else {
                str9 = "";
              } 
              arrayList4.add(str9);
            } 
            String str10 = CollectionsKt.joinToString$default(arrayList4, ",\n", null, null, 0, null, null, 62, null);
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("\n                        <head>\n                            <script src='https://cdn.plot.ly/plotly-latest.min.js'></script>\n                        </head>\n                        <body>\n                            <div id='myDiv'></div>\n                            <script>\n                                ");
            stringBuilder3.append(str12);
            stringBuilder3.append("\n                                \n                                var data = [];\n                                ");
            stringBuilder3.append(str11);
            stringBuilder3.append("\n                                \n                                var layout = {\n                                  title: 'Activities from <b>");
            Intrinsics.checkExpressionValueIsNotNull(str1, "startTimeString");
            stringBuilder3.append(StringsKt.substring(str1, new IntRange(11, 15)));
            stringBuilder3.append("</b> to <b>");
            Intrinsics.checkExpressionValueIsNotNull(str5, "endTimeString");
            stringBuilder3.append(StringsKt.substring(str5, new IntRange(11, 15)));
            stringBuilder3.append("</b>   <span style=\"color:blue\">central</span> <span style=\"color:red\">peripheral</span>',\n                                  height: 135 * ");
            stringBuilder3.append(set1.size());
            stringBuilder3.append(",\n                                  showlegend: false,\n                                  grid: {rows: ");
            stringBuilder3.append(set1.size());
            stringBuilder3.append(", columns: 1, pattern: 'independent'},\n                                  margin: {\n                                    t: 30,\n                                    r: 30,\n                                    b: 20,\n                                    l: 50,\n                                    pad: 0\n                                  },\n                                  ");
            stringBuilder3.append(str7);
            stringBuilder3.append(",\n                                  ");
            stringBuilder3.append(str10);
            stringBuilder3.append("\n                                };\n                                \n                                var config = {\n                                    responsive: true, \n                                    displayModeBar: false, \n                                    displaylogo: false, \n                                    modeBarButtonsToRemove: ['toImage', 'sendDataToCloud', 'editInChartStudio', 'zoom2d', 'select2d', 'pan2d', 'lasso2d', 'autoScale2d', 'resetScale2d', 'zoomIn2d', 'zoomOut2d', 'hoverClosestCartesian', 'hoverCompareCartesian', 'toggleHover', 'toggleSpikelines']\n                                }\n                                \n                                Plotly.newPlot('myDiv', data, layout, config);\n                            </script>\n                        </body>\n                    ");
            String str8 = StringsKt.trimIndent(stringBuilder3.toString());
            str1 = PlotActivity.this.TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("customHtml: ");
            stringBuilder2.append(str8);
            Log.d(str1, stringBuilder2.toString());
            ((WebView)PlotActivity.this._$_findCachedViewById(R.id.webView)).loadData(str8, str3, str4);
            return;
          } 
          break;
        } 
        WebView webView = (WebView)PlotActivity.this._$_findCachedViewById(R.id.webView);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No data received in the last ");
        stringBuilder.append(this.$displayTimePeriod);
        stringBuilder.append(" hour(s) or more.");
        webView.loadData(stringBuilder.toString(), "text/html", "UTF-8");
        continue;
      } 
    }
    
    @Metadata(bv = {1, 0, 3}, d1 = {"\000\036\n\000\n\002\020\b\n\002\b\006\n\002\b\006\n\002\b\006\n\002\b\006\n\002\b\006\n\002\b\007\020\000\032\0020\001\"\004\b\000\020\0022\016\020\003\032\n \004*\004\030\001H\002H\0022\016\020\005\032\n \004*\004\030\001H\002H\002H\n¢\006\004\b\006\020\007¨\006\b"}, d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I", "kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareByDescending$1"}, k = 3, mv = {1, 1, 16})
    public static final class PlotActivity$onCreate$zipResult$2$$special$$inlined$sortedByDescending$1<T> implements Comparator<T> {
      public final int compare(T param1T1, T param1T2) {
        return ComparisonsKt.compareValues(Long.valueOf(((StreetPassRecord)param1T2).getTimestamp()), Long.valueOf(((StreetPassRecord)param1T1).getTimestamp()));
      }
    }
    
    @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\002\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "a", "", "b", "compare"}, k = 3, mv = {1, 1, 16})
    static final class PlotActivity$onCreate$zipResult$2$sortedModelList$1<T> implements Comparator<String> {
      PlotActivity$onCreate$zipResult$2$sortedModelList$1(Map param1Map1, Map param1Map2) {}
      
      public final int compare(String param1String1, String param1String2) {
        byte b1;
        byte b2;
        byte b3;
        Intrinsics.checkParameterIsNotNull(param1String1, "a");
        Intrinsics.checkParameterIsNotNull(param1String2, "b");
        List list2 = (List)this.$dataByModelC.get(param1String1);
        int i = 0;
        if (list2 != null) {
          b1 = list2.size();
        } else {
          b1 = 0;
        } 
        List list1 = (List)this.$dataByModelP.get(param1String1);
        if (list1 != null) {
          b2 = list1.size();
        } else {
          b2 = 0;
        } 
        list1 = (List)this.$dataByModelC.get(param1String2);
        if (list1 != null) {
          b3 = list1.size();
        } else {
          b3 = 0;
        } 
        list1 = (List)this.$dataByModelP.get(param1String2);
        if (list1 != null)
          i = list1.size(); 
        return b3 + i - b1 + b2;
      }
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\036\n\000\n\002\020\b\n\002\b\006\n\002\b\006\n\002\b\006\n\002\b\006\n\002\b\006\n\002\b\007\020\000\032\0020\001\"\004\b\000\020\0022\016\020\003\032\n \004*\004\030\001H\002H\0022\016\020\005\032\n \004*\004\030\001H\002H\002H\n¢\006\004\b\006\020\007¨\006\b"}, d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I", "kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareByDescending$1"}, k = 3, mv = {1, 1, 16})
  public static final class PlotActivity$onCreate$zipResult$2$$special$$inlined$sortedByDescending$1<T> implements Comparator<T> {
    public final int compare(T param1T1, T param1T2) {
      return ComparisonsKt.compareValues(Long.valueOf(((StreetPassRecord)param1T2).getTimestamp()), Long.valueOf(((StreetPassRecord)param1T1).getTimestamp()));
    }
  }
  
  @Metadata(bv = {1, 0, 3}, d1 = {"\000\020\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\002\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\003H\n¢\006\002\b\005"}, d2 = {"<anonymous>", "", "a", "", "b", "compare"}, k = 3, mv = {1, 1, 16})
  static final class PlotActivity$onCreate$zipResult$2$sortedModelList$1<T> implements Comparator<String> {
    PlotActivity$onCreate$zipResult$2$sortedModelList$1(Map param1Map1, Map param1Map2) {}
    
    public final int compare(String param1String1, String param1String2) {
      byte b1;
      byte b2;
      byte b3;
      Intrinsics.checkParameterIsNotNull(param1String1, "a");
      Intrinsics.checkParameterIsNotNull(param1String2, "b");
      List list2 = (List)this.$dataByModelC.get(param1String1);
      int i = 0;
      if (list2 != null) {
        b1 = list2.size();
      } else {
        b1 = 0;
      } 
      List list1 = (List)this.$dataByModelP.get(param1String1);
      if (list1 != null) {
        b2 = list1.size();
      } else {
        b2 = 0;
      } 
      list1 = (List)this.$dataByModelC.get(param1String2);
      if (list1 != null) {
        b3 = list1.size();
      } else {
        b3 = 0;
      } 
      list1 = (List)this.$dataByModelP.get(param1String2);
      if (list1 != null)
        i = list1.size(); 
      return b3 + i - b1 + b2;
    }
  }
}


/* Location:              /Users/mfenniak/Development/decompile/tmp/classes-dex2jar.jar!/ca/albertahealthservices/contacttracing/PlotActivity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */