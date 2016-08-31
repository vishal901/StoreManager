package in.vaksys.storemanager.activitys;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.vaksys.storemanager.R;
import in.vaksys.storemanager.adapter.Branch_Adapter;
import in.vaksys.storemanager.extra.ApiClient;
import in.vaksys.storemanager.extra.ApiInterface;
import in.vaksys.storemanager.extra.DividerItemDecoration;
import in.vaksys.storemanager.model.branch;
import in.vaksys.storemanager.response.GetBranchList;
import in.vaksys.storemanager.response.UploadImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovoi3 on 8/31/2016.
 */
public class Branch_Activity extends AppCompatActivity {
    private Dialog dialog;
    private RecyclerView rec_get_branch;
    private ProgressBar pbbranch;
    private List<branch> addbranch;
    private Branch_Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_acticity);

        getbranchlist();
    }

    private void getbranchlist() {

        dialog = new Dialog(Branch_Activity.this);
        dialog.setTitle("Branch List");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_get_branch);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        rec_get_branch = (RecyclerView) dialog.findViewById(R.id.rec_get_branch);
        pbbranch = (ProgressBar) dialog.findViewById(R.id.pb_branch);


        Get_Branch_Network_Call();

    }

    private void Get_Branch_Network_Call() {


        pbbranch.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetBranchList> getBranchAdminCall = apiInterface.GET_BRANCH_CALL();

        getBranchAdminCall.enqueue(new Callback<GetBranchList>() {
            @Override
            public void onResponse(Call<GetBranchList> call, Response<GetBranchList> response) {

                ApiClient.showLog("code", "" + response.code());
                if (response.code() == 200) {
                    pbbranch.setVisibility(View.GONE);

                    List<GetBranchList.BranchBean> as = response.body().getBranch();
                    if (!response.body().isError()) {

                        addbranch = new ArrayList<branch>();

                        for (GetBranchList.BranchBean a : as) {

                            branch data = new branch();
                            data.setId_branch((a.getId()));
                            data.setBranchname(a.getName());
                            data.setBranchaddress(a.getAddress());
                            addbranch.add(data);

                        }
                      //  rec_get_branch.addItemDecoration(new DividerItemDecoration(Branch_Activity.this));
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Branch_Activity.this);
                        rec_get_branch.setLayoutManager(layoutManager);
                        adapter = new Branch_Adapter(Branch_Activity.this, addbranch,dialog);
                        rec_get_branch.setAdapter(adapter);

                        //    Toast.makeText(Branch_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(Branch_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } else {
                    pbbranch.setVisibility(View.GONE);
                    Toast.makeText(Branch_Activity.this, "Somthing worng", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<GetBranchList> call, Throwable t) {
                pbbranch.setVisibility(View.GONE);
                Toast.makeText(Branch_Activity.this, "No Internet Accesss", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (dialog.isShowing()){
            finish();
        }

    }
}
