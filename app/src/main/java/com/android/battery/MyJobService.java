package com.android.battery;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;

import com.android.battery.utils.CLoseUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyJobService extends JobService {
    public MyJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        new MyAyncTask().equals(params);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
    class MyAyncTask extends AsyncTask<JobParameters,Void,Void> {
        JobParameters jobParameters;

        @Override
        protected Void doInBackground(JobParameters[] object) {
            jobParameters = object[0];
            PersistableBundle extras = jobParameters.getExtras();
            String location = extras.getString("DATA");
            Log.d("zhangbin", "MyJobService 获得地址" + location);
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
            } finally {
                CLoseUtils.safeColse(outputStream);
                if (null != connection) {
                    connection.disconnect();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            jobFinished(jobParameters,false);
            super.onPostExecute(aVoid);
        }
    }
}
