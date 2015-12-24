package com.potato.sticker.main.ui.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.potato.chips.common.PageCtrl;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.SPUtils;
import com.potato.library.net.PotatoRequest;
import com.potato.library.net.PotatoRequestManager;
import com.potato.sticker.R;
import com.potato.sticker.login.ui.act.LoginActivity;
import com.potato.sticker.main.data.bean.LoadImgBean;
import com.potato.sticker.main.data.db.DBUtil;
import com.potato.sticker.main.data.parser.LoadImgParser;
import com.potato.sticker.main.data.request.StickerRequestBuilder;

import java.util.ArrayList;
import java.util.List;

//第一次运行的引导页代码
public class WelcomeActivity extends Activity implements OnPageChangeListener,
        OnClickListener {
    private Context context;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private Button startButton;
    private LinearLayout indicatorLayout;
    private ArrayList<View> views;
    private ImageView[] indicators = null;
    public ArrayList<LoadImgBean> loadImgBeans = new ArrayList<LoadImgBean>();
    private View ll_fisrt;
    private Animation animation;
    private static int TIME = 2000;                                        // 进入主程序的延迟时间
    private View iv_splash;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_welcome);

        context = this;
        ll_fisrt = findViewById(R.id.ll_first);
        ll_fisrt.setVisibility(View.GONE);
        iv_splash = findViewById(R.id.iv_splash);
        // 创建桌面快捷方式
        // new CreateShut(this);
        // 设置引导图片
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 仅需在这设置图片 指示器和page自动添加
//		 initView();
        into();
        loadData();
    }

    // 初始化视图
    private void initView() {

        // 实例化视图控件
        // if(loadImgBeans==null){
        // loadImgBeans = new ArrayList<LoadImgBean>();
        // for(int i= 0 ;i<2;i++){
        // LoadImgBean loadImgBean = new LoadImgBean();
        // loadImgBean.setPic("201409271449175.jpg");
        // loadImgBeans.add(new LoadImgBean());
        // }
        // }
        String[] imageUrls = new String[]{
                "http://img4.duitang.com/uploads/blog/201311/04/20131104193715_NCexN.thumb.jpeg",
                "http://cdn.duitang.com/uploads/blog/201401/07/20140107223310_LH3Uy.thumb.jpeg",
                "http://img5.duitang.com/uploads/item/201405/09/20140509222156_kVexJ.thumb.jpeg"
        };
        if (loadImgBeans == null || loadImgBeans.size() == 0) {
            loadImgBeans = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                LoadImgBean loadImgBean = new LoadImgBean();
                loadImgBean.setPic(imageUrls[i]);
                loadImgBeans.add(i, loadImgBean);
            }
        }

        viewPager = (ViewPager) findViewById(R.id.viewpage);

        startButton = (Button) findViewById(R.id.start_Button);
        startButton.setOnClickListener(this);
        indicatorLayout = (LinearLayout) findViewById(R.id.indicator);
        indicatorLayout.removeAllViews();
        views = new ArrayList<View>();
        indicators = new ImageView[loadImgBeans.size()]; // 定义指示器数组大小
        for (int i = 0; i < loadImgBeans.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            layoutParams.weight = 1;
            // 循环加入图片
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageLoaderUtil.displayImage(loadImgBeans.get(i).getPic(), imageView, R.drawable.def_gray_big);
            views.add(imageView);
            // 循环加入指示器
            indicators[i] = new ImageView(context);
            // indicators[i].setPadding(5, 5, 5, 5);
            indicators[i].setBackgroundResource(R.drawable.indicators_default);
            if (i == 0) {
                indicators[i].setBackgroundResource(R.drawable.indicators_now);
            }
            indicatorLayout.addView(indicators[i], layoutParams);
        }
        pagerAdapter = new BasePagerAdapter(views);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setAdapter(pagerAdapter); // 设置适配器
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        if (loadImgBeans.size() <= 1) {
            startButton.setVisibility(View.VISIBLE);
            if (loadImgBeans.size() <= 0) {
                startButton.setBackgroundResource(R.drawable.welcome_bg);
                startButton.performClick();
            } else {
                startButton.setBackgroundResource(R.color.transparent);
            }
        }
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //requestCode标示请求的标示   resultCode表示有数据
        if (requestCode == LoginActivity.LOGIN && resultCode == RESULT_OK) {
            if (DBUtil.getLoginUser() == null) {
                PageCtrl.start2LoginAct(WelcomeActivity.this);
            } else {
                PageCtrl.start(WelcomeActivity.this, MainTabActivity.class,
                        true, null, null);
            }
        }
    }

    // 按钮的点击事件
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_Button) {

            if (DBUtil.getLoginUser() == null) {
                PageCtrl.start2LoginAct(WelcomeActivity.this);
            } else {
                PageCtrl.start(WelcomeActivity.this, MainTabActivity.class,
                        true, null, null);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    // 监听viewpage
    @Override
    public void onPageSelected(int arg0) {
        // 显示最后一个图片时显示按钮
        if (arg0 == indicators.length - 1) {
            startButton.setVisibility(View.VISIBLE);
        } else {
            startButton.setVisibility(View.INVISIBLE);
        }
        // 更改指示器图片
        for (int i = 0; i < indicators.length; i++) {
            indicators[arg0].setBackgroundResource(R.drawable.indicators_now);
            if (arg0 != i) {
                indicators[i]
                        .setBackgroundResource(R.drawable.indicators_default);
            }
        }
    }

    private void loadData() {
        PotatoRequest req = StickerRequestBuilder.getLoadImgRequest();
        PotatoRequestManager.requestData(req,
                new PotatoRequestManager.DataLoadListener() {
                    @Override
                    public void onSuccess(int statusCode, String content) {
                        // TODO Auto-generated method stub
                        if (!TextUtils.isEmpty(content)) {
                            LoadImgParser parser = new LoadImgParser(
                                    content);
                            if (parser.isSucc()) {
                                if (parser.loadImgBeans != null) {
                                    loadImgBeans = parser.loadImgBeans;
                                }
                            } else {

                            }

                        }
                        initView();
                        // uploadPics(userlogined.getUid(), addedBitmap);
                    }

                    @Override
                    public void onFailure(Throwable error, String errMsg) {
                        // TODO Auto-generated method stub
                        initView();
                    }

                    @Override
                    public void onCacheLoaded(String content) {
                        // TODO Auto-generated method stub
                        // dialog.dismiss();
                        if (!TextUtils.isEmpty(content)) {
                            LoadImgParser parser = new LoadImgParser(
                                    content);
                            if (parser.isSucc()) {
                                if (parser.loadImgBeans != null) {
                                    loadImgBeans = parser.loadImgBeans;
                                }
                            } else {

                            }
                        }
                        initView();

                    }
                }, PotatoRequestManager.CACHE_TYPE_IGNORE_TIME);
    }

    //引导页使用的pageview适配器
    public class BasePagerAdapter extends PagerAdapter {
        private List<View> views = new ArrayList<View>();

        public BasePagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(views.get(position));
            return views.get(position);
        }
    }


    // 进入主程序的方法
    private void into() {
//		if (netManager.isOpenNetwork()) {

        // 设置动画效果是alpha，在anim目录下的alpha.xml文件中定义动画效果
        animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        // 给view设置动画效果
        iv_splash.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 如果第一次安装启动，则显示页面
                if (SPUtils.read(context, SPUtils.SP_NAME_DEFAULT,
                        SPUtils.PREF_KEY_ISFIRST, true)) {
                    SPUtils.write(WelcomeActivity.this,
                            SPUtils.SP_NAME_DEFAULT, SPUtils.PREF_KEY_ISFIRST,
                            false);
                    iv_splash.setVisibility(View.GONE);
                } else {
                    if (DBUtil.getLoginUser() == null) {
                        PageCtrl.start2LoginAct(WelcomeActivity.this);
                    } else {
                        PageCtrl.start(WelcomeActivity.this, MainTabActivity.class, true, null, null);
                    }
                }

            }
        }, TIME);
    }
}
