package com.example.atul_.eatit;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.rengwuxian.materialedittext.MaterialEditText;

import io.paperdb.Paper;

public class SignIn extends AppCompatActivity {
    EditText edtPhone, edtPassword;
    Button btnSignIn;
    TextView btnSignUp;
    CheckBox ckbRemember;
    TextView txtForgotPwd;
    ProgressDialog progressDialog;
    DatabaseReference table_user;
    FirebaseDatabase database;



    public final boolean validate() {
        boolean valid = true;

        String phone = edtPhone.getText().toString();
        String password = edtPassword.getText().toString();


        if (phone.isEmpty()) {
            edtPhone.setError("enter a valid User Id");
            valid = false;
        } else {
            edtPhone.setError(null);
        }

        if (password.isEmpty()) {
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
        setContentView(R.layout.activity_sign_in);


        edtPassword = (EditText) findViewById(R.id.edtpassword);
        edtPhone = (EditText) findViewById(R.id.edtphone);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        ckbRemember = (CheckBox) findViewById(R.id.ckbRemember);
        txtForgotPwd = (TextView) findViewById(R.id.txtForgotPwd);
        btnSignUp = (TextView) findViewById(R.id.btnSignUp);

        Paper.init(this);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");

        txtForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotpwdDialog();
            }


        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Common.isConnectedToInternet(getBaseContext())) {


                    if (ckbRemember.isChecked()) {
                        Paper.book().write(Common.USER_KEY, edtPhone.getText().toString());
                        Paper.book().write(Common.PWD_KEY, edtPassword.getText().toString());
                    }

                    validate();
                    final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                    mDialog.setMessage("Please wait");
                   mDialog.show();
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                mDialog.dismiss();
                                User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                                user.setPhone(edtPhone.getText().toString());


                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    Intent homeIntent = new Intent(SignIn.this, Home.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                    finish();
                                } else {
                                    Toast.makeText(SignIn.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                Toast.makeText(SignIn.this, "User does not exist.", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(SignIn.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick (View v){
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        String user=Paper.book().read(Common.USER_KEY);
        String pwd=Paper.book().read(Common.PWD_KEY);

        if (user !=null && pwd!=null){

            if (!user.isEmpty() && !pwd.isEmpty()) {
                Intent home = new Intent(SignIn.this, Home.class);

                 startActivity(home);


        }


    }}



    private void showForgotpwdDialog() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Forgot Password");
        builder.setMessage("Enter your secure code");

        LayoutInflater inflater=this.getLayoutInflater();
        View forgot_view=inflater.inflate(R.layout.forgot_password_layout,null);
        builder.setView(forgot_view);

        final MaterialEditText edtPhone=(MaterialEditText)forgot_view.findViewById(R.id.edtphone);
        final MaterialEditText edtSecureCode=(MaterialEditText)forgot_view.findViewById(R.id.edtSecureCode);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);

                        if (user.getSecureCode().equals(edtSecureCode.getText().toString()))
                            Toast.makeText(SignIn.this,"Your password:"+user.getPassword(),Toast.LENGTH_SHORT).show();


                        else
                            Toast.makeText(SignIn.this,"Wrong secure code",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        builder.setNegativeButton("No",new DialogInterface.OnClickListener(){

            @Override

            public  void onClick(DialogInterface dialogInterface,int i)
            {

            }


        });


        builder.show();

       


    }
}


