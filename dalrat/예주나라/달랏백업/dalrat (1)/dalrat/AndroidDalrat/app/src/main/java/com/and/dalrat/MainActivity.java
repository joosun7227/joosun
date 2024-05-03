package com.and.dalrat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    
    WebView webView;
    ProgressBar progressBar;
    CustomSwipeRefreshLayout refreshLayout;
    ProgressDialog progressDialog;
    
    public static Context mContext;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    //카메라(이미지)업로드
    private final static int FCR = 1;
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mContext = this;

        webView = (WebView) findViewById(R.id.webViewMain);

        progressBar = (ProgressBar) findViewById(R.id.progressBarMain);

        refreshLayout = (CustomSwipeRefreshLayout) findViewById(R.id.refreshMain);

        refreshLayout.setOnRefreshListener(new CustomSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //새로고침 소스
                webView.reload();
            }

        });

        getToken();
        grantFileUploadPermission();

        // URL 세팅
        String sUrl = getIntent().getStringExtra("sUrl");
        if(sUrl==null) {
            sUrl = getResources().getString(R.string.default_url);
        }

        // 웹뷰 옵션세팅
        setWebview(webView);

        // 웹뷰 로드
        webView.loadUrl(sUrl);

    }
    
    public void refresh(){
        webView.reload();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setWebview(final WebView webView)
    {
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        set.setLoadWithOverviewMode(true); // 한페이지에 전체화면이 다 들어가도록
        set.setJavaScriptCanOpenWindowsAutomatically(true);
        set.setSupportMultipleWindows(true); // <a>태그에서 target="_blank" 일 경우 외부 브라우저를 띄움
        set.setUserAgentString(webView.getSettings().getUserAgentString() + getResources().getString(R.string.user_agent));
        set.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        set.setGeolocationEnabled(true);
        set.setDomStorageEnabled(true); //Javascript error 무시
        set.setTextZoom(100);


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setMessage("SSL 경고 : 계속하시겠습니까?");
                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                    }
                });
                final android.app.AlertDialog dialog = builder.create();
                dialog.show();
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                //kcp 결제 처리
                if (url != null && (url.startsWith("vguardend:") )){
                    return false;
                }

                if (url != null && (url.startsWith("intent:") || (url.startsWith("ahnlabv3mobileplus:")))) {
                    Log.e("1번 intent://" , url);
                    try {
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                        if (existPackage != null) {
                            view.getContext().startActivity(intent);
                        } else {
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                            marketIntent.setData(Uri.parse("market://details?id="+intent.getPackage()));
                            view.getContext().startActivity(marketIntent);
                        }
                        return true;
                    }catch (Exception e) {
                        Log.e(TAG,e.getMessage());
                    }
                } else if (url != null && url.startsWith("market://")) {
                    try {
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        if (intent != null) {
                            view.getContext().startActivity(intent);
                        }
                        return true;
                    } catch (URISyntaxException e) {
                        Log.e(TAG,e.getMessage());
                    }
                }

                view.loadUrl(url);
                return true;
            }

            public void onPageStarted(WebView view, String url,
                                      Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                //refreshLayout.setRefreshing(true);

                clearApplicationData(mContext);
                putSP("cur_url", url);
            }

            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);

                progressBar.setVisibility(View.INVISIBLE);
                refreshLayout.setRefreshing(false);
                refreshLayout.setEnabled(true);

                String cur_url = getSP("cur_url","EMPTY");

                //if(cur_url.contains("index.php")) {
                if(cur_url.equals("https://www.dalrat.com/shop/")) {
                    sendDeviceInfo();
                }

                log(cur_url);
                if(cur_url.contains("html")) {
                    view.loadUrl(url.replace("html","php"));
                }

                if(cur_url.equals("https://www.dalrat.com/")) {
                    view.loadUrl("https://www.dalrat.com/index.php");
                }
            }

            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

        });

        webView.setWebChromeClient(new WebChromeClient() {

            //<a>태그에서 target="_blank" 일 경우 외부 브라우저를 띄우기 위해 필요한override
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView newWebView = new WebView(MainActivity.this);
                WebSettings webSettings = newWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);

                ((WebView.WebViewTransport) resultMsg.obj).setWebView(newWebView);
                resultMsg.sendToTarget();
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
            }

            //For Android 5.0+
            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    WebChromeClient.FileChooserParams fileChooserParams) {


                grantFileUploadPermission();

                if (mUMA != null) {
                    mUMA.onReceiveValue(null);
                }

                mUMA = filePathCallback;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(MainActivity.this.getPackageManager()) != null) {

                    File photoFile = null;

                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCM);
                    } catch (IOException ex) {
                        Log.e(TAG, "Image file creation failed", ex);
                    }
                    if (photoFile != null) {
                        mCM = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");
                Intent[] intentArray;

                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }


                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, FCR);

                return true;
            }
        });

        webView.addJavascriptInterface(new JavaScriptInterface(), getResources().getString(R.string.js_name));

    }

    private boolean grantFileUploadPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }else{
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
                return false;
            }
        }else{
            return true;
        }
    }

    private File createImageFile() throws IOException {

        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private class JavaScriptInterface {

        @JavascriptInterface
        public void appLogin(final String data) {

            //T.show(mContext, data);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (data.startsWith("DATA_DETAIL__")) {

                        String[] detail = data.split("__");
                        String type = detail[1];

                        switch (type) {

                            case "CHECK_APP_INSTALLED":
                                break;

                            default :
                                break;

                        }
                    }
                }
            });
        }
    }

    public void sendDeviceInfo(){

        String device_id;
        String device_token;
        String device_model;
        String app_version;

        device_id = getSP("device_id","");

        if(TextUtils.isEmpty(device_id))
        {
            String new_device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            String new_device_token = FirebaseInstanceId.getInstance().getToken();
            String new_device_model = Build.BRAND + "/" + Build.MODEL + "/" + Build.ID + "/" + Build.VERSION.RELEASE;
            String new_app_version = BuildConfig.VERSION_NAME;

            putSP("device_id", new_device_id);
            putSP("device_token", new_device_token);
            putSP("device_model", new_device_model);
            putSP("app_version", new_app_version);
        }

        device_id = getSP("device_id","");
        device_token = getSP("device_token","");
        device_model = getSP("device_model","");
        app_version = getSP("app_version","");

        //앱버젼 변경시 업데이트
        String new_app_version = BuildConfig.VERSION_NAME;
        if(new_app_version!=app_version) {
            app_version = new_app_version;
            putSP("app_version", new_app_version);
        }


        String data = "act=setAppDeviceInfo&device_type=Android"
                + "&device_id="+device_id
                + "&device_token="+device_token
                +"&device_model="+device_model
                +"&app_version="+app_version
                ;

        String enc_data = Base64.encodeToString(data.getBytes(), 0);

        log("jsNativeToServer(enc_data)");
        doJavascript("javascript:jsNativeToServer('" + enc_data + "')");

        return;

    }


    @Override
    public void onBackPressed() {


        if(
                webView.getUrl().endsWith("https://www.dalrat.com") ||
                webView.getUrl().endsWith("https://www.dalrat.com/") ||
                webView.getUrl().endsWith("https://www.dalrat.com/index.php") ||
                webView.getUrl().endsWith("https://www.dalrat.com/shop/") ||
                webView.getUrl().endsWith("https://www.dalrat.com/shop/index.php") ||
                webView.getUrl().endsWith(getResources().getString(R.string.default_url))
        )
        {
            finishApp();
        }
        else if (webView.canGoBack()) {
            webView.goBack();
            //doJavascript("javascript:androidBackBtnClicked()");
        }
        else {
            finishApp();
        }
        
    }

    public void finishApp()
    {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 뒤로가기 버튼을 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //카메라(이미지)업로드
        if (requestCode == FCR)
        {
            Uri[] results = null;

            if (resultCode == Activity.RESULT_OK) {
                if (null == mUMA) {
                    return;
                }
                if (data == null) {
                    //Capture Photo if no image available
                    if (mCM != null) {
                        results = new Uri[]{Uri.parse(mCM)};
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            mUMA.onReceiveValue(results);
            mUMA = null;
        }

    }

    public void doJavascript(final String msg){

        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(msg);
            }
        });

    }

    public static void clearApplicationData(Context context) {
        File cache = context.getCacheDir();
        File appDir = new File(cache.getParent());

        long size = (long) getDirSize(appDir);

        // 캐시(Cache) 용량이 20Mb 이상일 경우 삭제
        if (appDir.exists() && size > 20000000) {
            String[] children = appDir.list();
            for (String s : children) {

                //shared_prefs 파일은 지우지 않도록 설정
                if(s.equals("shared_prefs")) continue;

                deleteDir(new File(appDir, s));
                Log.d("TTT", "File /data/data/"+context.getPackageName()+"/" + s + size + " DELETED");
            }
        }
    }

    public static long getDirSize(File dir){
        long size = 0;
        for (File file : dir.listFiles()) {
            if (file != null && file.isDirectory()) {
                size += getDirSize(file);
            } else if (file != null && file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public void getToken(){
        Task<InstanceIdResult> id = FirebaseInstanceId.getInstance().getInstanceId();
        id.addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                // 토큰 확인
                if (task.isSuccessful()) {
                    InstanceIdResult result = task.getResult();
                    String id = result.getId();
                    String token = result.getToken();

                    Log.d(TAG, "Token id : " + id);
                    Log.d(TAG, "Token : " + token);

                    putSP("device_token", task.getResult().getToken());

                } else {
                    Log.d(TAG, "Token Exception : " + task.getException().toString());
                }
            }
        });
        /*
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.d("TTTL token : ", token);
                        putSP("device_token", token);
                    }
                });

         */
    }


    public void putSP(String name, String value){
        SharedPreferences put_pref = mContext.getSharedPreferences("shared_pref", MODE_PRIVATE);
        SharedPreferences.Editor put_editor = put_pref.edit();
        put_editor.putString(name, value);
        log("putSP - " + name + ":" + value);
        put_editor.commit();
    }


    public String getSP(String name, String default_result) {
        SharedPreferences get_pref = mContext.getSharedPreferences("shared_pref", MODE_PRIVATE);
        String result = get_pref.getString(name, default_result);
        log("getSP - " + name + ":" + result);
        return result;
    }

    public void log(String string){

        if(string.length()>350) {
            string = string.substring(0, 350);
        }
        Log.d("TTT", String.valueOf(string));
    }

}

