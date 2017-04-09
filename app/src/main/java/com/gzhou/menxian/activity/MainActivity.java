package com.gzhou.menxian.activity;

import android.support.v4.app.Fragment;

import com.gzhou.menxian.fragment.MainFragment;

public class MainActivity extends AbstractFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }
}
