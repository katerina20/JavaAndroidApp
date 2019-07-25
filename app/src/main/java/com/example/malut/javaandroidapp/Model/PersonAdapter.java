package com.example.malut.javaandroidapp.Model;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.malut.javaandroidapp.R;
import com.example.malut.javaandroidapp.Services.OnPersonClickListener;
import com.example.malut.javaandroidapp.Services.OnPersonInfoPass;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<Person> people;
    private OnPersonClickListener listener;

    public PersonAdapter(Activity context, ArrayList<Person> people) {
        this.context = context;
        this.people = people;
    }

    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(view, viewHolder.getAdapterPosition());
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PersonAdapter.ViewHolder viewHolder, int i) {

        Person selectedPerson = people.get(i);

        viewHolder.textViewName.setText(selectedPerson.getName());
        viewHolder.textViewSurname.setText(selectedPerson.getSurname());
        viewHolder.imageView.setImageResource(selectedPerson.getImage());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewSurname;
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textName);
            textViewSurname = itemView.findViewById(R.id.textSurname);
            imageView = itemView.findViewById(R.id.imageUser);
        }
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public OnPersonClickListener getListener() {
        return listener;
    }

    public void setListener(OnPersonClickListener listener) {
        this.listener = listener;
    }

}
