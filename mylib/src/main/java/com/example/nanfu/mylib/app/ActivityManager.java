package com.example.nanfu.mylib.app;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class ActivityManager {

    private static List<Activity> activities=new ArrayList<>();

    /**
     * 创建activity时添加到activities中
     * @param activity
     */
    public static void activityCreate(Activity activity){
        activities.add(activity);
    }

    /**
     * 销毁activity时移除
     * @param activity
     */
    public static void activityDestroy(Activity activity){
        activities.remove(activity);
    }

    /**
     * 根据指定的Class，获取Activity
     * @param clazz
     * @return
     */
    public static Activity getActivity(Class clazz) {
        Activity activity = null;
        for (Activity a: activities) {
            if (a != null && a.getClass() == clazz) {
                activity = a;
                break;
            }
        }
        return activity;
    }
    /**
     * 结束指定activity
     * @param cls
     */
    public static void finish(Class cls){
        for(int i=0;i<activities.size();i++){
            Activity activity=activities.get(i);
            if(cls!=null&&activity.getClass()==cls){
                activities.remove(i);
                activity.finish();
                break;
            }
        }
    }

    /**
     * 结束所有activity
     */
    public static void finishAll(){
        finishAllWithExcept(null);
    }

    /**
     * 结束指定activity外的所有活动
     * @param cls
     */
    public static void finishAllWithExcept(Class cls){
        int index=0;
        while (++index<activities.size()){
            Activity activity=activities.get(index);
            if(cls!=null&&cls!=activity.getClass()){
                activity.finish();
                activities.remove(index);
            }
        }
    }
    public static void finishAllWithExcet(List<Class> clses){
        if(clses==null) finishAll();
        int index=0;
        while (++index<activities.size()){
            Activity activity=activities.get(index);
            if(clses!=null&&!clses.contains(activity)){
                activity.finish();
                activities.remove(index);
            }
        }

    }


}
