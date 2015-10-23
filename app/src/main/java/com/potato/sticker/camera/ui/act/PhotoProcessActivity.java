package com.potato.sticker.camera.ui.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imagezoom.ImageViewTouch;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.potato.chips.util.ImageLoaderUtil;
import com.potato.chips.util.QiniuUtil;
import com.potato.chips.util.StringUtils;
import com.potato.chips.util.UIUtils;
import com.potato.library.net.Request;
import com.potato.library.net.RequestManager;
import com.potato.library.util.L;
import com.potato.library.view.dialog.DialogUtil;
import com.potato.sticker.R;
import com.potato.sticker.camera.common.FileUtils;
import com.potato.sticker.camera.common.ImageUtils;
import com.potato.sticker.camera.common.TimeUtils;
import com.potato.sticker.camera.customview.CommonTitleBar;
import com.potato.sticker.camera.customview.LabelSelector;
import com.potato.sticker.camera.customview.LabelView;
import com.potato.sticker.camera.customview.MyHighlightView;
import com.potato.sticker.camera.customview.MyImageViewDrawableOverlay;
import com.potato.sticker.camera.data.bean.Addon;
import com.potato.sticker.camera.data.bean.TagItem;
import com.potato.sticker.camera.ui.adapter.FilterAdapter;
import com.potato.sticker.camera.ui.adapter.StickerToolAdapter;
import com.potato.sticker.camera.util.App;
import com.potato.sticker.camera.util.AppConstants;
import com.potato.sticker.camera.util.CameraManager;
import com.potato.sticker.camera.util.EffectService;
import com.potato.sticker.camera.util.EffectUtil;
import com.potato.sticker.camera.util.FilterEffect;
import com.potato.sticker.camera.util.GPUImageFilterTools;
import com.potato.sticker.main.data.bean.PicBean;
import com.potato.sticker.main.data.bean.TagBean;
import com.potato.sticker.main.data.bean.TopicBean;
import com.potato.sticker.main.data.bean.UserBean;
import com.potato.sticker.main.data.db.DBUtil;
import com.potato.sticker.main.data.request.StickerRequestBuilder;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.sephiroth.android.library.widget.HListView;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

/**
 * 图片处理界面
 * Created by sky on 2015/7/8.
 * Weibo: http://weibo.com/2030683111
 * Email: 1132234509@qq.com
 */
public class PhotoProcessActivity extends CameraBaseActivity {


    @InjectView(R.id.title_layout)
    CommonTitleBar titleBar;
    //滤镜图片
    @InjectView(R.id.gpuimage)
    GPUImageView mGPUImageView;
    //绘图区域
    @InjectView(R.id.drawing_view_container)
    ViewGroup drawArea;
    //内容区
    @InjectView(R.id.tv_content)
    TextView tv_content;
    //底部按钮
    @InjectView(R.id.bt_content)
    TextView bt_content;
    @InjectView(R.id.sticker_btn)
    TextView stickerBtn;
    @InjectView(R.id.filter_btn)
    TextView filterBtn;
    @InjectView(R.id.text_btn)
    TextView labelBtn;
    //工具区
    @InjectView(R.id.list_tools)
    HListView bottomToolBar;
    @InjectView(R.id.toolbar_area)
    ViewGroup toolArea;
    private MyImageViewDrawableOverlay mImageView;
    private LabelSelector labelSelector;

    //当前选择底部按钮
    private TextView currentBtn;
    //当前图片
    private Bitmap currentBitmap;
    //用于预览的小图片
    //目前没用，直接用的大图，节省内存
    private Bitmap smallImageBackgroud;
    //小白点标签
    private LabelView emptyLabelView;

    private List<LabelView> labels = new ArrayList<LabelView>();

    //标签区域
    private View commonLabelArea;

    public UserBean userBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_process);
        ButterKnife.inject(this);
        EffectUtil.clear();
        initView();
        initEvent();

        setCurrentBtn(bt_content);

        userBean = DBUtil.getLoginUser();

        /*ImageUtils.asyncLoadImage(this, getIntent().getData(), new ImageUtils.LoadImageCallback() {
            @Override
            public void callback(Bitmap result) {
                currentBitmap = result;
                mGPUImageView.setImage(currentBitmap);
            }
        });

        ImageUtils.asyncLoadSmallImage(this, getIntent().getData(), new ImageUtils.LoadImageCallback() {
            @Override
            public void callback(Bitmap result) {
                smallImageBackgroud = result;
            }
        });*/
        ImageLoaderUtil.loadImageAsync(getIntent().getData().toString(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                currentBitmap = bitmap;

                mGPUImageView.setImage(currentBitmap);
                L.i("onLoadingComplete","ImageSize(500, 500)");
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        },new ImageSize(500, 500));
        /*ImageLoaderUtil.loadImageAsync(getIntent().getData().toString(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                smallImageBackgroud = bitmap;
                L.i("onLoadingComplete","ImageSize(300, 300)");
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        }, new ImageSize(300, 300));*/


    }

    private void initView() {
        //添加贴纸水印的画布
        View overlay = LayoutInflater.from(PhotoProcessActivity.this).inflate(
                R.layout.view_drawable_overlay, null);
        mImageView = (MyImageViewDrawableOverlay) overlay.findViewById(R.id.drawable_overlay);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(App.getApp().getScreenWidth(),
                App.getApp().getScreenWidth());
        mImageView.setLayoutParams(params);
        overlay.setLayoutParams(params);
        drawArea.addView(overlay);
        //添加标签选择器
        RelativeLayout.LayoutParams rparams = new RelativeLayout.LayoutParams(App.getApp().getScreenWidth(), App.getApp().getScreenWidth());
        labelSelector = new LabelSelector(this);
        labelSelector.setLayoutParams(rparams);
        drawArea.addView(labelSelector);
        labelSelector.hide();

        //初始化滤镜图片
        mGPUImageView.setLayoutParams(rparams);


        //初始化空白标签
        emptyLabelView = new LabelView(this);
        emptyLabelView.setEmpty();
        EffectUtil.addLabelEditable(mImageView, drawArea, emptyLabelView,
                mImageView.getWidth() / 2, mImageView.getWidth() / 2);
        emptyLabelView.setVisibility(View.INVISIBLE);

        //初始化推荐标签栏
        commonLabelArea = LayoutInflater.from(PhotoProcessActivity.this).inflate(
                R.layout.view_label_bottom, null);
        commonLabelArea.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        toolArea.addView(commonLabelArea);
        commonLabelArea.setVisibility(View.GONE);

        tv_content.setVisibility(View.VISIBLE);


    }

    private void initEvent() {

        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextActivity.openTextEdit(PhotoProcessActivity.this, "", 100, AppConstants.ACTION_EDIT_CONTENT);
            }
        });

        bt_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PhotoProcessActivity.this.setCurrentBtn(bt_content)) {
                    return;
                }
                tv_content.setVisibility(View.VISIBLE);
                labelSelector.hide();
                bottomToolBar.setVisibility(View.GONE);
                emptyLabelView.setVisibility(View.GONE);
                commonLabelArea.setVisibility(View.GONE);
//                EditTextActivity.openTextEdit(PhotoProcessActivity.this, "", 100, AppConstants.ACTION_EDIT_CONTENT);
            }
        });
        stickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PhotoProcessActivity.this.setCurrentBtn(stickerBtn)) {
                    return;
                }
                bottomToolBar.setVisibility(View.VISIBLE);
                labelSelector.hide();
                emptyLabelView.setVisibility(View.GONE);
                commonLabelArea.setVisibility(View.GONE);
                tv_content.setVisibility(View.GONE);
                PhotoProcessActivity.this.initStickerToolBar();
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PhotoProcessActivity.this.setCurrentBtn(filterBtn)) {
                    return;
                }
                bottomToolBar.setVisibility(View.VISIBLE);
                labelSelector.hide();
                emptyLabelView.setVisibility(View.INVISIBLE);
                commonLabelArea.setVisibility(View.GONE);
                tv_content.setVisibility(View.GONE);
                PhotoProcessActivity.this.initFilterToolBar();
            }
        });
        labelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PhotoProcessActivity.this.setCurrentBtn(labelBtn)) {
                    return;
                }
                bottomToolBar.setVisibility(View.GONE);
                tv_content.setVisibility(View.GONE);
                labelSelector.showToTop();
                commonLabelArea.setVisibility(View.VISIBLE);

            }
        });
        labelSelector.setTxtClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextActivity.openTextEdit(PhotoProcessActivity.this, "", 8, AppConstants.ACTION_EDIT_LABEL);
            }
        });
        labelSelector.setAddrClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextActivity.openTextEdit(PhotoProcessActivity.this, "", 8, AppConstants.ACTION_EDIT_LABEL_POI);

            }
        });
        mImageView.setOnDrawableEventListener(wpEditListener);
        mImageView.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() {
            @Override
            public void onSingleTapConfirmed() {
                emptyLabelView.updateLocation((int) mImageView.getmLastMotionScrollX(),
                        (int) mImageView.getmLastMotionScrollY());
                emptyLabelView.setVisibility(View.VISIBLE);

                labelSelector.showToTop();
                drawArea.postInvalidate();
            }
        });
        labelSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelSelector.hide();
                emptyLabelView.updateLocation((int) labelSelector.getmLastTouchX(),
                        (int) labelSelector.getmLastTouchY());
                emptyLabelView.setVisibility(View.VISIBLE);
            }
        });


        titleBar.setRightBtnOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userBean == null) {
                    UIUtils.toast(mContext, "请先登录");
                    return;
                }
                if(TextUtils.isEmpty(tv_content.getText())){
                    UIUtils.toast(mContext, "说点什么");
                    return;
                }
                savePicture();
            }
        });
    }

    //保存图片
    private void savePicture() {
        //加滤镜
        final Bitmap newBitmap = Bitmap.createBitmap(mImageView.getWidth(), mImageView.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newBitmap);
        RectF dst = new RectF(0, 0, mImageView.getWidth(), mImageView.getHeight());
        try {
            cv.drawBitmap(mGPUImageView.capture(), null, dst, null);
        } catch (InterruptedException e) {
            e.printStackTrace();
            cv.drawBitmap(currentBitmap, null, dst, null);
        }
        //加贴纸水印
        EffectUtil.applyOnSave(cv, mImageView);

        new SavePicToFileTask().execute(newBitmap);
    }

    private class SavePicToFileTask extends AsyncTask<Bitmap, Void, String> {
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(Bitmap... params) {
            String fileName = null;
            try {
                bitmap = params[0];

                String picName = TimeUtils.dtFormat(new Date(), "yyyyMMddHHmmss");
                fileName = ImageUtils.saveToFile(FileUtils.getInst().getPhotoSavedPath() + "/" + picName, false, bitmap);

            } catch (Exception e) {
                e.printStackTrace();
                toast("图片处理错误，请退出相机并重试", Toast.LENGTH_LONG);
            }
            return fileName;
        }

        @Override
        protected void onPostExecute(final String fileName) {
            super.onPostExecute(fileName);
            if (TextUtils.isEmpty(fileName)) {
                dismissProgressDialog();
                return;
            }
//            final String enCodeFileName = MD5Util.md5(fileName)+".jpg";
            final String enCodeFileName = StringUtils.getPicName(userBean.getId());
            L.i("uptoken", QiniuUtil.def_token);
            QiniuUtil.uploadManager.put(fileName, enCodeFileName, QiniuUtil.def_token,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo respInfo,
                                             JSONObject jsonData) {

                            if (respInfo.isOK()) {
                                try {
                                    String fileKey = jsonData.getString("key");
                                    String fileHash = jsonData.getString("hash");

                                    /*//将照片信息保存至sharedPreference
                                    //保存标签信息
                                    List<TagItem> tagInfoList = new ArrayList<TagItem>();
                                    for (LabelView label : labels) {
                                        tagInfoList.add(label.getTagInfo());
                                    }

                                    //将图片信息通过EventBus发送到MainActivity
                                    FeedItem feedItem = new FeedItem(tagInfoList, enCodeFileName);
                                    EventBus.getDefault().post(feedItem);*/

                                    ArrayList<PicBean> picBeans = new ArrayList<PicBean>();
                                    PicBean picBean = new PicBean();
                                    picBean.setImgPath(fileKey);

                                    //保存标签信息
                                    ArrayList<TagBean> tagBeans = new ArrayList<TagBean>();

                                    for (LabelView label : labels) {
                                        TagBean tagBean = new TagBean();
                                        tagBeans.add(TagBean.createFromTagItem(label.getTagInfo()));
                                    }
                                    picBean.setTagBeans(tagBeans);
                                    picBeans.add(picBean);

                                    TopicBean topicBean = new TopicBean();
                                    topicBean.setUserId(userBean.getId());
                                    topicBean.setContent(tv_content.getText().toString());
                                    topicBean.setTitle("帖子title");
                                    topicBean.setPicBeans(picBeans);

                                    Request request = StickerRequestBuilder.create(topicBean);
                                    RequestManager.requestData(request, new RequestManager.DataLoadListener() {
                                        @Override
                                        public void onCacheLoaded(String content) {

                                        }

                                        @Override
                                        public void onSuccess(int statusCode, String content) {

                                            dismissProgressDialog();
                                            UIUtils.toast(mContext, content);
                                            CameraManager.getInst().close();
                                        }

                                        @Override
                                        public void onFailure(Throwable error, String errMsg) {
                                            dismissProgressDialog();
                                            CameraManager.getInst().close();
                                        }
                                    }, RequestManager.CACHE_TYPE_NOCACHE);


                                } catch (JSONException e) {
                                    CameraManager.getInst().close();
                                }

                            }
                        }
                    }, null);
        }

    }


    public void tagClick(View v) {
        TextView textView = (TextView) v;
        TagItem tagItem = new TagItem(AppConstants.POST_TYPE_TAG, textView.getText().toString());
        addLabel(tagItem);
    }

    private MyImageViewDrawableOverlay.OnDrawableEventListener wpEditListener = new MyImageViewDrawableOverlay.OnDrawableEventListener() {
        @Override
        public void onMove(MyHighlightView view) {
        }

        @Override
        public void onFocusChange(MyHighlightView newFocus, MyHighlightView oldFocus) {
        }

        @Override
        public void onDown(MyHighlightView view) {

        }

        @Override
        public void onClick(MyHighlightView view) {
            labelSelector.hide();
        }

        @Override
        public void onClick(final LabelView label) {
            if (label.equals(emptyLabelView)) {
                return;
            }

            DialogUtil.createCommonDialog(mContext, new DialogUtil.CustomDialogCallBack() {
                @Override
                public void OkClick() {
                    EffectUtil.removeLabelEditable(mImageView, drawArea, label);
                    labels.remove(label);
                }

                @Override
                public void CancelClick() {

                }
            }, "温馨提示", "是否需要删除该标签！", "确定", "取消");
        }
    };

    private boolean setCurrentBtn(TextView btn) {
        if (currentBtn == null) {
            currentBtn = btn;
        } else if (currentBtn.equals(btn)) {
            return false;
        } else {
            currentBtn.setTextColor(Color.rgb(208, 190, 185));
            currentBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        Drawable myImage = getResources().getDrawable(R.drawable.select_icon);
        btn.setTextColor(Color.rgb(255, 255, 255));
        btn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, myImage);
        currentBtn = btn;
        return true;
    }


    //初始化贴图
    private void initStickerToolBar() {

        bottomToolBar.setAdapter(new StickerToolAdapter(PhotoProcessActivity.this, EffectUtil.addonList));
        bottomToolBar.setOnItemClickListener(new it.sephiroth.android.library.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> arg0,
                                    View arg1, int arg2, long arg3) {
                labelSelector.hide();
                Addon sticker = EffectUtil.addonList.get(arg2);
                EffectUtil.addStickerImage(mImageView, PhotoProcessActivity.this, sticker,
                        new EffectUtil.StickerCallback() {
                            @Override
                            public void onRemoveSticker(Addon sticker) {
                                labelSelector.hide();
                            }
                        });
            }
        });
        setCurrentBtn(stickerBtn);
    }


    //初始化滤镜
    private void initFilterToolBar() {
        final List<FilterEffect> filters = EffectService.getInst().getLocalFilters();
        final FilterAdapter adapter = new FilterAdapter(PhotoProcessActivity.this, filters, currentBitmap);
        bottomToolBar.setAdapter(adapter);
        bottomToolBar.setOnItemClickListener(new it.sephiroth.android.library.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                labelSelector.hide();
                if (adapter.getSelectFilter() != arg2) {
                    adapter.setSelectFilter(arg2);
                    GPUImageFilter filter = GPUImageFilterTools.createFilterForType(
                            PhotoProcessActivity.this, filters.get(arg2).getType());
                    mGPUImageView.setFilter(filter);
                    GPUImageFilterTools.FilterAdjuster mFilterAdjuster = new GPUImageFilterTools.FilterAdjuster(filter);
                    //可调节颜色的滤镜
                    if (mFilterAdjuster.canAdjust()) {
                        //mFilterAdjuster.adjust(100); 给可调节的滤镜选一个合适的值
                    }
                }
            }
        });
    }

    //添加标签
    private void addLabel(TagItem tagItem) {
        labelSelector.hide();
        emptyLabelView.setVisibility(View.INVISIBLE);
        if (labels.size() >= 5) {
            DialogUtil.createCommonDialog(mContext, new DialogUtil.CustomDialogCallBack() {
                @Override
                public void OkClick() {
                }

                @Override
                public void CancelClick() {

                }
            }, "温馨提示", "您只能添加5个标签！", "确定", "取消");
        } else {
            int left = emptyLabelView.getLeft();
            int top = emptyLabelView.getTop();
            if (labels.size() == 0 && left == 0 && top == 0) {
                left = mImageView.getWidth() / 2 - 10;
                top = mImageView.getWidth() / 2;
            }
            LabelView label = new LabelView(PhotoProcessActivity.this);
            label.init(tagItem);
            EffectUtil.addLabelEditable(mImageView, drawArea, label, left, top);
            labels.add(label);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        labelSelector.hide();
        super.onActivityResult(requestCode, resultCode, data);
        if (AppConstants.ACTION_EDIT_LABEL == requestCode && data != null) {
            String text = data.getStringExtra(AppConstants.PARAM_EDIT_TEXT);
            if (!TextUtils.isEmpty(text)) {
                TagItem tagItem = new TagItem(AppConstants.POST_TYPE_TAG, text);
                addLabel(tagItem);
            }
        } else if (AppConstants.ACTION_EDIT_LABEL_POI == requestCode && data != null) {
            String text = data.getStringExtra(AppConstants.PARAM_EDIT_TEXT);
            if (!TextUtils.isEmpty(text)) {
                TagItem tagItem = new TagItem(AppConstants.POST_TYPE_POI, text);
                addLabel(tagItem);
            }
        } else if (AppConstants.ACTION_EDIT_CONTENT == requestCode && data!= null) {
            String text = data.getStringExtra(AppConstants.PARAM_EDIT_TEXT);
            if (!TextUtils.isEmpty(text)) {
                tv_content.setText(text);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (currentBitmap != null) {
            currentBitmap.recycle();
            currentBitmap = null;
        }
//        if (smallImageBackgroud != null) {
//            smallImageBackgroud.recycle();
//            smallImageBackgroud = null;
//        }
    }


}
