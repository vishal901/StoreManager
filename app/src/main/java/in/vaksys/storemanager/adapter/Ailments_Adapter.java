package in.vaksys.storemanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.vaksys.storemanager.R;
import in.vaksys.storemanager.activitys.Pain_Activity;
import in.vaksys.storemanager.extra.ApiClient;
import in.vaksys.storemanager.extra.ApiInterface;
import in.vaksys.storemanager.extra.AppConfig;
import in.vaksys.storemanager.extra.MyApplication;
import in.vaksys.storemanager.extra.PreferenceHelper;
import in.vaksys.storemanager.model.ailment;
import in.vaksys.storemanager.response.RegisterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by vishal on 7/18/2016.
 */
public class Ailments_Adapter extends RecyclerView.Adapter<Ailments_Adapter.ViewHolder> {
    private List<ailment> ailmentdata;
    private Context context;
    PreferenceHelper preferenceHelper;
    MyApplication myApplication;
    private String user_fname, user_gender, user_adddress, user_phone, user_email, user_ailment_key;
    private ArrayList<ailment> ailemtArrayList = new ArrayList<>();

    public Ailments_Adapter(Context context, List<ailment> countries) {
        this.ailmentdata = countries;
        this.context = context;

        myApplication = MyApplication.getInstance();
        myApplication.createDialog((Activity) context, false);





    }

    @Override
    public Ailments_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_text_recy_vew, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Ailments_Adapter.ViewHolder viewHolder, final int position) {

        final ailment ailment = ailmentdata.get(position);

        viewHolder.tv_country.setText(ailment.getAilment_title());

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                customer_detail_call(user_fname, user_phone, user_gender, user_email, user_adddress, ailment.getAilment_code());
//
////                PreferenceHelper preferenceHelper = new PreferenceHelper(context, "login");
////                preferenceHelper.initPref();
////                preferenceHelper.SaveStringPref(AppConfig.PREF_USER_AILMENT_KEY, ailment.getAilment_code());
////                preferenceHelper.ApplyPref();
//
//
////                Intent intent = new Intent(context,Pain_Activity1.class);
////                context.startActivity(intent);
//            }
//        });


        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    ailment ailment1 = new ailment();
                    ailment1.setAilment_id(ailmentdata.get(position).getAilment_id());
                    ailment1.setAilment_title(ailmentdata.get(position).getAilment_title());
                    ailment1.setAilment_code(ailmentdata.get(position).getAilment_code());

                    ailemtArrayList.add(ailment1);

                    for (ailment s : ailemtArrayList) {

                        ApiClient.showLog("data", s.getAilment_title());
                    }
                } else {

                    ailemtArrayList.remove(position);

                    for (ailment s : ailemtArrayList) {

                        ApiClient.showLog("data", s.getAilment_title());
                    }

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return ailmentdata.size();
    }
    public ArrayList<ailment> getAllData(){
        return ailemtArrayList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_country;
        private AppCompatCheckBox checkBox;

        public ViewHolder(View view) {
            super(view);

            tv_country = (TextView) view.findViewById(R.id.textView3);
            checkBox = (AppCompatCheckBox) view.findViewById(R.id.cb_aliment);
        }


    }

//    private void customer_detail_call(String user_fname, String user_phone, String user_gender, String user_email, String user_adddress, String user_ailment_key) {
//
//        myApplication.DialogMessage("Loading...");
//        myApplication.showDialog();
//
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<RegisterResponse> responseCall = apiInterface.REGISTER_RESPONSE_CALL("1", user_fname, user_gender, user_adddress, user_phone, user_email, user_ailment_key);
//
//        responseCall.enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//
//                if (response.code() == 200) {
//
//                    myApplication.hideDialog();
//
//                    if (!response.body().isError()) {
//
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                        preferenceHelper.initPref();
//                        preferenceHelper.SaveStringPref(AppConfig.PREF_CUSTOMER_ID, response.body().getCustomerId());
//                        preferenceHelper.ApplyPref();
//
//                        Intent intent = new Intent(context, Pain_Activity.class);
//                        context.startActivity(intent);
//
//
//                    } else {
//
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//
//                    }
//                } else {
//                    myApplication.hideDialog();
//                    Toast.makeText(context, "something worng", Toast.LENGTH_SHORT).show();
//
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                myApplication.hideDialog();
//                Toast.makeText(context, "No Internet Access", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }

}
