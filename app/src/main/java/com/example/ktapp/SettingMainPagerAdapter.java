package com.example.ktapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;


public class SettingMainPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private String[] titles = new String[0];
    private Fragment[] baseFragments = new Fragment[]{
            new FirstFragment(),
            new SecondFragment1(),
            new SecondFragment(),
            new FirstFragment(),
            new FirstFragment(),
            new FirstFragment()};


    public SettingMainPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mContext = context;
        titles = context.getResources().getStringArray(R.array.setting_tab);
//        fragments = new SparseArray<BaseFragment>(titles.length);
//        for (int i = 0; i < titles.length; i++) {
//            if (baseFragments[i] instanceof NVJourneyExtensionFragment) {
//                if (ToolUtils.isElectricCar()) {
//                    baseFragments[i] = new NVJourneyPureElectricFragment();
//                }
//            }
//            fragments.put(i, baseFragments[i]);
//        }
    }


    @Override
    public Fragment getItem(int position) {
//        BaseFragment[] baseFragments = new BaseFragment[]{
//                new NVVehicleFragment(),
//                new NVADASFragment(),
//                new NVVoiceFragment(),
//                new NVChargeOrDischargeFragment(),
//                new NVJourneyExtensionFragment(),
//                new NVSystemSettingFragment()};

        return baseFragments[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    private View setTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_item, null);
        TextView txt_title = (TextView) view.findViewById(R.id.tabText);
        txt_title.setText(titles[position]);
        return view;
    }

    public void onTabSelected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if (view == null) {
            view = setTabView(tab.getPosition());
            tab.setCustomView(view);
        }
        TextView txt_title = view.findViewById(R.id.tabText);
        txt_title.setTextAppearance(R.style.tabLayout_style_selected);
        View divider = view.findViewById(R.id.tab_divider);
        divider.setVisibility(View.VISIBLE);
//        tab.view.setPadding(0,0,0,0);
//        LinearLayout linearLayout = (LinearLayout) tab.parent.getChildAt(0);
//
//        tab.view.post(()->{
//            try {
//                Field field = tab.view.getClass().getDeclaredField("customView");
//                field.setAccessible(true);
//                tab.view.setBackground(tab.view.getContext().getDrawable(R.color.nv_ambientligint_b));
//                View view1 = (View) field.get(tab.view);
//                view1.setBackground(tab.view.getContext().getDrawable(R.color.nv_ambientligint_p));
//                txt_title.setBackground(tab.view.getContext().getDrawable(R.color.nv_ambientligint_y));
//                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) view1.getLayoutParams();
//                params1.width = txt_title.getWidth();
//                params1.rightMargin = 0;
//                params1.leftMargin = 0;
//                view1.setLayoutParams(params1);
//                (tab.view).postDelayed(()->{
//
//                    int width = txt_title.getWidth();
//                    int width1 = tab.view.getWidth();
//                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) (tab.view).getLayoutParams();
//                    Log.d("wuli1", "9999999:::" + txt_title.getText() + ";;" + width + ";;;" + width1 + " params.width::"+ linearLayout.getChildAt(0).getWidth());
//                    params.width = width;
//                    (tab.view).setLayoutParams(params);
//                    linearLayout.invalidate();
//                },100);
//                tab.view.invalidate();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });


    }

    public void onTabUnselected(TabLayout.Tab tab) {
//        View view = tab.getCustomView();
//        if (view == null) {
//            view = setTabView(tab.getPosition());
//            tab.setCustomView(view);
//        }
//        TextView txt_title = view.findViewById(R.id.tabText);
//        View divider = view.findViewById(R.id.tab_divider);
//        divider.setVisibility(View.INVISIBLE);
//        txt_title.setTextAppearance(R.style.tabLayout_style_unselected);
//        tab.view.setPadding(0,0,0,0);
//        tab.view.measure(0,0);
//        tab.view.post(() -> {
//            try {
//                tab.view.setBackground(tab.view.getContext().getDrawable(R.color.nv_ambientligint_y));
//                Field field = tab.view.getClass().getDeclaredField("customView");
//                field.setAccessible(true);
//                View view1 = (View) field.get(tab.view);
//                int width = view1.getMeasuredWidth();
//                int width1 = txt_title.getMeasuredWidth();
//                ViewGroup.LayoutParams params = ((LinearLayout) tab.view).getLayoutParams();
//                Log.d("wuli1", "888:::" + txt_title.getText() + ";;" + width+ ";;;" + width1 + "params.width::" + tab.view.getMeasuredWidth() + " " + tab.view.getWidth());
//                params.width = 0;
//                (tab.view).setLayoutParams(params);
//                tab.view.invalidate();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });


    }

}