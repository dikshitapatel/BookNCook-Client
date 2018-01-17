package com.example.atul_.eatit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.atul_.eatit.Common.Common;
import com.example.atul_.eatit.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText edtPhone,edtPassword;
    Button btnSignIn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        edtPassword = (EditText) findViewById(R.id.edtpassword);
        edtPhone = (EditText) findViewById(R.id.edtphone);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog=new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please wait");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword.getText().toString()))
                            {
                                Intent homeIntent=new Intent(SignIn.this,Home.class);
                                Common.currentUser=user;
                                startActivity(homeIntent);
                                finish();
                            }
                        else
                            {
                                Toast.makeText(SignIn.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {

                            Toast.makeText(SignIn.this,"User doesnot exist.",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

    }
}