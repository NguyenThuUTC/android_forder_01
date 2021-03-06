package com.framgia.forder.screen.orderhistory;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.forder.R;
import com.framgia.forder.data.model.Order;
import com.framgia.forder.data.source.DomainRepository;
import com.framgia.forder.data.source.OrderRepository;
import com.framgia.forder.data.source.local.DomainLocalDataSource;
import com.framgia.forder.data.source.local.UserLocalDataSource;
import com.framgia.forder.data.source.local.sharedprf.SharedPrefsApi;
import com.framgia.forder.data.source.local.sharedprf.SharedPrefsImpl;
import com.framgia.forder.data.source.remote.OrderRemoteDataSource;
import com.framgia.forder.data.source.remote.api.service.FOrderServiceClient;
import com.framgia.forder.databinding.FragmentOrderHistoryBinding;
import com.framgia.forder.utils.navigator.Navigator;
import com.framgia.forder.widgets.dialog.DialogManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 25-04-2017.
 */

public class OrderHistoryFragment extends Fragment {

    private OrderHistoryContract.ViewModel mViewModel;

    public static OrderHistoryFragment newInstance() {
        return new OrderHistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        FragmentOrderHistoryBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_order_history, container,
                        false);
        List<Order> orderHistories = new ArrayList<>();
        DialogManager dialogManager = new DialogManager(getActivity());
        OrderHistoryAdapter orderHistoryAdapter =
                new OrderHistoryAdapter(getActivity(), orderHistories);
        Navigator navigator = new Navigator(getParentFragment());
        mViewModel = new OrderHistoryViewModel(getActivity().getWindow().getContext(),
                orderHistoryAdapter, navigator, dialogManager);
        SharedPrefsApi prefsApi = new SharedPrefsImpl(getActivity());
        OrderRepository orderRepository =
                new OrderRepository(new OrderRemoteDataSource(FOrderServiceClient.getInstance()));
        DomainRepository domainRepository = new DomainRepository(null,
                new DomainLocalDataSource(prefsApi, new UserLocalDataSource(prefsApi)));
        OrderHistoryContract.Presenter presenter =
                new OrderHistoryPresenter(mViewModel, orderRepository, domainRepository);
        mViewModel.setPresenter(presenter);
        binding.setViewModel((OrderHistoryViewModel) mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
