package com.example.ktapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.util.Pools;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.commonlib.aop.annotation.DebugTrace;
import com.example.ktapp.aop.AppTrace;
import com.example.ktapp.bean.Student;
import com.example.ktapp.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;


/**
 * @author wuli
 */
public class PagerActivity extends FragmentActivity {
    private ActivityMainBinding activityMainBinding;
    private SettingMainPagerAdapter settingMainPagerAdapter;
    private boolean stopSettOffsetLimit;
//
//    @Inject
//    Student student;
//    @Inject
//    Student student１;
//    @Inject
//    Teacher teacher;
//    @Inject
//    Car car01;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        test();

        activityMainBinding.settingTab1.setupWithViewPager(activityMainBinding.settingViewpager);
        activityMainBinding.settingTab1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                settingMainPagerAdapter.onTabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                settingMainPagerAdapter.onTabUnselected(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (activityMainBinding.settingViewpager.getOffscreenPageLimit() == 1) {
            stopSettOffsetLimit = false;
            activityMainBinding.settingViewpager.postDelayed(mRunnable, 10000);
        }
        if (settingMainPagerAdapter == null) {
            settingMainPagerAdapter = new SettingMainPagerAdapter(getSupportFragmentManager(), this);
        }
        activityMainBinding.settingViewpager.setAdapter(settingMainPagerAdapter);
        activityMainBinding.settingViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ///关于dependencies参数
//        DaggerStudentComponent.builder().teacherComponent(DaggerTeacherComponent.create())
//                .build().inject(this);
//        student.log();
//        teacher.log();
////    @Subcomponen与关于dependencies参数对比
//        DaggerTeacherStubComponent.create().buildStudentStubComponent().build().inject(this);
//        student.log();
//        teacher.log();

        //module include 使用
//        DaggerStudentComponentInclude.create().inject(this);
//        Log.e("wuli1", "&&&&&" + student);
//        Log.e("wuli1", "&&&&&" + student１);

//        DaggerComponentAnnotationBuilder.builder().setStudent(new Student(new Teacher())).build().inject(this);
//        DaggerComponentAnnotationsFactoryComponent.factory().create(new Student(new Teacher())).inject(this);
//        Log.e("wuli1", "&&&&&" + student);

//        /provider 和binds
//        Car car = DaggerCarComponent.create().car();
//        car.log();
//        car = DaggerCarAnnotationBindsComponent.create().car();
//        car.log();
//        car = DaggerCarAnnotationBindsNoprividerComponent.create().car();
//        car.log();

    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            test();
            if (!stopSettOffsetLimit) {
                activityMainBinding.settingViewpager.setOffscreenPageLimit(5);
            }
        }
    };

    @DebugTrace
    public void test() {
//        System.out.println("Hello, I am CSDN_LQR");
//        int a = 1 / 0;
    }
//    private void addDebugThreadListener() {
//
//            DexposedBridge.hookAllConstructors(Thread.class, new XC_MethodHook() {
//
//
//                @Override
//
//                protected void afterHookedMethod(MethodHookParam param) throws
//                        Throwable {
//                     super.afterHookedMethod(param);
//                     Thread thread = (Thread) param.thisObject;
//                     String name = thread.getName();
//                     if (filterThreadLog(name)) {
//                    }
//
//                }
//
//            });
//
//
//    }


    private boolean filterThreadLog(String name) {
        return !TextUtils.isEmpty(name)
                && !name.startsWith("Beans")
                && !name.startsWith("OkHttp")
                && !name.startsWith("Rx");
    }
}
