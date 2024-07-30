package com.b07group2.taamapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.ArrayList;

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.BoxViewHolder>{

    private List<ItemCollection> boxList;
    private int pageSize = 5;
    private int currentPage = 0;
    private boolean[] clickedPositions;

    public BoxAdapter(List<ItemCollection> boxList) {
        if (boxList == null) {
            boxList = new ArrayList<>();
        }
        this.boxList = boxList;
        this.clickedPositions = new boolean[boxList.size()];
    }

    public void setBoxList(List<ItemCollection> boxList){
        if (boxList == null) {
            throw new IllegalArgumentException("boxList cannot be null");
        }
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

    public List<ItemCollection> getBoxList() {
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
        int index = currentPage * pageSize + position;
        if (index < boxList.size()){
            ItemCollection item = boxList.get(index);
            holder.nameTextView.setText(item.getName());
            holder.lotNumberTextView.setText("Lot Number: " + item.getLotNumber());
            holder.categoryTextView.setText("Category: " + item.getCategory());
            holder.periodTextView.setText("Period: " + item.getPeriod());
            holder.itemView.setVisibility(View.VISIBLE);

            if (clickedPositions[position]) {
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.blue));
            } else {
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(android.R.color.transparent));
            }
            holder.itemView.setOnClickListener(v -> {
                clickedPositions[position] = !clickedPositions[position];
                notifyDataSetChanged();
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

    public ItemCollection getFirstClickedItem(){
        for (int i = 0; i < clickedPositions.length; i++){
            if (clickedPositions[i]){
                return boxList.get(currentPage * pageSize + i);
            }
        }
        return null;
    }

    static class BoxViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView lotNumberTextView;
        TextView categoryTextView;
        TextView periodTextView;

        BoxViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            lotNumberTextView = itemView.findViewById(R.id.lotNumberTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            periodTextView = itemView.findViewById(R.id.periodTextView);
        }
    }
}