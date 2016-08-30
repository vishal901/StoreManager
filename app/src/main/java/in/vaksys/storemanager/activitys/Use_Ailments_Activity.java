package in.vaksys.storemanager.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.storemanager.R;

/**
 * Created by lenovoi3 on 8/10/2016.
 */
public class Use_Ailments_Activity extends AppCompatActivity {
    @Bind(R.id.fab_next)
    FloatingActionButton fabNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_use_ailment);
        ButterKnife.bind(this);

        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Use_Ailments_Activity.this, Ailments_Activity.class);
                startActivity(i);
            }
        });

    }
}
