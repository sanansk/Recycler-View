package com.example.reecyclee_viewww;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.viewHolder>{

    ArrayList<ProjectModel> list;
    Context context;

    public ProjectAdapter(ArrayList<ProjectModel> list, Context context) {
        this.list= list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ProjectModel model= list.get(position);

        Picasso.get().load(model.getProductImage()).placeholder(R.drawable.sanan).into(holder.itemImage);

        holder.itemHeadline.setText(model.getHeadline());
        holder.itemDescription.setText(model.getDescription());
        holder.itemPrice.setText(model.getPrice());
        holder.itemBrand.setText(model.getBrand());

//        singleProduct
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, SingleProductActivity.class);

                intent.putExtra("singleImage", model.getProductImage());

                intent.putExtra("singleHeadline", model.getHeadline());
                intent.putExtra("singlePrice", model.getPrice());
                intent.putExtra("singleBrand", model.getBrand());
                intent.putExtra("singleProductType", model.getProductType());
                intent.putExtra("singleAboutProduct", model.getAboutProduct());
                intent.putExtra("singleOrigin", model.getOrigin());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
// single product
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView itemHeadline,itemDescription,itemPrice,itemBrand;
        ImageView itemImage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            itemHeadline= itemView.findViewById(R.id.itemHeadline);
            itemDescription= itemView.findViewById(R.id.itemDescription);
            itemPrice= itemView.findViewById(R.id.itemPrice);
            itemBrand= itemView.findViewById(R.id.itemBrand);

            itemImage= itemView.findViewById(R.id.itemImage);

        }
    }
}
