package com.example.emailverification;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    EditText edtUserName, edtLoginPassword;
    Button btnLogin;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserName = findViewById(R.id.edt_User_Name);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        dialog = new ProgressDialog(this);
    }

    public void loginIsPressed(View btnView){

        login(edtUserName.getText().toString(),edtLoginPassword.getText().toString());

    }

    private void login(String username, String password) {
        dialog.show();
        ParseUser.logInInBackground(username, password, (parseUser, e) -> {
            dialog.dismiss();
            if (parseUser != null) {
                showAlert("Login Successful", "Welcome, " + username + "!", false);
            } else {
                ParseUser.logOut();
                showAlert("Login Fail", e.getMessage() + " Please try again", true);
            }
        });
    }

    private void showAlert(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    // don't forget to change the line below with the names of your Activities
                    if (!error) {
                        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }


}