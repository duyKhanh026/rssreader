package com.example.rssreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<String> titles;
    private Context context;

    public BookAdapter(Context context) {
        this.context = context;
        this.titles = new ArrayList<>();
    }

    public void addTitle(String title) {
        titles.add(title);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bind(titles.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
        }

        public void bind(String title) {
            titleTextView.setText(title);
        }
    }
}
