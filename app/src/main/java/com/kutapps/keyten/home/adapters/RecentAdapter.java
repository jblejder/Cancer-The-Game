package com.kutapps.keyten.home.adapters;

import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kutapps.keyten.databinding.ViewUserItemBinding;
import com.kutapps.keyten.home.models.LoggedUserModel;
import com.kutapps.keyten.shared.database.models.Ownership;
import com.kutapps.keyten.shared.framework.RecyclerListObserver;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.VH> {

    private ObservableList<Ownership> dataSet;

    public RecentAdapter(ObservableList<Ownership> dataSet) {
        this.dataSet = dataSet;
        setHasStableIds(true);
        dataSet.addOnListChangedCallback(new RecyclerListObserver<>(this));
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewUserItemBinding binding = ViewUserItemBinding.inflate(inflater, parent, false);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.setModel(dataSet.get(position).getUser(), position);
    }

    @Override
    public long getItemId(int position) {
        return dataSet.get(position).getUserId().hashCode();
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private ViewUserItemBinding binding;

        VH(ViewUserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setModel(LoggedUserModel user, int position) {
            binding.setModel(user);
            float scale;
            if (position > 2) {
                scale = 0.4f;
            } else {
                scale = 1 - ((float) position / getItemCount() / 1.4f);
            }
            binding.setScale(scale);
        }
    }
}
