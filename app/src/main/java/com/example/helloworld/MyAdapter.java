package com.example.helloworld;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<MedOrganization> medOrgs;
    Context context;

    public MyAdapter(Context ct, List<MedOrganization> medOrgs) {
        context = ct;
        this.medOrgs = medOrgs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(view);
    }

    public void openOrganizationDetail(int position){
        Intent intent = new Intent(context, OrganizationDetailActivity.class);
        intent.putExtra("org_details", medOrgs.get(position));
        context.startActivity(intent);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameLst.setText(medOrgs.get(position).getName());
        holder.descLst.setText("TODO");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOrganizationDetail(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return medOrgs.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView nameLst, descLst;
        ImageView myImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameLst = itemView.findViewById(R.id.name_lst);
            descLst = itemView.findViewById(R.id.desc_lst);
            myImage = itemView.findViewById(R.id.my_image);
        }
    }
}
