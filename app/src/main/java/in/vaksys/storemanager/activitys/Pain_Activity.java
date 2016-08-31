package in.vaksys.storemanager.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.storemanager.R;
import in.vaksys.storemanager.extra.ApiClient;
import in.vaksys.storemanager.extra.ApiInterface;
import in.vaksys.storemanager.extra.AppConfig;
import in.vaksys.storemanager.extra.MyApplication;
import in.vaksys.storemanager.extra.MyDrawView;
import in.vaksys.storemanager.extra.PreferenceHelper;
import in.vaksys.storemanager.response.UploadImage;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovoi3 on 7/22/2016.
 */
public class Pain_Activity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(R.id.txt_fornt_image)
    TextView txtfrontimage;
    @Bind(R.id.txt_back_image)
    TextView txtbackimage;
    @Bind(R.id.draw)
    MyDrawView myDrawView;
    @Bind(R.id.btn_save_pain)
    Button btnSavePain;
    String timeStamp;

    private static final int REQUEST_PERMISSION = 0;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "";
    @Bind(R.id.progressBar2)
    ProgressBar progressBar2;
    @Bind(R.id.viewgroup)
    FrameLayout viewgroup;
    @Bind(R.id.btn_save_image_pain)
    TextView btnSaveImagePain;
    private Bitmap bitmap;

    File myPath;
    private String iamge_file_path;
    ArrayList<String> add_file_path = new ArrayList<>();

    private String customer_id;
    private MyApplication myApplication;
    private String FrontImage = "";
    private String BackImage = "";
    private String screentype = "FRONT";
    private String barnch_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pain_activity);
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("BODY PAIN");

        myApplication = MyApplication.getInstance();
        myApplication.createDialog(Pain_Activity.this, false);

        getdata();


    }

    private void getdata() {

        PreferenceHelper preferenceHelper = new PreferenceHelper(this, "login");
        customer_id = preferenceHelper.LoadStringPref(AppConfig.PREF_CUSTOMER_ID, "");


        txtbackimage.setBackgroundColor(Color.GRAY);

        ApiClient.showLog("customer id", customer_id);

        btnSavePain.setOnClickListener(this);
        txtbackimage.setOnClickListener(this);
        txtfrontimage.setOnClickListener(this);
        btnSaveImagePain.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_save_image_pain:

                if (ActivityCompat.checkSelfPermission(Pain_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestCameraPermission();
                } else {
                    Log.e(TAG,
                            "CAMERA permission has already been granted. Displaying camera preview.");
                    //  EasyImage.openCamera(MainActivity.this, EasyImageConfig.REQ_TAKE_PICTURE);

                    save_image();
                }

                break;


            case R.id.txt_fornt_image:

                imageView.setVisibility(View.VISIBLE);
                imageView2.setVisibility(View.GONE);
                myDrawView.clear(imageView);

                screentype = "FRONT";

                break;

            case R.id.txt_back_image:
                imageView.setVisibility(View.GONE);
                imageView2.setVisibility(View.VISIBLE);
                myDrawView.clear1(imageView2);
                screentype = "BACK";
                // imageView.setBackgroundResource(R.drawable.manbody2);
                break;

            case R.id.btn_save_pain:

//                UploadPainImage();

//                if (ActivityCompat.checkSelfPermission(Pain_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    requestCameraPermission();
//                } else {
//                    Log.e(TAG,
//                            "CAMERA permission has already been granted. Displaying camera preview.");
//                    //  EasyImage.openCamera(MainActivity.this, EasyImageConfig.REQ_TAKE_PICTURE);
//
//                    save_image();
//                }

                ApiClient.showLog("scrreen type",screentype);

                UploadPainImage(customer_id,screentype);



                break;
        }
    }


    private void UploadPainImage(final String customer_id, String screentype) {


        final HashMap<String, String> stringHashMap = new HashMap<>();
        stringHashMap.put("customer_id", customer_id);
        stringHashMap.put("type", screentype);


        myApplication.DialogMessage("Loading...");
        myApplication.showDialog();

        try {
            String a = add_file_path.get(0);
            ApiClient.showLog("path 1 -->", a);

            final String imgname = iamge_file_path.substring(iamge_file_path.lastIndexOf("/") + 1);

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), new File(a));

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", imgname, requestFile);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<UploadImage> uploadImageCall = apiInterface.getupload(body, stringHashMap);

            uploadImageCall.enqueue(new Callback<UploadImage>() {
                @Override
                public void onResponse(Call<UploadImage> call, Response<UploadImage> response) {

                    ApiClient.showLog("code1", "" + response.code());

                    if (response.code() == 200) {

                        if (!response.body().isError()) {

                            myApplication.hideDialog();
                            txtfrontimage.setEnabled(false);
                            txtbackimage.setEnabled(true);


                            Toast.makeText(Pain_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            txtfrontimage.setBackgroundColor(Color.GRAY);
                            txtbackimage.setBackground(getResources().getDrawable(R.drawable.btn_ractangle_border1));



                            try {
                                System.out.println(add_file_path.get(1));

                                secondcall(add_file_path.get(1), stringHashMap);
                            } catch (Exception e) {
                                e.printStackTrace();
                                myApplication.hideDialog();
                            }
                        } else {
                            txtfrontimage.setEnabled(true);
                            Toast.makeText(Pain_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        myApplication.hideDialog();
                        txtfrontimage.setEnabled(true);
                        Toast.makeText(Pain_Activity.this, "Something Data Worng", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<UploadImage> call, Throwable t) {
                    txtfrontimage.setEnabled(true);
                    myApplication.hideDialog();
                    Toast.makeText(Pain_Activity.this, "No Internet Access", Toast.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e) {

            e.printStackTrace();
            myApplication.hideDialog();

            Toast.makeText(Pain_Activity.this, "Please Save Image After Submit", Toast.LENGTH_SHORT).show();

        }


    }

    private void secondcall(String s, HashMap<String, String> customer_id) {

        myApplication.DialogMessage("Loading...");
        myApplication.showDialog();

        ApiClient.showLog("path 2 -->", s);

        final String imgname = s.substring(s.lastIndexOf("/") + 1);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), new File(s));

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", imgname, requestFile);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UploadImage> uploadImageCall = apiInterface.getupload(body, customer_id);

        uploadImageCall.enqueue(new Callback<UploadImage>() {
            @Override
            public void onResponse(Call<UploadImage> call, Response<UploadImage> response) {
                ApiClient.showLog("code1", "" + response.code());
                if (response.code() == 200) {
                    myApplication.hideDialog();
                    if (!response.body().isError()) {

                        Toast.makeText(Pain_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        add_file_path.clear();

                        Intent intent = new Intent(Pain_Activity.this,NewMemberActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(Pain_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(Pain_Activity.this, "Something Data Wrong", Toast.LENGTH_SHORT).show();
                    myApplication.hideDialog();
                }


            }

            @Override
            public void onFailure(Call<UploadImage> call, Throwable t) {
                myApplication.hideDialog();
                Toast.makeText(Pain_Activity.this, "No Internet Access", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void save_image() {


//        try {
//            URL url = new URL("haha");
//            System.out.println("y");
//        } catch (MalformedURLException e) {
//            System.out.println("n");
//        }

        View view1 = viewgroup;

        Bitmap b = Bitmap.createBitmap(view1.getWidth(), view1.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        view1.draw(canvas);

        timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss",
                Locale.getDefault()).format(new Date());

        File folder = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "mydata");

        // String extr = Environment.getExternalStorageDirectory().toString();
        myPath = new File(folder, timeStamp + ".png");
        FileOutputStream fos = null;

        try {


            fos = new FileOutputStream(myPath);

            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
//            MediaStore.Images.Media.insertImage(getContentResolver(), b,
//                    "Screen", "screen");
            Toast.makeText(getApplication(), "File saved : " + myPath, Toast.LENGTH_LONG).show();

            iamge_file_path = "" + myPath;
            add_file_path.add("" + myPath);


            ApiClient.showLog("path 0000--->", add_file_path.get(0));

            try {
                ApiClient.showLog("path ---1111>", add_file_path.get(1));
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Something worng plz try again", Toast.LENGTH_SHORT).show();
        }

    }


    private void requestCameraPermission() {
        Log.e(TAG, "CAMERA permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.e(TAG,
                    "Displaying camera permission rationale to provide additional context.");
            ActivityCompat.requestPermissions(Pain_Activity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        } else {

            Log.e(TAG,
                    "Displaying camera permission rationale to provide additional context.");
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }
        // END_INCLUDE(camera_permission_request)
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        System.out.println(grantResults.length);
        System.out.println(requestCode);
        System.out.println(grantResults[0]);
        if (requestCode == REQUEST_PERMISSION) {

            Log.e(TAG, "Received response for Camera permission request.");
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "CAMERA permission has now been granted. Showing preview.");

            } else {
                Log.e(TAG, "CAMERA permission was NOT granted.");

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cleardrow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.btn_clear_pain:
                myDrawView.clear(imageView);

                break;

            case android.R.id.home:
                Intent intent = new Intent(Pain_Activity.this, Ailments_Activity.class);
                startActivity(intent);
                finish();
                break;


            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Pain_Activity.this, Ailments_Activity.class);
        startActivity(intent);
        finish();
    }



}

