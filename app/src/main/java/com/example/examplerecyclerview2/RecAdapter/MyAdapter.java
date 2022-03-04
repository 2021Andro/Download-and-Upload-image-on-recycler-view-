package com.example.examplerecyclerview2.RecAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.examplerecyclerview2.Classes.PersonDemo;
import com.example.examplerecyclerview2.Classes.RecyclerEventListener_1;
import com.example.examplerecyclerview2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<PersonDemo> personList = new ArrayList<>();
    private RecyclerEventListener_1 selectedItem;
    private PersonDemo person = new PersonDemo();
    private Context context;

    public MyAdapter(Context context) {
        this.selectedItem = (RecyclerEventListener_1) context;
        this.context = context;
    }

    public void setPersonList(ArrayList<PersonDemo> personList) {

        if (personList == null) {
            this.personList = new ArrayList<>();

        }
        this.personList = personList;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        person = personList.get(position);

        holder.itemView.setTag(personList.get(position));

        holder.tvName.setText(person.getPersonName());

        Picasso
                .with(context)
                .load(person.getPersonImage())
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .centerCrop()
                .into(holder.ivProfile);


    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivProfile;
        private TextView tvName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfile = itemView.findViewById(R.id.imageView2);
            tvName = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedItem.setOnRecItemClick(personList.indexOf((PersonDemo) itemView.getTag()));
                }
            });


        }
    }
}
