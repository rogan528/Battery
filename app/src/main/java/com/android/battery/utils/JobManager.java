package com.android.battery.utils;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;

import com.android.battery.MyJobService;

import java.util.List;

public class JobManager {
    static JobManager instance;
    private JobScheduler jobScheduler;
    private Context mContext;
    private static final int jobId = 0;
    public static JobManager getInstance(){
        if (null == instance){
            instance = new JobManager();
        }
        return instance;
    }
    public void init(Context context){
        this.mContext = context.getApplicationContext();
        jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

    }
    public void addJob(String location){
        if (null == jobScheduler){
            return;
        }
        JobInfo pendingJob = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            pendingJob = jobScheduler.getPendingJob(jobId);
        }else {
            List<JobInfo> allPendingJobs = jobScheduler.getAllPendingJobs();
            for (JobInfo jobInfo:allPendingJobs) {
                if (jobInfo.getId() == jobId){
                    pendingJob = jobInfo;
                    break;
                }
            }

        }
        if (null != pendingJob){
            //多个坐标一起上传
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                PersistableBundle extras = pendingJob.getExtras();
                String data = extras.getString("DATA");
                //拼接
                location = data + "@" + location;
                jobScheduler.cancel(jobId);

            }

        }
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString("DATA",location);

        JobInfo jobInfo = new JobInfo.Builder(jobId, new ComponentName(mContext, MyJobService.class))
                //充电状态
                .setRequiresCharging(true)
                //使用wifi
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setExtras(persistableBundle)
                .build();
        //提交任务
        jobScheduler.schedule(jobInfo);

    }
}
