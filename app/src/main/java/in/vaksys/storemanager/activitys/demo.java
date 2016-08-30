package in.vaksys.storemanager.activitys;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.storemanager.R;
import in.vaksys.storemanager.extra.MyDrawView;

/**
 * Created by lenovoi3 on 8/9/2016.
 */
public class demo extends AppCompatActivity {
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(R.id.draw)
    MyDrawView draw;
    @Bind(R.id.viewgroup)
    FrameLayout viewgroup;
    @Bind(R.id.txt_fornt_image)
    TextView btnEditOrderdetils;
    @Bind(R.id.txt_back_image)
    TextView btnDeleteOrderdetils;
    @Bind(R.id.btn_save_pain)
    Button btnSavePain;
    @Bind(R.id.progressBar2)
    ProgressBar progressBar2;
    String timeStamp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pain_activity);
        ButterKnife.bind(this);

        btnSavePain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("dcbh","dfhgdyfh");

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
                File myPath = new File(folder, "img"+".jpg");
                FileOutputStream fos = null;

                try {
                    fos = new FileOutputStream(myPath);
                    b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    MediaStore.Images.Media.insertImage(getContentResolver(), b,
                            "Screen", "screen");

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
    }
}
