package com.cscodetech.moverslorry.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cscodetech.moverslorry.R;
import com.cscodetech.moverslorry.fragment.CompletBookLoadFragment;
import com.cscodetech.moverslorry.fragment.CurrentBookLoadFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyBookLoadActivity extends BaseActivity {

    @BindView(R.id.img_back)
    public ImageView imgBack;
    @BindView(R.id.tab)
    public TabLayout mTabs;
    @BindView(R.id.viewPager)
    public ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_load);
        ButterKnife.bind(this);


        TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(CurrentBookLoadFragment.newInstance(), getString(R.string.mycurrentloads));
        adapter.addFragment(CompletBookLoadFragment.newInstance(), getString(R.string.completed));
        mViewPager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewPager);



    }

    @OnClick({R.id.img_back})

    public void onBindClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        }
    }

    public class TabFragmentAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public TabFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }
    }
}