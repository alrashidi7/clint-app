package com.example.myhamada;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database =     FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Users");
    EditText userPhone,userPass;
    ImageView login;
    String NameUser,PhoneUser,PassUser;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userPass = findViewById(R.id.userPass_ET);
        userPhone = findViewById(R.id.userPhone_ET);
        login = findViewById(R.id.login_btn);
        //---------------
        //sstup the login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
        //end the setup login button
    }
    //start setup method
    public void check(){
        PassUser = userPass.getText().toString();
        PhoneUser = userPhone.getText().toString();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                password  = dataSnapshot.child(PhoneUser).child("password").getValue(String.class);
                NameUser  = dataSnapshot.child(PhoneUser).child("name").getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        // some conditions
        if (TextUtils.isEmpty(PhoneUser)){
            Toast.makeText(this, "please inter your phone", Toast.LENGTH_SHORT).show();
        } else if (PhoneUser.length() < 11){
            Toast.makeText(this, "please inter correct phone", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(PassUser)){
            Toast.makeText(this, "please inter your Pass", Toast.LENGTH_SHORT).show();

        }
        else if (!(PassUser.equals(password))){
            Toast.makeText(this, "plase click again after2 secands", Toast.LENGTH_SHORT).show();



        }


        else {


            Sign();
        }


    }


    //end setup check() method
    //start setup Sign() method
    public void Sign(){


        PassUser = userPass.getText().toString();


        PhoneUser = userPhone.getText().toString();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if ((dataSnapshot.child(PhoneUser).exists())){


                    Toast.makeText(MainActivity.this, "wellcome "+ NameUser + " in fast food order", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this,menuActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(MainActivity.this, "this number not exists ", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //end setup Sign() method
}