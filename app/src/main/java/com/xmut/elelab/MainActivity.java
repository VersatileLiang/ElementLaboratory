package com.xmut.elelab;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cy.translucentparent.StatusNavUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.xmut.elelab.MyTool.MyImage.ImageUtils;
import com.xmut.elelab.MyTool.base.BaseActivity;
import com.xmut.elelab.ui.knowledge.BackHandledFragment;
import com.xmut.elelab.ui.knowledge.BackHandledInterface;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

import static com.xmut.elelab.MyTool.base.BaseFragmentActivity.getNavigationBarFinalHeight;

public class MainActivity extends BaseActivity implements BackHandledInterface {

    private AppBarConfiguration mAppBarConfiguration;//主界面fragment配置
    private LinearLayout leftHeadLinear;//左侧上方
    private ImageView headImg;//左侧头像
    private Drawable btnDrawable; //左侧背景图片
    private RequestBuilder<Drawable> drawableRequestBuilder; //头像图片缓存

    private BackHandledFragment mBackHandedFragment;
    private boolean hadIntercept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //NavigationBar半透明
        StatusNavUtils.setNavigationBarColor(this,0x33000000);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //浮动按钮
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //左侧抽屉菜单栏
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //默认背景图片网址
        String backgroundImgUrl = "https://i0.hdslb.com/bfs/album/d4442467ebb9711faa7cdc7754f4973ad0972691.jpg";
        String backgroundImgUrl2 = "https://i0.hdslb.com/bfs/album/12b9605b3566455e6f0dd5a2c99ab68c67749a71.jpg";
        Random random = new Random();
        int imgNum = random.nextInt(2);
        if (imgNum == 0){//随机使用一个背景图片
            btnDrawable = ImageUtils.getDrawable(backgroundImgUrl);
        }else {
            btnDrawable = ImageUtils.getDrawable(backgroundImgUrl2);
        }
        //默认头像网址
        String headImgUrl = "https://i0.hdslb.com/bfs/album/5b27d870c275b716e800ed1b214c1ccd87b09d75.jpg";
        drawableRequestBuilder = Glide.with(this)
                .load(headImgUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()));//设置圆形头像

        BottomNavigationView navView = findViewById(R.id.bottom_nav_view);//底部导航栏
        NavigationView navigationView = findViewById(R.id.left_nav_view);
        // 将每个菜单ID作为一组ID传递，因为每个菜单都应视为顶级目标。
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send,
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(navView, navController);
        setHalfTransparent();//设置状态栏半透明
        setFitSystemWindow(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 膨胀菜单；
        // 这会将项目添加到操作栏（如果有）。
        getMenuInflater().inflate(R.menu.main, menu);

        //设置默认背景图片
        leftHeadLinear = this.findViewById(R.id.left_head_linear);
        leftHeadLinear.setBackground(btnDrawable);//设置左侧抽屉菜单栏上方背景图

        //设置默认头像
        headImg = this.findViewById(R.id.imageView);
        drawableRequestBuilder.into(headImg);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * 选中了web那个fragment
     * @param selectedFragment
     */
    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }

    /**
     * 监听web的fragment的返回事件
     */
    @Override
    public void onBackPressed() {
        if(mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()){
            if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                super.onBackPressed(); //退出
            }else{
                getSupportFragmentManager().popBackStack(); //fragment 出栈
            }
        }
    }

    /**
     * 界面控制
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //隐藏导航栏布局，导航栏依然显示
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏导航栏和布局
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
            int height;
            height = getNavigationBarFinalHeight();
//            height /= 2;
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
            ConstraintLayout.LayoutParams b = (ConstraintLayout.LayoutParams) bottomNavigationView.getLayoutParams();
            b.setMargins(0,0,0,height);
        }
    }

    /**
     * 全透状态栏
     */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 半透明状态栏
     */
    protected void setHalfTransparent() {

        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 如果需要内容紧贴着StatusBar
     * 应该在对应的xml布局文件中，设置根布局fitsSystemWindows=true。
     */
    private View contentViewGroup;

    protected void setFitSystemWindow(boolean fitSystemWindow) {
        if (contentViewGroup == null) {
            contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        }
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }
}
