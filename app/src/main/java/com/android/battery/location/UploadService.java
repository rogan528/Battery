package com.android.battery.location;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.android.battery.utils.CLoseUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UploadService extends IntentService {

    public UploadService() {
        super("UploadService");
    }

    public static void UploadService(Context context,String location) {
        Intent intent = new Intent(context, UploadService.class);

        intent.putExtra("DATA",location);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            String location = intent.getStringExtra("DATA");
            Log.d("zhangbin","UploadService 获得地址"+location);
            HttpURLConnection connection = null;
            OutputStream outputStream = null;
                try {
                    connection = (HttpURLConnection) new URL("http://www.baidu.com/").openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    outputStream = connection.getOutputStream();
                    outputStream.write(location.getBytes());
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    CLoseUtils.safeColse(outputStream);
                    if (null != connection){
                        connection.disconnect();
                    }
                }



        }
    }


}
