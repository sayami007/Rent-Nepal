package values;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mobile.app.rentnepal.HFragment.BasicClass;
import com.mobile.app.rentnepal.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MyAd extends Fragment {
    RecyclerView add;
    View v;
    RequestQueue queue;
    String user_name;
    ArrayList<String> addId, address, username;
    ArrayList<Integer> type;
    ArrayList<Bitmap> image;

    ArrayList<String> usernameSS, addressSS, contactSS, zonesSS, latiSS, longiSS, priceSS;
    ArrayList<Bitmap> imageSS, image1SS, image2SS, image3SS;

    public MyAd() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        usernameSS = new ArrayList<>();
        addressSS = new ArrayList<>();
        contactSS = new ArrayList<>();
        zonesSS = new ArrayList<>();
        latiSS = new ArrayList<>();

        longiSS = new ArrayList<>();
        priceSS = new ArrayList<>();
        SharedPreferences pref = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        user_name = pref.getString("Name", null);
        Log.v("USER", "" + user_name);
        Toast.makeText(getActivity(), "" + user_name, Toast.LENGTH_SHORT).show();
        addId = new ArrayList<>();
        type = new ArrayList<>();
        image = new ArrayList<>();
        address = new ArrayList<>();
        username = new ArrayList<>();
        v = inflater.inflate(R.layout.fragment_my_ad, container, false);
        queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://nathmtourismfestival.com/RENT/MyAds.php?userid=" + user_name, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        if (response.getJSONObject(i).getString("Type_M").equals("Rent")) {
                            type.add(R.drawable.r);
                        } else
                            type.add(R.drawable.s);
                        image.add(getBit(response.getJSONObject(i).getString("Image1_M")));
                        address.add(response.getJSONObject(i).getString("Address_M"));
                        username.add(response.getJSONObject(i).getString("UserName_M"));
                        addId.add(response.getJSONObject(i).getString("AddId_M"));
                    }
                    add = (RecyclerView) v.findViewById(R.id.recyclerAd);
                    add.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    add.setAdapter(new CustomAdapter(getActivity(), getData()));
                    YoYo.with(Techniques.Landing).duration(700).playOn(add);
                } catch (Exception err) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
        return v;
    }


    private List<BasicClass> getData() {
        List<BasicClass> classes = new ArrayList<>();
        for (int i = 0; i < address.size(); i++) {
            BasicClass classe = new BasicClass();
            classe.type = type.get(i);
            classe.pic = image.get(i);
            classe.addId = addId.get(i);
            classe.add = address.get(i);
            classes.add(classe);
        }
        return classes;
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolders> {

        LayoutInflater inflate;
        List<BasicClass> data;

        public CustomAdapter(Context ctx, List<BasicClass> data) {
            this.inflate = LayoutInflater.from(ctx);
            this.data = data;
        }

        @Override
        public ViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflate.inflate(R.layout.home_advertise, parent, false);
            ViewHolders hold = new ViewHolders(v);
            return hold;
        }

        @Override
        public void onBindViewHolder(ViewHolders holder, int position) {
            BasicClass classes = data.get(position);
            holder.pic.setImageBitmap(classes.pic);
            holder.add.setText(classes.add);
            holder.type.setImageResource(classes.type);
            holder.addId_S.setText(classes.addId);
            YoYo.with(Techniques.Landing).duration(700).playOn(holder.card);

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolders extends RecyclerView.ViewHolder {
            ImageView pic, type;
            TextView add, addId_S;
            CardView card;

            public ViewHolders(View itemView) {
                super(itemView);
                imageSS = new ArrayList<Bitmap>();
                image2SS = new ArrayList<Bitmap>();
                image3SS = new ArrayList<Bitmap>();
                image1SS = new ArrayList<Bitmap>();

                addId_S = (TextView) itemView.findViewById(R.id.addId);
                pic = (ImageView) itemView.findViewById(R.id.pic);
                add = (TextView) itemView.findViewById(R.id.add);
                type = (ImageView) itemView.findViewById(R.id.type);
                card = (CardView) itemView.findViewById(R.id.myCard);
                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestQueue que = Volley.newRequestQueue(getContext());
                        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://nathmtourismfestival.com/RENT/DetailInfo.php?add=" + addId_S.getText().toString(), new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    for (int i = 0; i < response.length(); i++) {
                                        usernameSS.add(response.getJSONObject(i).getString("UserName_M"));
                                        addressSS.add(response.getJSONObject(i).getString("Address_M"));
                                        contactSS.add(response.getJSONObject(i).getString("Phone_M"));
                                        zonesSS.add(response.getJSONObject(i).getString("Zone_M"));
                                        latiSS.add(response.getJSONObject(i).getString("Lati_M"));
                                        longiSS.add(response.getJSONObject(i).getString("Longi_M"));
                                        priceSS.add(response.getJSONObject(i).getString("Price"));
                                        imageSS.add(getBit(response.getJSONObject(i).getString("Image1_M")));
                                        image1SS.add(getBit(response.getJSONObject(i).getString("Image2_M")));
                                        image2SS.add(getBit(response.getJSONObject(i).getString("Image3_M")));
                                        image3SS.add(getBit(response.getJSONObject(i).getString("Image4_M")));
                                    }


                                    Log.v("get", addId_S.getText().toString());
                                    final Dialog dialog = new Dialog(getContext());
                                    dialog.setContentView(R.layout.more_information);
                                    TextView usernameF = (TextView) dialog.findViewById(R.id.usernameF);
                                    usernameF.setText(usernameSS.get(0));
                                    TextView addressF = (TextView) dialog.findViewById(R.id.addressF);
                                    addressF.setText(addressSS.get(0));
                                    TextView contactF = (TextView) dialog.findViewById(R.id.contactF);
                                    contactF.setText(contactSS.get(0));
                                    TextView priceF = (TextView) dialog.findViewById(R.id.priceF);
                                    priceF.setText(priceSS.get(0));

                                    Button call = (Button) dialog.findViewById(R.id.callF);
                                    call.setVisibility(View.GONE);

                                    Button map = (Button) dialog.findViewById(R.id.show_map);
                                    map.setText("Delete");
                                    map.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            AlertDialog.Builder buil = new AlertDialog.Builder(getContext());
                                            buil.setTitle("Delete");
                                            buil.setMessage("Do you really want to delete this ?");
                                            buil.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(final DialogInterface dialog, int which) {
                                                    new CountDownTimer(5000, 1000) {
                                                        @Override
                                                        public void onTick(long millisUntilFinished) {
                                                            }

                                                        @Override
                                                        public void onFinish() {
                                                            delete(addId_S.getText().toString());
                                                        }
                                                    }.start();
                                                }
                                            });
                                            buil.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            buil.show();
                                        }
                                    });
                                    ImageView imageView1 = (ImageView) dialog.findViewById(R.id.imageViewMore1);
                                    imageView1.setImageBitmap(imageSS.get(0));

                                    ImageView imageView2 = (ImageView) dialog.findViewById(R.id.imageViewMore2);
                                    imageView2.setImageBitmap(image1SS.get(0));

                                    ImageView imageView3 = (ImageView) dialog.findViewById(R.id.imageViewMore3);
                                    imageView3.setImageBitmap(image2SS.get(0));

                                    ImageView imageView4 = (ImageView) dialog.findViewById(R.id.imageViewMore4);
                                    imageView4.setImageBitmap(image3SS.get(0));

                                    Button delete = (Button) dialog.findViewById(R.id.MessageF);
                                    delete.setVisibility(View.GONE);


                                    WindowManager.LayoutParams param = new WindowManager.LayoutParams();
                                    param.width = WindowManager.LayoutParams.MATCH_PARENT;
                                    param.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                    param.windowAnimations = R.style.dialog_animation;
                                    dialog.getWindow().setAttributes(param);
                                    dialog.show();


                                } catch (Exception err) {
                                    Log.v("Errrrr", err.getMessage());
                                    Toast.makeText(getContext(), err.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "ER " + error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                        que.add(request);
                        usernameSS.clear();
                        addressSS.clear();
                        contactSS.clear();
                        zonesSS.clear();
                        latiSS.clear();
                        longiSS.clear();
                        priceSS.clear();
                        imageSS.clear();
                        image1SS.clear();
                        image2SS.clear();
                        image3SS.clear();
                    }
                });
            }
        }


    }

    private void delete(String s) {
        RequestQueue quee = Volley.newRequestQueue(getContext());
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, "http://nathmtourismfestival.com/RENT/Delete.php?add=" + s, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(getContext(), "Congrats", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        quee.add(req);
    }

    public Bitmap getBit(String value) {
        byte[] arr = Base64.decode(value, Base64.DEFAULT);
        Bitmap img = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        return img;
    }


}