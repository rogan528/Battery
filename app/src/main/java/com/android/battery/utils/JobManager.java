package com.android.battery.utils;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.os.Build;

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

        }
    }
}
