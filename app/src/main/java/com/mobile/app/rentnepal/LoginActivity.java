package com.mobile.app.rentnepal;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Dialog d1;
    GoogleApiClient aps;
    SharedPreferences pref;
    GoogleSignInOptions gso;
    SignInButton signUp;
    RequestQueue que, que2, queue;
    EditText Sgmail, userId, password, Spp, Suserid, Susername, Spassword, Smobilenumber;
    TextView forget;
    EditText emailForget, pwdForget, numberForget;
    Button signIN;
    Random rand;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rand=new Random();
        d1 = new Dialog(this);
        d1.setContentView(R.layout.pop_up);
        Button leave = (Button) d1.findViewById(R.id.leave);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        aps = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        signUp = (SignInButton) d1.findViewById(R.id.sign_in);
        signUp.setScopes(gso.getScopeArray());
        signUp.setOnClickListener(this);
        leave.setOnClickListener(this);
        d1.show();

        signIN = (Button) findViewById(R.id.signIn);
        queue = Volley.newRequestQueue(getApplicationContext());
        signIN.setOnClickListener(this);
        forget = (TextView) findViewById(R.id.forget);
        forget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leave:
                d1.dismiss();
                break;

            case R.id.sign_in:
                Intent s = Auth.GoogleSignInApi.getSignInIntent(aps);
                startActivityForResult(s, 100);
                break;

            case R.id.forget:
                final Dialog dia = new Dialog(this);
                dia.setContentView(R.layout.forget);
                emailForget = (EditText) dia.findViewById(R.id.emailForget);
                pwdForget = (EditText) dia.findViewById(R.id.passwordForget);
                numberForget = (EditText) dia.findViewById(R.id.numberForget);
                Button b1 = (Button) dia.findViewById(R.id.submitForget);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        que2 = Volley.newRequestQueue(getApplicationContext());
                        StringRequest req = new StringRequest(Request.Method.POST, "http://nathmtourismfestival.com/RENT/Change.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> val = new HashMap<>();
                                val.put("gmail", String.valueOf(emailForget.getText()));
                                val.put("pwd", String.valueOf(pwdForget.getText()));
                                val.put("number", String.valueOf(numberForget.getText()));
                                return val;
                            }
                        };
                        que2.add(req);
                    }
                });
                dia.show();
                break;

            case R.id.signIn:
                signIn();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            GoogleSignInResult res = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSign(res);
        }
    }

    private void handleSign(final GoogleSignInResult res) {
        if (res.isSuccess()) {
            GoogleSignInAccount act = res.getSignInAccount();

            final Dialog d1s = new Dialog(this);
            d1s.setContentView(R.layout.sign_up);

            Sgmail = (EditText) d1s.findViewById(R.id.mySGmail);
            Sgmail.setText(act.getEmail());

            Suserid = (EditText) d1s.findViewById(R.id.mySuserid);
            Suserid.setText(act.getId());

            Spp = (EditText) d1s.findViewById(R.id.mySPicture);
            Spp.setText(String.valueOf(act.getPhotoUrl()));

            Susername = (EditText) d1s.findViewById(R.id.mySusername);

            Spassword = (EditText) d1s.findViewById(R.id.mySpassword);

            Smobilenumber = (EditText) d1s.findViewById(R.id.mySNumber);
            Button save = (Button) d1s.findViewById(R.id.save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    que = Volley.newRequestQueue(getApplicationContext());
                    StringRequest req = new StringRequest(Request.Method.POST, "http://nathmtourismfestival.com/RENT/SignUp.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            d1s.dismiss();
                            d1.dismiss();
                            Toast.makeText(getApplicationContext(), "Message: "+response.toString(), Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> val = new HashMap<>();
                            val.put("gmail", String.valueOf(Sgmail.getText()));
                            val.put("pic", String.valueOf(Spp.getText()));
                            val.put("username", String.valueOf(Susername.getText()));
                            val.put("userid", String.valueOf(rand.nextInt(1000)));
                            val.put("pwd", String.valueOf(Spassword.getText()));
                            val.put("number", String.valueOf(Smobilenumber.getText()));
                            return val;
                        }
                    };
                    que.add(req);
                }


            });
            d1s.show();
        }
    }

    private void signIn() {
        userId = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        if (userId.getText().equals(null) && password.getText().equals(null)) {
            Snackbar.make(findViewById(android.R.id.content), "Please Fill up username and password", Snackbar.LENGTH_LONG).show();
        } else {
            userId.setEnabled(false);
            password.setEnabled(false);
            Snackbar.make(findViewById(android.R.id.content), "Connecting", Snackbar.LENGTH_INDEFINITE).show();
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://nathmtourismfestival.com/RENT/CheckUser.php?userid=" + userId.getText() + "&password=" + password.getText(),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            pref = getSharedPreferences("user", MODE_PRIVATE);
                            SharedPreferences.Editor values = pref.edit();
                            if (response.length() > 0 && response.length() <= 1) {
                                try {
                                    values.putString("Gmail", response.getJSONObject(0).getString("Gmail_U"));
                                    values.putString("Name", response.getJSONObject(0).getString("UserName_U"));
                                    values.putString("UserId", response.getJSONObject(0).getString("UserId_U"));
                                    values.putString("profile",response.getJSONObject(0).getString("GmailImage_U"));
                                    values.commit();
                                } catch (Exception err) {

                                } finally {
                                    new CountDownTimer(1000, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {

                                        }

                                        @Override
                                        public void onFinish() {
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    }.start();
                                }
                            } else {
                                new CountDownTimer(4000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        userId.setEnabled(true);
                                        password.setEnabled(true);
                                        Snackbar.make(findViewById(android.R.id.content), "Login Error", Snackbar.LENGTH_LONG).show();
                                    }
                                }.start();
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_LONG).show();
                }
            });
            queue.add(request);
        }
    }
}

