package com.example.think.foodorderonline;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.think.foodorderonline.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignIn extends AppCompatActivity {

    EditText edtPassword1, edtPhone1;
    Button btnsignin1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword1 = (EditText) findViewById(R.id.edtpassword1);
        edtPhone1 = (EditText) findViewById(R.id.edtphone1);

        btnsignin1 = (Button) findViewById(R.id.btnsignin1);

        ///
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnsignin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog dialog = new ProgressDialog(SignIn.this);
                dialog.setMessage("Please Waiting.....");
                dialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(edtPhone1.getText().toString()).exists())

                        {

                            dialog.dismiss();
                            User user = dataSnapshot.child(edtPhone1.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword1.getText().toString())) {
                                Toast.makeText(SignIn.this, "Sign in Suceessfully !!!! ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignIn.this, "Wrong Password  !!!! ", Toast.LENGTH_SHORT).show();
                            }
                        }

                    else
                            {
                                dialog.dismiss();
                                Toast.makeText(SignIn.this, "User not Exist  !!!! ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
