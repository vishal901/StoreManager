package in.vaksys.storemanager.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.vaksys.storemanager.R;
import in.vaksys.storemanager.activitys.NewMemberActivity;
import in.vaksys.storemanager.extra.ApiClient;
import in.vaksys.storemanager.extra.AppConfig;
import in.vaksys.storemanager.extra.PreferenceHelper;
import in.vaksys.storemanager.model.branch;


/**
 * Created by vishal on 7/18/2016.
 */
public class Branch_Adapter extends RecyclerView.Adapter<Branch_Adapter.ViewHolder> {
    private List<branch> countries;
    private Context context;
    private PreferenceHelper preferenceHelper;
    private Dialog dialog;


    public Branch_Adapter(Context context, List<branch> countries, Dialog dialog) {
        this.countries = countries;
        this.context = context;
        this.dialog = dialog;
        preferenceHelper = new PreferenceHelper(context,"login");



    }

    @Override
    public Branch_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_branch_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Branch_Adapter.ViewHolder viewHolder, int i) {

        final branch data = countries.get(i);

//        viewHolder.branchid.setText(data.getId_branch());
        viewHolder.branchname.setText(data.getBranchname());
//        viewHolder.branchaddress.setText(data.getBranchaddress());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiClient.showLog("branch id",data.getId_branch());



                preferenceHelper.initPref();
                preferenceHelper.SaveStringPref(AppConfig.PREF_BRANCH_ID,data.getId_branch());
                preferenceHelper.ApplyPref();

                Intent intent = new Intent(context, NewMemberActivity.class);
                context.startActivity(intent);

                ((Activity)context).finish();

                dialog.dismiss();

            }
        });


    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView branchid, branchname, branchaddress;

        public ViewHolder(View view) {
            super(view);

            branchname = (TextView) view.findViewById(R.id.txt_branch_name);
//            branchname = (TextView) view.findViewById(R.id.txt_branch_name);
//            branchaddress = (TextView) view.findViewById(R.id.txt_branch_addresss);
        }
    }


}
