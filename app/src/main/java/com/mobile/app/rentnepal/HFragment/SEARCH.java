package com.mobile.app.rentnepal.HFragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.SearchView;
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
import com.mobile.app.rentnepal.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SEARCH extends Fragment {

    SearchView searchView;
    RecyclerView recyclerView;
    View v;
    ArrayList<String> address, username,addId;
    ArrayList<Integer> type;
    ArrayList<Bitmap> image;
    RequestQueue queue;

    public SEARCH() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        type = new ArrayList<>();
        image = new ArrayList<>();
        address = new ArrayList<>();
        username = new ArrayList<>();
        addId = new ArrayList<>();
        v = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = (SearchView) v.findViewById(R.id.mySearchView);
        recyclerView = (RecyclerView) v.findViewById(R.id.myRecyclerView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myfunction(query);
                clear();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return v;
    }

    private void clear() {
        type.clear();
        address.clear();
        username.clear();
        image.clear();
    }


    private void myfunction(String zones) {

        queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://nathmtourismfestival.com/RENT/SearchZones.php?zone=" + zones, new Response.Listener<JSONArray>() {
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

                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(new CustomAdapter(getActivity(), getData()));
                } catch (Exception err) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);

    }


    private List<BasicClass> getData() {
        List<BasicClass> classes = new ArrayList<>();
        for (int i = 0; i < address.size(); i++) {
            BasicClass classe = new BasicClass();
            classe.type = type.get(i);
            classe.pic = image.get(i);
            classe.add = address.get(i);
            classe.addId=addId.get(i);
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
            holder.type.setImageResource(classes.type);
            holder.pic.setImageBitmap(classes.pic);
            holder.add.setText(classes.add);
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
            ArrayList<String> usernameSS, addressSS, contactSS, zonesSS, latiSS, longiSS, priceSS,latiSSS,longiSSS;
            ArrayList<Bitmap> imageSS, image1SS, image2SS, image3SS;

            public ViewHolders(View itemView) {
                super(itemView);
                usernameSS=new ArrayList<>();
                addressSS=new ArrayList<>();
                contactSS=new ArrayList<>();
                zonesSS=new ArrayList<>();
                latiSS=new ArrayList<>();
                longiSS=new ArrayList<>();
                priceSS=new ArrayList<>();
                imageSS=new ArrayList<>();
                image1SS=new ArrayList<>();
                image2SS=new ArrayList<>();
                image3SS=new ArrayList<>();
                latiSSS=new ArrayList<>();
                longiSSS=new ArrayList<>();


                pic = (ImageView) itemView.findViewById(R.id.pic);
                type = (ImageView) itemView.findViewById(R.id.type);
                add = (TextView) itemView.findViewById(R.id.add);
                addId_S = (TextView) itemView.findViewById(R.id.addId);
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
                                        latiSSS.add(response.getJSONObject(i).getString("Lati_M"));
                                        longiSSS.add(response.getJSONObject(i).getString("Longi_M"));
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
                                    call.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder build = new AlertDialog.Builder(getContext());
                                            build.setTitle("Call " + contactSS.get(0));
                                            build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent inte = new Intent(Intent.ACTION_CALL);
                                                    inte.setData(Uri.parse("tel:" + contactSS.get(0)));
                                                    startActivity(inte);
                                                }
                                            });
                                            build.show();
                                        }
                                    });
                                    Button delete = (Button) dialog.findViewById(R.id.MessageF);

                                    Button map = (Button) dialog.findViewById(R.id.show_map);
                                    map.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(v.getContext(), NewMap.class);
                                            intent.putExtra("Lati", "" + latiSSS.get(0));
                                            intent.putExtra("Longi", "" + longiSSS.get(0));
                                            startActivity(intent);

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
                        latiSSS.clear();
                        longiSSS.clear();
                    }
                });
            }
        }
    }

    public Bitmap getBit(String value) {
        byte[] arr = Base64.decode(value, Base64.DEFAULT);
        Bitmap img = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        return img;
    }

}

