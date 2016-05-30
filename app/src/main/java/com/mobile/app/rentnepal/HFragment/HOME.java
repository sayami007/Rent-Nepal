package com.mobile.app.rentnepal.HFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class HOME extends Fragment {
    RecyclerView view;
    View v;
    RequestQueue queue;
    ProgressBar bar;
    ArrayList<String> address, username, addId;
    ArrayList<Integer> type;
    ArrayList<Bitmap> image, image1, image2, image3, image4;

    public HOME() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        type = new ArrayList<>();
        addId = new ArrayList<>();
        image = new ArrayList<>();
        image1 = new ArrayList<>();
        image2 = new ArrayList<>();
        image3 = new ArrayList<>();
        image4 = new ArrayList<>();
        address = new ArrayList<>();
        username = new ArrayList<>();
        v = inflater.inflate(R.layout.fragment_home2, container, false);
        view = (RecyclerView) v.findViewById(R.id.recycler);
        queue = Volley.newRequestQueue(getContext());


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "http://nathmtourismfestival.com/RENT/Detail.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Snackbar.make(getActivity().findViewById(android.R.id.content), "Loading", Snackbar.LENGTH_INDEFINITE).show();
                try {

                    for (int i = 0; i < response.length(); i++) {
                        if (response.getJSONObject(i).getString("Type_M").equals("Rent") || response.getJSONObject(i).getString("Type_M").equals("rent"))
                            type.add(R.drawable.r);
                        else if (response.getJSONObject(i).getString("Type_M").equals("Sale") || response.getJSONObject(i).getString("Type_M").equals("sale"))
                            type.add(R.drawable.s);
                        addId.add(response.getJSONObject(i).getString("AddId_M"));
                        image.add(getBit(response.getJSONObject(i).getString("Image1_M")));
                        address.add(response.getJSONObject(i).getString("Address_M"));
                        username.add(response.getJSONObject(i).getString("UserName_M"));
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Completed", Snackbar.LENGTH_SHORT).show();

                    }
                    view.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    view.setAdapter(new CustomAdapter(getActivity(), getData()));
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
            classe.add = address.get(i);
            classe.addId = addId.get(i);
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
            holder.addId.setText(classes.addId);
            YoYo.with(Techniques.Landing).duration(700).playOn(holder.card);

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolders extends RecyclerView.ViewHolder {
            ImageView pic, type;
            TextView add, addId;
            CardView card;
            ArrayList<String> username, address, contact, zones, lati, longi, price;

            public ViewHolders(final View itemView) {
                super(itemView);
                username = new ArrayList<>();
                address = new ArrayList<>();
                contact = new ArrayList<>();
                zones = new ArrayList<>();
                lati = new ArrayList<>();
                image1 = new ArrayList<>();
                image2 = new ArrayList<>();
                image3 = new ArrayList<>();
                image4 = new ArrayList<>();
                longi = new ArrayList<>();
                price = new ArrayList<>();
                pic = (ImageView) itemView.findViewById(R.id.pic);
                add = (TextView) itemView.findViewById(R.id.add);
                addId = (TextView) itemView.findViewById(R.id.addId);
                type = (ImageView) itemView.findViewById(R.id.type);
                card = (CardView) itemView.findViewById(R.id.myCard);
                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, "http://nathmtourismfestival.com/RENT/DetailInfo.php?add=" + addId.getText().toString(), new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                try {
                                    for (int i = 0; i < response.length(); i++) {
                                        username.add(response.getJSONObject(i).getString("UserName_M"));
                                        address.add(response.getJSONObject(i).getString("Address_M"));
                                        contact.add(response.getJSONObject(i).getString("Phone_M"));
                                        zones.add(response.getJSONObject(i).getString("Zone_M"));
                                        lati.add(response.getJSONObject(i).getString("Lati_M"));
                                        longi.add(response.getJSONObject(i).getString("Longi_M"));
                                        image1.add(getBit(response.getJSONObject(i).getString("Image1_M")));
                                        image2.add(getBit(response.getJSONObject(i).getString("Image2_M")));
                                        image3.add(getBit(response.getJSONObject(i).getString("Image3_M")));
                                        image4.add(getBit(response.getJSONObject(i).getString("Image4_M")));
                                        price.add(response.getJSONObject(i).getString("Price"));
                                    }
                                    Dialog builder = new Dialog(getContext());

                                    WindowManager.LayoutParams param = new WindowManager.LayoutParams();
                                    param.width = WindowManager.LayoutParams.MATCH_PARENT;
                                    param.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                    param.windowAnimations = R.style.dialog_animation;

                                    builder.setContentView(R.layout.more_information);
                                    TextView usernameF = (TextView) builder.findViewById(R.id.usernameF);
                                    TextView addressF = (TextView) builder.findViewById(R.id.addressF);
                                    TextView contactF = (TextView) builder.findViewById(R.id.contactF);
                                    TextView priceF = (TextView) builder.findViewById(R.id.priceF);
                                    Button call = (Button) builder.findViewById(R.id.callF);
                                    Button map = (Button) builder.findViewById(R.id.show_map);
                                    map.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(v.getContext(), NewMap.class);
                                            intent.putExtra("Lati", "" + lati.get(0));
                                            intent.putExtra("Longi", "" + longi.get(0));
                                            startActivity(intent);

                                        }
                                    });
                                    ImageView imageView1 = (ImageView) builder.findViewById(R.id.imageViewMore1);
                                    imageView1.setImageBitmap(image1.get(0));

                                    ImageView imageView2 = (ImageView) builder.findViewById(R.id.imageViewMore2);
                                    imageView2.setImageBitmap(image2.get(0));

                                    ImageView imageView3 = (ImageView) builder.findViewById(R.id.imageViewMore3);
                                    imageView3.setImageBitmap(image3.get(0));

                                    ImageView imageView4 = (ImageView) builder.findViewById(R.id.imageViewMore4);
                                    imageView4.setImageBitmap(image4.get(0));

                                    Button message = (Button) builder.findViewById(R.id.MessageF);
                                    usernameF.setText(username.get(0));
                                    addressF.setText(address.get(0));
                                    contactF.setText(contact.get(0));
                                    priceF.setText(price.get(0));
                                    call.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder build = new AlertDialog.Builder(getContext());
                                            build.setTitle("Call " + contact.get(0));
                                            build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent inte = new Intent(Intent.ACTION_CALL);
                                                    inte.setData(Uri.parse("tel:" + contact.get(0)));
                                                    startActivity(inte);
                                                }
                                            });
                                            build.show();
                                        }
                                    });

                                    builder.show();
                                    builder.getWindow().setAttributes(param);

                                } catch (Exception err)

                                {
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        queue.add(req);
                        username.clear();
                        address.clear();
                        contact.clear();
                        zones.clear();
                        lati.clear();
                        image1.clear();
                        image2.clear();
                        image3.clear();
                        image4.clear();
                        longi.clear();
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
