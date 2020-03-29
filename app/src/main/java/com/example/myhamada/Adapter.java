package com.example.myhamada;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    private Context context;
    private List<Orders> ordersList;
    private onItemClickListener mlistener ;


    public interface onItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        mlistener = listener;
    }

    public Adapter(Context context, List<Orders> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders,parent,false);

        return new viewHolder(view,mlistener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Orders orders = ordersList.get(position);

        holder.order.setText("" + orders.getOrder_id()+"\n" +orders.getOrder());
        holder.status.setText(orders.getStatus());
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView order,status ;
        public viewHolder(@NonNull View itemView , final onItemClickListener listener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            order = itemView.findViewById(R.id.order);
            status = itemView.findViewById(R.id.order_status);
        }
    }
}

