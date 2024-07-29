package com.b07group2.taamapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.BoxViewHolder>{

    private List<String> boxList;
    private int pageSize = 5;
    private int currentPage = 0;
    private boolean[] clickedPositions;

    public BoxAdapter(List<String> boxList) {
        this.boxList = boxList;
        this.clickedPositions = new boolean[boxList.size()];
    }

    public void setBoxList(List<String> boxList){
        this.boxList = boxList;
        this.clickedPositions = new boolean[boxList.size()];
        notifyDataSetChanged();
    }//look at this

    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
        notifyDataSetChanged();
    }

    public int getCurrentPage(){
        return currentPage;
    }

    public List<String> getBoxList() {
        return boxList;
    }

    @NonNull
    @Override
    public BoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_box, parent, false);
        return new BoxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoxViewHolder holder, int position) {
        // set sample data

        int index = currentPage * pageSize + position;
        if (index < boxList.size()){
            holder.textView.setText(boxList.get(index));
            holder.itemView.setVisibility(View.VISIBLE);

            if (clickedPositions[position]) {
                // item clicked
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.blue));
            } else {
                // item not clicked
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(android.R.color.transparent));
            }
            holder.itemView.setOnClickListener(v -> {
                // Toggle the clicked status
                clickedPositions[position] = !clickedPositions[position];
                notifyDataSetChanged(); // refresh item view
            });
        } else {
            holder.itemView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        int remainingItems = boxList.size() - currentPage * pageSize;
        return Math.min(remainingItems, pageSize);
    }

    public boolean checkClick(){
        for (boolean clicked : clickedPositions){
            if (clicked){
                return true;
            }
        }
        return false;
    }

    static class BoxViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        BoxViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.mockBoxText);
        }
    }
}