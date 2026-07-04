package com.uth.appfrima;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SignatureAdapter extends RecyclerView.Adapter<SignatureAdapter.SignatureViewHolder> {

    private List<Signature> signatureList;
    private OnItemLongClickListener longClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(Signature signature, int position);
    }

    public SignatureAdapter(List<Signature> signatureList, OnItemLongClickListener longClickListener) {
        this.signatureList = signatureList;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public SignatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_signature, parent, false);
        return new SignatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SignatureViewHolder holder, int position) {
        Signature signature = signatureList.get(position);
        holder.txtDescription.setText(signature.getDescripcion());

        byte[] blob = signature.getFirmaDigital();
        if (blob != null && blob.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
            holder.imgSignature.setImageBitmap(bitmap);
        } else {
            holder.imgSignature.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(signature, holder.getAdapterPosition());
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return signatureList.size();
    }

    public static class SignatureViewHolder extends RecyclerView.ViewHolder {
        TextView txtDescription;
        ImageView imgSignature;

        public SignatureViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            imgSignature = itemView.findViewById(R.id.imgSignature);
        }
    }
}
