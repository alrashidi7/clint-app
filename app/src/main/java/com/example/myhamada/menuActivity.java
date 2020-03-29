package com.example.myhamada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class menuActivity extends AppCompatActivity {

    public static final String CHANNEL_1 = "channel_1";
    NotificationManagerCompat managerCompat;

    Adapter adapterOrder;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    RecyclerView recyclerView ;
    List<Orders> ordersList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        managerCompat = NotificationManagerCompat.from(this);


        reference.child("3mashi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.child("order").getChildren();
                Orders order = dataSnapshot.child("order").getValue(Orders.class);
                Intent intent =new Intent(menuActivity.this,menuActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(menuActivity.this,
                        1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                Notification notification = new NotificationCompat.Builder(menuActivity.this, CHANNEL_1).setSmallIcon(R.drawable.main_background)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setContentTitle("YOU HAVE NEW ORDER").setContentText((CharSequence) order).setPriority(NotificationCompat.PRIORITY_HIGH).setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();
                managerCompat.notify(0, notification);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ordersList = new ArrayList<>();
        reference.child("3mashi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Orders orders = postSnapshot.getValue(Orders.class);
                    ordersList.add(orders);
                }


                adapterOrder = new Adapter(menuActivity.this,ordersList);

                recyclerView.setAdapter(adapterOrder);

                adapterOrder.setOnItemClickListener(new Adapter.onItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                       String x =   ordersList.get(position).getOrder_id();
                       reference.child("3mashi").child(x).child("status").setValue("CONFIRM");



                        reference.child("Users").child("phone").child("order_list").child(x).child("status").setValue("CONFIRM");

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        createNotificationChannels();
        buttons();


    }

    public void buttons() {
        Button start;
        start = findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference.child("open").setValue("opened");

            }
        });

        Button stop;
        stop = findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("open").setValue("sorry we are clossed");
            }
        });
        Button scroll;
        scroll = findViewById(R.id.scroll);
        scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

            }
        });



    }

    public void createNotificationChannels(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_1, "channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("this is channel 1");
            NotificationManager manager = getSystemService(NotificationManager.class);


            assert manager != null;
            manager.createNotificationChannel(channel);
        }

    }

}
