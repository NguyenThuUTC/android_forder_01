package com.example.duong.android_forder_01.ui.shopmanagementdetail.ordershop.checkorder;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duong.android_forder_01.R;
import com.example.duong.android_forder_01.data.model.Orders;
import com.example.duong.android_forder_01.data.model.Shop;
import com.example.duong.android_forder_01.databinding.FragmentCheckOrderBinding;
import com.example.duong.android_forder_01.ui.adapter.CheckOrderAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.duong.android_forder_01.utils.Const.KeyIntent.EXTRA_SHOP;

/**
 * Created by Duong on 3/10/2017.
 */
public class CheckOrderFragment extends Fragment implements CheckOrderContract.View {
    private FragmentCheckOrderBinding mBinding;
    private CheckOrderContract.Presenter mPresenter;
    private List<Orders> mOrders = new ArrayList<>();
    private ObservableField<CheckOrderAdapter> mCheckOrderAdapter = new ObservableField<>();
    private Shop mShop;

    public static CheckOrderFragment newInstance(Shop shop) {
        CheckOrderFragment fragment = new CheckOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_SHOP, shop);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_check_order, container, false);
        setPresenter(new CheckOrderPresenter(this));
        mPresenter.start();
        return mBinding.getRoot();
    }

    @Override
    public void start() {
        mShop = (Shop) getArguments().getSerializable(EXTRA_SHOP);
        mBinding.setCheckOrder(this);
        mBinding.setActionHandler(new CheckOrderActionHandler(mPresenter));
        mCheckOrderAdapter
            .set(new CheckOrderAdapter(getContext(), mOrders, mPresenter));
    }

    @Override
    public void setPresenter(CheckOrderContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public ObservableField<CheckOrderAdapter> getCheckOrderAdapter() {
        return mCheckOrderAdapter;
    }

    @Override
    public void shopListOrder(List<Orders> orders) {
        mOrders.addAll(orders);
        updateAdapter();
    }

    @Override
    public void getListOrderError() {
        // todo show error message
    }

    @Override
    public void updateAdapter() {
        mCheckOrderAdapter.get().notifyDataSetChanged();
    }
}