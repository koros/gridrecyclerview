package com.github.koros.sampleapp.grid;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.koros.sampleapp.R;
import com.github.koros.sampleapp.model.GridHeader;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    private final Context context;
    private final TextView headerTextView;
    private final TextView subHeaderTextView;

    public HeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.headerTextView = itemView.findViewById(R.id.txtHeader);
        this.subHeaderTextView = itemView.findViewById(R.id.txtSubHeader);
    }

    public void bind(GridHeader header) {
        headerTextView.setText(header.getHeader());
        subHeaderTextView.setText(header.getSubHeader());
    }
}
