package com.example.student.newsapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;
import com.example.student.newsapp.Common.ConstVariable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;
import static com.example.student.newsapp.Adapter.RVAdapter.channelname;
import static com.example.student.newsapp.Adapter.RVAdapter.channelname2;
import static com.example.student.newsapp.Adapter.RVAdapter.channelsource;
import static com.example.student.newsapp.Adapter.RVAdapter.channelsource2;
import static com.example.student.newsapp.Adapter.RVAdapter.county;
import static com.example.student.newsapp.Adapter.categorychannel.countyc;


public class AlarmReceiver extends BroadcastReceiver implements ConstVariable{
    public static boolean boolean_headline;
    String str_channelname,str_channelsource;





    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(final Context context, Intent intent) {

        if(county<4 && countyc <4){
            channelname = "Google News";
            Log.d("Notification county < 4" , channelname);
            channelsource ="google-news";
        }
        else
        {
            channelname = channelname2;
            Log.d("Notification county > 4" , channelname2);
            channelsource = channelsource2;
        }
      /*  if(countyc<4){
            channelname = "Google News";
            Log.d("Notificaton countyC < 4" , channelname);
            channelsource ="google-news";
        }
        else
        {
            channelname = channelname3;
            Log.d("Notificatio countyC > 4" , channelname3);
            channelsource = channelsource3;
        }*/


        HashMap<String,Object>HM = new HashMap<>();
        HM.put(SOURCE, channelsource);

        HM.put(SORTBY,TOP);

        HM.put(APIKEY, APIKEYID);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getresponse("articles",HM,"");
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String title = null;
                String image = null;
                List<HashMap<String, String>> dataList = new ArrayList<>();

                if (response.isSuccessful()) {
                    try {
                        HashMap<String, Object> dataMap = null;


                        String str_testing = response.body().string();

                        JSONObject jsonObject = new JSONObject(str_testing);
                        JSONArray data = jsonObject.getJSONArray("articles");
                        int i = 0;
                        JSONObject d = data.getJSONObject(i);
                        title = d.getString("title");
                        image = d.getString("urlToImage");
                        HashMap<String,String> samachar = new HashMap<>();

                        samachar.put("title",title);
                        samachar.put("urlToImage",image);
                        dataList.add(samachar);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    TaskStackBuilder stackBuilder;

                    if(Objects.equals(channelsource, "google-news")){
                        Intent intenti = new Intent(context,MainActivity.class);
                        boolean_headline = true;
                        intenti.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        stackBuilder = TaskStackBuilder.create(context);
                        stackBuilder.addParentStack(MainActivity.class);
                        stackBuilder.addNextIntent(intenti);

                    }
                    else
                    {
                        Intent intenti = new Intent(context,NotificationDetail.class);
                        intenti.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intenti.putExtra(INTENT_CHANNELSOURCE,channelsource);
                        intenti.putExtra(INTENT_CHANNELNAME,channelname);
                        stackBuilder = TaskStackBuilder.create(context);
                        stackBuilder.addParentStack(MainActivity.class);
                        stackBuilder.addNextIntent(intenti);
                    }



                    PendingIntent pendingIntent = stackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);


                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                    RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.notification);
                    remoteViews.setImageViewResource(R.id.imageview,R.mipmap.ic_launcher);
                    remoteViews.setTextViewText(R.id.textView,title);

                       Notification notification = builder
                            .setTicker("New Message Alert!")
                            .setAutoCancel(true)
                            .setSmallIcon(R.mipmap.aaaaaa)
                            .setContentIntent(pendingIntent)
                            .setVibrate(new long[]{1000, 1000})
                            .setLights(Color.GREEN, 3000, 3000)
                               .setContent(remoteViews)
                            .build();

                     NotificationTarget notificationTarget = new NotificationTarget(
                            context,remoteViews,R.id.imageview,notification,0);

                    Glide.with( context.getApplicationContext() ) // safer!
                            .load(image )
                            .asBitmap()
                            .into( notificationTarget );


                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.HOUR, 2);
                    Intent intent = new Intent("android.action.DISPLAY_NOTIFICATION");
                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    PendingIntent broadcast = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Call", t.toString());
            }
        });
    }


    @Override
    public void callback(HashMap<String, Object> tmpMap, String dataType, int mode) {

    }
}

/*
package com.example.student.newsapp;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.student.newsapp.Common.ConstVariable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;
import static com.example.student.newsapp.Adapter.RVAdapter.channelname;
import static com.example.student.newsapp.Adapter.RVAdapter.channelsource;


public class AlarmReceiver extends BroadcastReceiver implements ConstVariable {
    TaskStackBuilder stackBuilder;
    public static boolean boolean_headline;
    Context context;

    String str_channelname,str_channelsource;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(final Context context, Intent intent) {
       str_channelname=channelname;
        str_channelsource="google-news";
        HashMap<String,Object>HM = new HashMap<>();
        HM.put(SOURCE,channelsource);

            HM.put(SORTBY,TOP);

        HM.put(APIKEY, APIKEYID);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getresponse("articles",HM,"");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String title = null;
                String image = null;
                List<HashMap<String, String>> dataList = new ArrayList<>();

                if (response.isSuccessful()) {
                    try {
                        HashMap<String, Object> dataMap = null;


                        String str_testing = response.body().string();

                        JSONObject jsonObject = new JSONObject(str_testing);
                        JSONArray data = jsonObject.getJSONArray("articles");
                        int i = 0;
                        JSONObject d = data.getJSONObject(i);
                        title = d.getString("title");
                        image = d.getString("urlToImage");
                        HashMap<String,String> samachar = new HashMap<>();

                        samachar.put("title",title);
                         samachar.put("urlToImage",image);
                        dataList.add(samachar);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(Objects.equals(channelsource, "google-news")){
                        Intent intenti = new Intent(context,MainActivity.class);
                        boolean_headline = true;
                        intenti.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        stackBuilder = TaskStackBuilder.create(context);
                        stackBuilder.addParentStack(MainActivity.class);
                        stackBuilder.addNextIntent(intenti);
                        
                  }
                    else
                    {
                        Intent intenti = new Intent(context,NotificationDetail.class);
                        intenti.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intenti.putExtra(INTENT_CHANNELSOURCE,channelsource);
                        intenti.putExtra(INTENT_CHANNELNAME,channelname);
                        stackBuilder = TaskStackBuilder.create(context);
                        stackBuilder.addParentStack(MainActivity.class);
                        stackBuilder.addNextIntent(intenti);
                    }
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher);

                    PendingIntent pendingIntent = stackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                    Notification notification = builder.setContentTitle("New Notification")
                            .setContentText(title)
                            .setTicker("New Message Alert!")
                            .setAutoCancel(true)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pendingIntent)
                            .setLargeIcon(bitmap)
                           // .setVibrate(new long[]{1000, 1000})
                            .setLights(Color.GREEN, 3000, 3000)

                            .build();


                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.SECOND, 15);
                    Intent intent = new Intent("android.action.DISPLAY_NOTIFICATION");
                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    PendingIntent broadcast = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Call", t.toString());
            }
            });
        }


    @Override
    public void callback(HashMap<String, Object> tmpMap, String dataType, int mode) {

    }
}
*/
