package io.armcha.arch;


import android.os.Bundle;

import androidx.lifecycle.Lifecycle;

/**
 * Created by glovebx on 20.05.2017.
 */

public interface BaseMVPContract {

    interface View {

    }

    interface Presenter<V extends BaseMVPContract.View> {

        Bundle getStateBundle();

        void attachLifecycle(Lifecycle lifecycle);

        void detachLifecycle(Lifecycle lifecycle);

        void attachView(V view);

        void detachView();

        V getView();

        boolean isViewAttached();

        void onPresenterCreate();

        void onPresenterDestroy();
    }
}
