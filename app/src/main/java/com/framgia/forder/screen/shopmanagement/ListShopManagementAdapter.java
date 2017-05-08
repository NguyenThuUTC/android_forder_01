package com.framgia.forder.screen.shopmanagement;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.forder.R;
import com.framgia.forder.data.model.ShopManagement;
import com.framgia.forder.databinding.ItemShopManagementBinding;
import com.framgia.forder.screen.BaseRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by levutantuan on 5/3/17.
 */

public class ListShopManagementAdapter
        extends BaseRecyclerViewAdapter<ListShopManagementAdapter.ItemViewHolder> {

    private final List<ShopManagement> mShopManagements;
    private BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object> mItemClickListener;

    public ListShopManagementAdapter(@NonNull Context context,
            List<ShopManagement> shopManagements) {
        super(context);
        mShopManagements = new ArrayList<>();
        if (shopManagements == null) {
            return;
        }
        mShopManagements.addAll(shopManagements);
    }

    @Override
    public ListShopManagementAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        ItemShopManagementBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_shop_management, parent, false);
        return new ListShopManagementAdapter.ItemViewHolder(binding, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ListShopManagementAdapter.ItemViewHolder holder, int position) {
        holder.bind(mShopManagements.get(position));
    }

    @Override
    public int getItemCount() {
        return mShopManagements.size();
    }

    public void setItemClickListener(OnRecyclerViewItemClickListener<Object> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void updateData(List<ShopManagement> shopManagements) {
        if (shopManagements == null) {
            return;
        }
        mShopManagements.clear();
        mShopManagements.addAll(shopManagements);
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ItemShopManagementBinding mBinding;
        private final BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object>
                mItemClickListener;

        ItemViewHolder(ItemShopManagementBinding binding,
                BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object> listener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = listener;
        }

        void bind(ShopManagement shopManagements) {
            mBinding.setViewModel(
                    new ItemShopManagementViewModel(shopManagements, mItemClickListener));
            mBinding.executePendingBindings();
        }
    }
}