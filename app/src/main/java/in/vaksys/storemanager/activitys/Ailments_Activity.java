package in.vaksys.storemanager.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.storemanager.R;
import in.vaksys.storemanager.adapter.Ailments_Adapter;
import in.vaksys.storemanager.extra.ApiClient;
import in.vaksys.storemanager.extra.ApiInterface;
import in.vaksys.storemanager.extra.AppConfig;
import in.vaksys.storemanager.extra.DividerItemDecoration;
import in.vaksys.storemanager.extra.MyApplication;
import in.vaksys.storemanager.extra.PreferenceHelper;
import in.vaksys.storemanager.model.ailment;
import in.vaksys.storemanager.response.Ailments;
import in.vaksys.storemanager.response.RegisterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

/**
 * Created by lenovoi3 on 7/22/2016.
 */
public class Ailments_Activity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.edt_search_ailments)
    EditText edtSearchAilments;
    @Bind(R.id.rec_aliments)
    RecyclerView recAliments;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.btn_submit_aliment)
    Button btnSubmitAliment;
    @Bind(R.id.txt_hide)
    TextView txtHide;

    private List<ailment> ailmentList;
    private Ailments_Adapter adapter;
    private PreferenceHelper preferenceHelper;
    private String user_fname, user_gender, user_adddress, user_phone, user_email, st_findustitle, st_lastname, user_ailment_key, st_address2, user_dob, user_city, user_state, st_zip, st_landline, st_findus_word;
    MyApplication myApplication;
    private String barnch_id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ailments);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Stetho.initializeWithDefaults(this);
        myApplication = MyApplication.getInstance();
        myApplication.createDialog(Ailments_Activity.this, false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("AILMENTS");


        init();
    }

    private void init() {

        preferenceHelper = new PreferenceHelper(Ailments_Activity.this, "login");

        user_fname = preferenceHelper.LoadStringPref(AppConfig.PREF_USER_NAME, "");
        user_phone = preferenceHelper.LoadStringPref(AppConfig.PREF_USER_PHONE, "");
        user_email = preferenceHelper.LoadStringPref(AppConfig.PREF_USER_EMAIL, "");
        user_gender = preferenceHelper.LoadStringPref(AppConfig.PREF_USER_GENDER, "");
        user_adddress = preferenceHelper.LoadStringPref(AppConfig.PREF_USER_ADDRESS, "");
        barnch_id = preferenceHelper.LoadStringPref(AppConfig.PREF_BRANCH_ID, "");


        user_dob = preferenceHelper.LoadStringPref(AppConfig.PREF_USER_DOB, "");
        user_city = preferenceHelper.LoadStringPref(AppConfig.PREF_USER_CITY, "");
        user_state = preferenceHelper.LoadStringPref(AppConfig.PREF_USER_STATE, "");
        st_zip = preferenceHelper.LoadStringPref(AppConfig.PREF_USER_ZIPCODE, "");
        st_landline = preferenceHelper.LoadStringPref(AppConfig.PREF_USER_LANDLINE, "");
        st_findus_word = preferenceHelper.LoadStringPref(AppConfig.PREF_USER_ST_FINDUS_WORD, "");
        st_address2 = preferenceHelper.LoadStringPref(AppConfig.PREF_USER_ADDRESS1, "");
        st_lastname = preferenceHelper.LoadStringPref(AppConfig.PREF_USER_LASTNAME, "");
        st_findustitle = preferenceHelper.LoadStringPref(AppConfig.PREF_USER_FINDUS_TITLE, "");


        btnSubmitAliment.setOnClickListener(this);

        recAliments.addItemDecoration(new DividerItemDecoration(Ailments_Activity.this));
        recAliments = (RecyclerView) findViewById(R.id.rec_aliments);
        recAliments.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Ailments_Activity.this);
        recAliments.setLayoutManager(layoutManager);

        getAilments();


        edtSearchAilments.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence query, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub

                query = query.toString().toLowerCase();

                final List<ailment> filteredList = new ArrayList<>();

                for (int i = 0; i < ailmentList.size(); i++) {

                    final String text = ailmentList.get(i).getAilment_title().toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(ailmentList.get(i));
                    }
                }
                recAliments.addItemDecoration(new DividerItemDecoration(Ailments_Activity.this));
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Ailments_Activity.this);
                recAliments.setLayoutManager(layoutManager);
                final Ailments_Adapter adapter = new Ailments_Adapter(Ailments_Activity.this, filteredList);
                recAliments.setAdapter(adapter);
                adapter.notifyDataSetChanged();  // data set changed
            }
        });
    }

    private void getAilments() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Ailments> call = apiInterface.ailments();

        call.enqueue(new Callback<Ailments>() {
            @Override
            public void onResponse(Call<Ailments> call, Response<Ailments> response) {

                if (response.isSuccessful()) {

                    if (response.code() == 200) {


                        progressBar.setVisibility(View.GONE);

                        List<Ailments.DataBean> as = response.body().getData();

                        if (!response.body().isError()) {

                            ailmentList = new ArrayList<>();


                            for (Ailments.DataBean a : as) {

                                ailment ailment = new ailment();
                                ailment.setAilment_id(a.getId());
                                ailment.setAilment_code(a.getCode());
                                ailment.setAilment_title(a.getTitle());
                                ailmentList.add(ailment);

                                System.out.println(a.getTitle());
                            }


                            adapter = new Ailments_Adapter(Ailments_Activity.this, ailmentList);
                            recAliments.setAdapter(adapter);

                        } else {
                            Toast.makeText(Ailments_Activity.this, "", Toast.LENGTH_SHORT).show();
                        }


                    } else {

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Ailments_Activity.this, "code errroor", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Ailments_Activity.this, "No respone complet", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<Ailments> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Ailments_Activity.this, "No Internet Access", Toast.LENGTH_SHORT).show();

            }
        });

//        ailment = new ArrayList<>();
//        ailment.add("Arthritis/Rheumatism");
//        ailment.add("Bad Posture");
//        ailment.add("Diabetice");
//        ailment.add("Frequent Stomach Pains");
//        ailment.add("For Mind Concentration");
//        ailment.add("Forzen Shouder");
//        ailment.add("High Blood Pressure");
//        ailment.add("Hip Pain");
//        ailment.add("Leg Pain");
//        ailment.add("Low Energy");
//        ailment.add("Lower Back Pain");
//        ailment.add("Migraine");
//        ailment.add("Neck Pain");
//        ailment.add("Poor Indigestion");
//        ailment.add("Scoliosis");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Ailments_Activity.this, NewMemberActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(Ailments_Activity.this, NewMemberActivity.class);
                startActivity(intent);
                finish();

                break;


            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {


        for (ailment aa : adapter.getAllData()) {

            txtHide.append(aa.getAilment_title());
            txtHide.append(",");

        }

        ApiClient.showLog("data all selected", txtHide.getText().toString());

        if (txtHide.getText().toString().equalsIgnoreCase("")) {

            Toast.makeText(Ailments_Activity.this, "Please Select One Ailment On List", Toast.LENGTH_SHORT).show();
        } else {

            customer_detail_call(user_fname, user_phone, user_gender, user_email, user_adddress, txtHide.getText().toString(), barnch_id);

        }


    }

    private void customer_detail_call(String user_fname, String user_phone, String user_gender, String user_email, String user_adddress, String user_ailment_key, String barnch_id) {

        myApplication.DialogMessage("Loading...");
        myApplication.showDialog();


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RegisterResponse> responseCall = apiInterface.REGISTER_RESPONSE_CALL(barnch_id,
                user_fname, user_gender, user_adddress, user_phone, user_email, user_ailment_key,
                st_lastname, user_dob, st_address2, user_city, user_state, st_zip, st_findustitle, "", st_findus_word, st_landline);

        responseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                ApiClient.showLog("code", "" + response.code());

                if (response.code() == 200) {

                    myApplication.hideDialog();

                    if (!response.body().isError()) {

                        Toast.makeText(Ailments_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        preferenceHelper.initPref();
                        preferenceHelper.SaveStringPref(AppConfig.PREF_CUSTOMER_ID, response.body().getCustomerId());
                        preferenceHelper.ApplyPref();

                        myApplication.hideDialog();
                        Intent intent = new Intent(Ailments_Activity.this, Pain_Activity.class);
                        startActivity(intent);
                        finish();


                    } else {

                        Toast.makeText(Ailments_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                    }
                } else {
                    myApplication.hideDialog();
                    Toast.makeText(Ailments_Activity.this, "something worng", Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                myApplication.hideDialog();
                Toast.makeText(Ailments_Activity.this, "No Internet Access", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
