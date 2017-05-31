package com.kutapps.keyten.shared.framework;

import android.databinding.ObservableList;

import com.kutapps.keyten.home.adapters.RecentAdapter;

public class RecyclerListObserver<T> extends ObservableList.OnListChangedCallback<ObservableList<T>> {

    private RecentAdapter adapter;

    public RecyclerListObserver(RecentAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onChanged(ObservableList observableList) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
        adapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
        adapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void onItemRangeMoved(ObservableList sender, int from, int to, int itemCount) {
        adapter.notifyItemRangeRemoved(from, itemCount);
        adapter.notifyItemRangeInserted(to, itemCount);
    }

    @Override
    public void onItemRangeRemoved(ObservableList sender, int start, int itemCount) {
        adapter.notifyItemRangeRemoved(start, itemCount);
    }
}
