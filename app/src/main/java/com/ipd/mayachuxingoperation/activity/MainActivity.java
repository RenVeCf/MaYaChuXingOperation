package com.ipd.mayachuxingoperation.activity;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.base.BaseActivity;
import com.ipd.mayachuxingoperation.base.BasePresenter;
import com.ipd.mayachuxingoperation.base.BaseView;
import com.ipd.mayachuxingoperation.fragment.HomeFragment;
import com.ipd.mayachuxingoperation.fragment.MalfunctionCarFragment;
import com.ipd.mayachuxingoperation.fragment.ModifyCarFragment;
import com.ipd.mayachuxingoperation.fragment.SumWorkFragment;
import com.ipd.mayachuxingoperation.utils.ApplicationUtil;
import com.ipd.mayachuxingoperation.utils.NavigationBarUtil;
import com.ipd.mayachuxingoperation.utils.ToastUtil;

import butterknife.BindView;

/**
 * Description ：主页
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/7.
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.nav_view)
    BottomNavigationView navView;

    private long firstTime = 0;
    private Fragment currentFragment = new Fragment();
    private HomeFragment homeFragment = new HomeFragment();
    private MalfunctionCarFragment malfunctionCarFragment = new MalfunctionCarFragment();
    private ModifyCarFragment modifyCarFragment = new ModifyCarFragment();
    private SumWorkFragment sumWorkFragment = new SumWorkFragment();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(homeFragment).commit();
                    return true;
                case R.id.navigation_malfunction_car:
                    switchFragment(malfunctionCarFragment).commit();
                    return true;
                case R.id.navigation_modify_car:
                    switchFragment(modifyCarFragment).commit();
                    return true;
                case R.id.navigation_sum_work:
                    switchFragment(sumWorkFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }

    @Override
    public void init() {
        //适配虚拟按键
        if (NavigationBarUtil.hasNavigationBar(this)) {
            NavigationBarUtil.initActivity(findViewById(android.R.id.content));
        }
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        switchFragment(homeFragment).commit();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    //Fragment优化
    public FragmentTransaction switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.ll_main, targetFragment, targetFragment.getClass().getName());
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;
        return transaction;
    }

    //双击退出程序
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtil.showShortToast(getResources().getString(R.string.click_out_again));
            firstTime = secondTime;
        } else
            ApplicationUtil.getManager().exitApp();
    }
}