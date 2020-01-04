package com.xmut.elelab;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.xmut.elelab.MyTool.MyImage.ImageUtils;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;//主界面fragment配置
    private LinearLayout leftHeadLinear;//左侧上方
    private ImageView headImg;//左侧头像
    private Drawable btnDrawable; //左侧背景图片
    private RequestBuilder<Drawable> drawableRequestBuilder; //头像图片缓存

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


}
