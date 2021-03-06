package in.vaksys.storemanager.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import butterknife.Bind;
import butterknife.ButterKnife;
import in.vaksys.storemanager.R;
import in.vaksys.storemanager.adapter.SpinnerTextAdapter;
import in.vaksys.storemanager.adapter.SpinnerTextAdapterstatic;
import in.vaksys.storemanager.extra.ApiClient;
import in.vaksys.storemanager.extra.ApiInterface;
import in.vaksys.storemanager.extra.AppConfig;
import in.vaksys.storemanager.extra.MyApplication;
import in.vaksys.storemanager.extra.PhoneNumberTextWatcher;
import in.vaksys.storemanager.extra.PreferenceHelper;
import in.vaksys.storemanager.model.findus;
import in.vaksys.storemanager.response.FindAs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMemberActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.edt_fname_user)
    EditText edtFirstNameUser;
    @Bind(R.id.edt_lname_user)
    EditText edtLastNameUser;
    @Bind(R.id.sp_day)
    Spinner spDay;
    @Bind(R.id.sp_month)
    Spinner spMonth;
    @Bind(R.id.sp_Year)
    Spinner spYear;
    @Bind(R.id.toggle_male_female)
    ToggleSwitch toggleMaleFemale;
    @Bind(R.id.edt_address_user)
    EditText edtAddressUser;

    @Bind(R.id.edt_mobile_user)
    EditText edtMobileUser;
    @Bind(R.id.edt_landline_user)
    EditText edtLandlineUser;
    @Bind(R.id.edt_email_user)
    EditText edtEmailUser;
    @Bind(R.id.sp_source)
    Spinner spSource;
    @Bind(R.id.btn_submit_newmember)
    Button btnSubmitNewmember;

    @Bind(R.id.scrollView)
    LinearLayout scrollView;
    @Bind(R.id.edt_other_write_user)
    EditText edtOtherWriteUser;
    @Bind(R.id.edt_zipcode)
    EditText edtZipcode;
    @Bind(R.id.sp_city)
    Spinner spCity;
    @Bind(R.id.sp_state)
    Spinner spState;
    @Bind(R.id.edt_address1_user)
    EditText edtAddress1User;

    private String malefemale = "MALE";
    private String st_zip = "";
    private String st_landline = "";


    private ArrayList<String> day, month, year, state, city;
    private ArrayList<findus> sourc;

    private MyApplication myApplication;
    Handler handler;

    private String st_day, st_month, st_yr, st_city, st_state, user_dob, user_city, user_state, user_fname, user_gender, user_adddress, user_phone, user_email, st_dob;
    private String st_findus_word = "";
    private String st_address2 = "";
    private String st_lastname = "";
    private String st_findustitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmember);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("NEW MEMBER");


        myApplication = MyApplication.getInstance();
        myApplication.createDialog(NewMemberActivity.this, false);


        edtMobileUser.addTextChangedListener(new PhoneNumberTextWatcher(edtMobileUser));
        edtLandlineUser.addTextChangedListener(new PhoneNumberTextWatcher(edtLandlineUser));


        inti();


    }

    private void inti() {


        findas_network_call();

        btnSubmitNewmember.setOnClickListener(this);
        toggleMaleFemale.setCheckedTogglePosition(0);
        toggleMaleFemale.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {

                Log.e("poooo", "" + position);
                if (position == 0) {

                    malefemale = "MALE";
                } else {

                    malefemale = "FEMALE";
                }
            }
        });


        day = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            day.add(Integer.toString(i));
        }


        month = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            month.add(Integer.toString(i));
        }

        year = new ArrayList<>();
        for (int i = 1910; i <= 2008; i++) {
            year.add(Integer.toString(i));
        }

        city = new ArrayList<>();

        city.add("Select City");
        city.add("Mehsana");
        city.add("Gandhinagar");
        city.add("Ahmedabad");

        state = new ArrayList<>();
        state.add("Select State");
        state.add("Gujarat");
        state.add("Panjab");


        SpinnerTextAdapterstatic spinnerTextAdapterday = new SpinnerTextAdapterstatic(NewMemberActivity.this, day);
        // attaching data adapter to spinner
        spDay.setAdapter(spinnerTextAdapterday);


        spDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                st_day = ((TextView) view.findViewById(R.id.spin_text)).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinnerTextAdapterstatic spinnerTextAdaptermonth = new SpinnerTextAdapterstatic(NewMemberActivity.this, month);
        // attaching data adapter to spinner
        spMonth.setAdapter(spinnerTextAdaptermonth);

        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                st_month = ((TextView) view.findViewById(R.id.spin_text)).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinnerTextAdapterstatic spinnerTextAdapteryr = new SpinnerTextAdapterstatic(NewMemberActivity.this, year);
        // attaching data adapter to spinner
        spYear.setAdapter(spinnerTextAdapteryr);

        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                st_yr = ((TextView) view.findViewById(R.id.spin_text)).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinnerTextAdapterstatic spinnerTextAdaptercity = new SpinnerTextAdapterstatic(NewMemberActivity.this, city);
        // attaching data adapter to spinner
        spCity.setAdapter(spinnerTextAdaptercity);

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                st_city = ((TextView) view.findViewById(R.id.spin_text)).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinnerTextAdapterstatic spinnerTextAdapterstate = new SpinnerTextAdapterstatic(NewMemberActivity.this, state);
        // attaching data adapter to spinner
        spState.setAdapter(spinnerTextAdapterstate);

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                st_state = ((TextView) view.findViewById(R.id.spin_text)).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void findas_network_call() {

        myApplication.DialogMessage("Loading...");
        myApplication.showDialog();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FindAs> call = apiInterface.findus();

        call.enqueue(new Callback<FindAs>() {
            @Override
            public void onResponse(Call<FindAs> call, final Response<FindAs> response) {
                if (response.isSuccessful()) {

                    response.code();
                    Log.e("code", "" + response.code());

                    if (response.code() == 200) {
                        myApplication.hideDialog();
                        List<FindAs.DataBean> as = response.body().getData();

                        if (!response.body().isError()) {

                            sourc = new ArrayList<>();

                            for (FindAs.DataBean a : as) {

                                findus findus = new findus();
                                findus.setId(a.getId());
                                findus.setCode(a.getCode());
                                findus.setTitle(a.getTitle());
                                sourc.add(findus);

                                System.out.println(a.getTitle());
                            }

                            SpinnerTextAdapter spinnerTextAdapteryrs = new SpinnerTextAdapter(NewMemberActivity.this, sourc);
                            spSource.setAdapter(spinnerTextAdapteryrs);
                            spSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    FindAs.DataBean aa = response.body().getData().get(position);

//                                    ApiClient.showLog("titel",title);

                                    st_findustitle = aa.getTitle();

                                    if (aa.getTitle().equalsIgnoreCase("Other")) {

                                        edtOtherWriteUser.setVisibility(View.VISIBLE);

                                    } else {

                                        edtOtherWriteUser.setVisibility(View.GONE);
                                    }


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        } else {

                            Toast.makeText(NewMemberActivity.this, "No Response Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        myApplication.hideDialog();


                        Toast.makeText(NewMemberActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<FindAs> call, Throwable t) {
                myApplication.hideDialog();


                Toast.makeText(NewMemberActivity.this, "No Internet Access", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onClick(View v) {

        if (!validateFirstName()) {
            return;
        }
        if (!validateAddress()) {
            return;
        }

        if (!validateNumber()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }

//-----------select date of barth--------------------------

        st_dob = st_yr + "-" + st_day + "-" + st_month;
        if (st_dob.equalsIgnoreCase("1910-1-1")) {

            user_dob = "";
        } else {

            user_dob = st_dob;
        }
//----------------------------------------------------------------------

        if (st_city.equalsIgnoreCase("Select City")) {

            user_city = "";

        } else {
            user_city = st_city;
        }

//------------------------------------------------------------------------
        if (st_state.equalsIgnoreCase("Select State")) {

            user_state = "";

        } else {
            user_state = st_state;
        }

//-------------------------------------------------------------------------

        st_zip = edtZipcode.getText().toString();
        st_landline = edtLandlineUser.getText().toString();
        st_findus_word = edtOtherWriteUser.getText().toString();
        st_address2 = edtAddress1User.getText().toString();
        st_lastname = edtLastNameUser.getText().toString();


        ApiClient.showLog("all data", user_dob + "\n" + user_city + "\n" + user_state + "\n" + st_zip + "\n" + st_landline + "\n" + st_findus_word + "\n" + st_address2+"\n"+st_lastname);


        user_fname = edtFirstNameUser.getText().toString();
        user_adddress = edtAddressUser.getText().toString();
        user_phone = edtMobileUser.getText().toString();
        user_email = edtEmailUser.getText().toString();


        PreferenceHelper preferenceHelper = new PreferenceHelper(this, "login");
        preferenceHelper.initPref();
        preferenceHelper.SaveStringPref(AppConfig.PREF_USER_NAME, user_fname);
        preferenceHelper.SaveStringPref(AppConfig.PREF_USER_ADDRESS, user_adddress);
        preferenceHelper.SaveStringPref(AppConfig.PREF_USER_GENDER, malefemale);
        preferenceHelper.SaveStringPref(AppConfig.PREF_USER_PHONE, user_phone);
        preferenceHelper.SaveStringPref(AppConfig.PREF_USER_EMAIL, user_email);

//        other add
        preferenceHelper.SaveStringPref(AppConfig.PREF_USER_DOB, user_dob);
        preferenceHelper.SaveStringPref(AppConfig.PREF_USER_CITY, user_city);
        preferenceHelper.SaveStringPref(AppConfig.PREF_USER_STATE, user_state);
        preferenceHelper.SaveStringPref(AppConfig.PREF_USER_ZIPCODE, st_zip);
        preferenceHelper.SaveStringPref(AppConfig.PREF_USER_LANDLINE, st_landline);

        preferenceHelper.SaveStringPref(AppConfig.PREF_USER_ST_FINDUS_WORD, st_findus_word);
        preferenceHelper.SaveStringPref(AppConfig.PREF_USER_ADDRESS1, st_address2);
        preferenceHelper.SaveStringPref(AppConfig.PREF_USER_LASTNAME, st_lastname);
        preferenceHelper.SaveStringPref(AppConfig.PREF_USER_FINDUS_TITLE, st_findustitle);


        preferenceHelper.ApplyPref();

        Intent i = new Intent(NewMemberActivity.this, Ailments_Activity.class);
        startActivity(i);
        finish();

    }


    private boolean validateAddress() {
        if (edtAddressUser.getText().toString().trim().isEmpty()) {
            edtAddressUser.setError(getString(R.string.err_msg_address));
            requestFocus(edtAddressUser);
            return false;
        } else {
            return true;
        }
    }

    private boolean validateFirstName() {
        if (edtFirstNameUser.getText().toString().trim().isEmpty()) {
            edtFirstNameUser.setError(getString(R.string.err_msg_first_name));
            requestFocus(edtFirstNameUser);
            return false;
        } else {
            return true;
        }
    }


    private boolean validateNumber() {
        if (edtMobileUser.getText().toString().trim().isEmpty()) {
            edtMobileUser.setError(getString(R.string.err_msg_number));
            requestFocus(edtMobileUser);
            return false;
        }
        if (edtMobileUser.length() != 12) {
            edtMobileUser.setError(getString(R.string.err_msg_valid_number));
            requestFocus(edtMobileUser);
            return false;
        } else {
            return true;
        }
    }

    private boolean validateEmail() {
        String email = edtEmailUser.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            edtEmailUser.setError(getString(R.string.err_msg_email));
            requestFocus(edtEmailUser);
            return false;
        } else {
            return true;
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//        st_day = ((TextView) view.findViewById(R.id.spin_text)).getText().toString();
//
//        ApiClient.showLog("name",st_day);
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}
