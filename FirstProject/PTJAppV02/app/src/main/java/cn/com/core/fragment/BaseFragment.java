package cn.com.core.fragment;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    protected Activity mActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity)context;
    }
}
