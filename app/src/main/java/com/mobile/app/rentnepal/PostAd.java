package com.mobile.app.rentnepal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostAd extends Fragment implements View.OnClickListener {

    Button map, submit;
    Double lat, lng;
    SharedPreferences pref;
    EditText lati, longi, username, address, phone, mobile, des, price;
    ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5;
    Spinner zone, type;
    RequestQueue queue;
    UUID r;
    String image1 = null;
    String image2 = null;
    String image3 = null;
    String image4 = null;
    private final String url = "http://nathmtourismfestival.com/RENT/PostAd.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_ad, container, false);
        pref = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        username = (EditText) v.findViewById(R.id.userNamePOST);
        username.setText(pref.getString("Name", "ADMIN"));

        address = (EditText) v.findViewById(R.id.addressPOST);
        price = (EditText) v.findViewById(R.id.amountPOST);
        map = (Button) v.findViewById(R.id.map);
        map.setOnClickListener(this);
        lati = (EditText) v.findViewById(R.id.lat);
        longi = (EditText) v.findViewById(R.id.lng);
        des = (EditText) v.findViewById(R.id.descriptionPOST);

        phone = (EditText) v.findViewById(R.id.phoneNumberPOST);
        mobile = (EditText) v.findViewById(R.id.mobilePOST);

        zone = (Spinner) v.findViewById(R.id.zonePOST);
        type = (Spinner) v.findViewById(R.id.typePOST);


        imageButton1 = (ImageButton) v.findViewById(R.id.imageButton1POST);
        imageButton1.setOnClickListener(this);

        imageButton2 = (ImageButton) v.findViewById(R.id.imageButton2POST);
        imageButton2.setOnClickListener(this);

        imageButton3 = (ImageButton) v.findViewById(R.id.imageButton3POST);
        imageButton3.setOnClickListener(this);

        imageButton4 = (ImageButton) v.findViewById(R.id.imageButton4POST);
        imageButton4.setOnClickListener(this);

        submit = (Button) v.findViewById(R.id.submit);
        submit.setOnClickListener(this);
        return v;

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.map)
            startActivityForResult(new Intent(getActivity(), MapsActivity.class), 2);
        else if (v.getId() == R.id.imageButton1POST) try {
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 3);
        } catch (Exception err) {
            Toast.makeText(getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
        else if (v.getId() == R.id.imageButton2POST)
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 4);
        else if (v.getId() == R.id.imageButton3POST)
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 5);
        else if (v.getId() == R.id.imageButton4POST)
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 6);
        else if (v.getId() == R.id.submit) {
            AlertDialog.Builder dl = new AlertDialog.Builder(getContext());
            dl.setTitle("Post Ad.");
            dl.setMessage("Do you want to request for the post ?");
            dl.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    queue = Volley.newRequestQueue(getContext());
                    StringRequest request = new StringRequest(Request.Method.POST, "http://nathmtourismfestival.com/RENT/PostAd.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.v("dsf", response);
                            Toast.makeText(getContext(), "Success " + response.toString(), Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("Error", error.getMessage());
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> values = new HashMap<>();
                            values.put("id", String.valueOf(r.randomUUID()));
                            values.put("username", pref.getString("Name", "Admin"));
                            values.put("address", String.valueOf(address.getText()));
                            values.put("lati", lat.toString());
                            values.put("longi", lng.toString());
                            values.put("phone", String.valueOf(phone.getText()));
                            values.put("mobile", String.valueOf(mobile.getText()));
                            values.put("zone", String.valueOf(zone.getSelectedItem()));
                            values.put("type", String.valueOf(type.getSelectedItem()));
                            values.put("date", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                            values.put("des", String.valueOf(des.getText()));
                            values.put("likes", String.valueOf(0));
                            values.put("image1", image1);
                            values.put("image1Name", String.valueOf(r.randomUUID()));
                            values.put("image2", image2);
                            values.put("image2Name", String.valueOf(r.randomUUID()));
                            values.put("image3", image3);
                            values.put("image3Name", String.valueOf(r.randomUUID()));
                            values.put("image4", image4);
                            values.put("image4Name", String.valueOf(r.randomUUID()));
                            values.put("price", String.valueOf(price.getText()));
                            return values;
                        }
                    };
                    queue.add(request);
                }
            });
            dl.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dl.show();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case 2:
                    lat = data.getDoubleExtra("LATI", 0d);
                    lng = data.getDoubleExtra("LONGI", 0d);
                    lati.setText("" + lat);
                    longi.setText("" + lng);
                    break;

                case 3:
                    Bitmap image = (Bitmap) data.getExtras().get("data");
                    String value = base64val(image);
                    imageButton1.setBackgroundColor(Color.RED);
                    new CountDownTimer(1000, 50) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            imageButton1.setBackgroundColor(Color.GREEN);
                        }
                    }.start();
                    image1 = value;
                    break;

                case 4:
                    Bitmap imageA = (Bitmap) data.getExtras().get("data");
                    String value2 = base64val(imageA);
                    imageButton2.setBackgroundColor(Color.RED);
                    new CountDownTimer(1000, 50) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            imageButton2.setBackgroundColor(Color.GREEN);
                        }
                    }.start();
                    image2 = value2;
                    break;

                case 5:
                    Bitmap imageB = (Bitmap) data.getExtras().get("data");
                    String value3 = base64val(imageB);
                    imageButton3.setBackgroundColor(Color.RED);
                    new CountDownTimer(1000, 50) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            imageButton3.setBackgroundColor(Color.GREEN);
                        }
                    }.start();
                    image3 = value3;
                    break;

                case 6:
                    Bitmap imageC = (Bitmap) data.getExtras().get("data");
                    String value4 = base64val(imageC);
                    imageButton4.setBackgroundColor(Color.RED);
                    new CountDownTimer(1000, 50) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            imageButton4.setBackgroundColor(Color.GREEN);
                        }
                    }.start();
                    image4 = value4;
                    break;


            }
        } catch (NullPointerException err) {
            Toast.makeText(getContext(), err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String base64val(Bitmap val) {
        ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
        val.compress(Bitmap.CompressFormat.JPEG, 80, BAOS);
        byte[] arr = BAOS.toByteArray();
        return Base64.encodeToString(arr, Base64.DEFAULT);
    }
}