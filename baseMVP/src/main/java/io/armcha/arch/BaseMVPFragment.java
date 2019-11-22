package io.armcha.arch;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by glovebx on 22.05.2017.
 */

public abstract class BaseMVPFragment<V extends BaseMVPContract.View, P extends BaseMVPContract.Presenter<V>>
        extends Fragment implements BaseMVPContract.View {

    protected P presenter;

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseViewModel<V, P> viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        boolean isPresenterCreated = false;
        if (viewModel.getPresenter() == null) {
            viewModel.setPresenter(initPresenter());
            isPresenterCreated = true;
        }
        presenter = viewModel.getPresenter();
        presenter.attachLifecycle(getLifecycle());
        presenter.attachView((V) this);
        if (isPresenterCreated)
            presenter.onPresenterCreate();
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        presenter.detachLifecycle(getLifecycle());
        presenter.detachView();
        super.onDestroyView();
    }

    protected abstract P initPresenter();
}
