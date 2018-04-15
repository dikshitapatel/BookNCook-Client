package com.example.atul_.eatit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atul_.eatit.Common.Common;
import com.example.atul_.eatit.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    EditText edtName,edtPhone,edtPassword,edtRepeat;
    Button btnSignUp;
    ProgressDialog progressDialog;

    public boolean validate() {
        boolean valid = true;

        String phone = edtPhone.getText().toString();
        String password = edtPassword.getText().toString();


        if (phone.isEmpty() ) {
            edtPhone.setError("enter a valid User Id");
            valid = false;
        } else {
            edtPhone.setError(null);
        }
        if(!edtPassword.equals(edtRepeat))
        {
            valid=false;
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edtPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            edtPassword.setError(null);
        }

        return valid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName= (EditText) findViewById(R.id.edtname);
        edtPhone= (EditText) findViewById(R.id.edtphone);
        edtPassword= (EditText) findViewById(R.id.edtpassword);
        edtRepeat=(EditText) (findViewById(R.id.edtRepeatPassword));

        btnSignUp=(Button)(findViewById(R.id.btnSignUp));


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edtPhone.getText().toString();
                String password = edtPassword.getText().toString();
                if (phone.isEmpty() && password.isEmpty()) {
                    edtPhone.setError("Required");
                    edtPassword.setError("Required");
                }
                else {

                    if (Common.isConnectedToInternet(getBaseContext())) {
                        final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                        mDialog.setMessage("Please wait");
                        mDialog.show();

                        //final ProgressDialog mDialog=new ProgressDialog(SignUp.this);
                        validate();
                        // mDialog.setMessage("Please wait");
                        // mDialog.show();


                        table_user.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                    mDialog.dismiss();
                                    edtPhone.setError("User Already exists");

                                } else {
                                    mDialog.dismiss();
                                    User user = new User(edtName.getText().toString(), edtPassword.getText().toString());
                                    table_user.child(edtPhone.getText().toString()).setValue(user);
                                    Toast.makeText(SignUp.this, "Sign up successfull", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(SignUp.this, Home.class);
                                    startActivity(i);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Toast.makeText(SignUp.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

            }
        });
    }
}


