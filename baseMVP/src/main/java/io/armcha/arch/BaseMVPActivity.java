package io.armcha.arch;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by glovebx on 20.05.2017.
 */

public abstract class BaseMVPActivity<V extends BaseMVPContract.View, P extends BaseMVPContract.Presenter<V>>
        extends AppCompatActivity implements BaseMVPContract.View {

    protected P presenter;

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    protected void onDestroy() {
        presenter.detachLifecycle(getLifecycle());
        presenter.detachView();
        super.onDestroy();
    }

    protected abstract P initPresenter();
}
