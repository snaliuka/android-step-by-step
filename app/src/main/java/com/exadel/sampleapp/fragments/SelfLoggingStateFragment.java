package com.exadel.sampleapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exadel.sampleapp.R;

public class SelfLoggingStateFragment extends Fragment {

    public static final String KEY_TAG = "tag";
    public static final String ARG_HEADER = "arg_header";

    private TextView headerView;
    private LogWriter logWriter;

    private String header;

    @Override
    public void onAttach(Context context) {
        // fragment is attaching to activity to keep the life-cycle synchronized
        super.onAttach(context);
        if (context instanceof LogWriter) {
            logWriter = (LogWriter) context;
        }

        // you can access the arguments this fragment was launched;
        Bundle arguments = getArguments();
        if (arguments != null) {
            header = arguments.getString(ARG_HEADER);
        }

        // WARNING! onCreate() method in parent activity might not be called yet.
        log("onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate(): savedInstanceState empty = " + (savedInstanceState == null));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // the same as setContentView in activity. Sets the layout to be shown
        View view = inflater.inflate(R.layout.fragment_self_state_logging, container, false);
        headerView = view.findViewById(R.id.tv_header);
        log("onCreateView(): savedInstanceState empty = " + (savedInstanceState == null));

        if (header != null) {
            // takes string "Hello!\n%s" from resources and replace %s with value of header
            String formattedHeader = getString(R.string.hello, header);
            headerView.setText(formattedHeader);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        log("onViewCreated(): savedInstanceState empty = " + (savedInstanceState == null));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Called when the fragment's activity has been created and this
        // fragment's view hierarchy instantiated.
        log("onActivityCreated(): savedInstanceState empty = " + (savedInstanceState == null));
    }

    @Override
    public void onStart() {
        super.onStart();
        // fragment is going to be visible
        log("onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        // fragment is going to be foreground
        log("onResume()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // fragment is no more active (not in the foreground) (or activity is going to be destroyed)

        // saved state is also provided to onCreate() method and you can use it there
//        outState.putString(KEY_TAG, tag);
        log("onSaveInstanceState()");
    }

    @Override
    public void onPause() {
        super.onPause();
        log("onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        log("onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log("onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log("onDetach()");
    }

    private void log(String message) {
        if (logWriter != null) {
            String tag = header == null ? "No header" : header;
            logWriter.log(tag, message);
        }
        // default logger from andorid SDK.
        Log.d(this.getClass().getSimpleName(), message);
    }

    public static SelfLoggingStateFragment newInstance(String header) {

        Bundle args = new Bundle();
        args.putString(ARG_HEADER, header);

        SelfLoggingStateFragment fragment = new SelfLoggingStateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface LogWriter {
        void log(CharSequence tag, String message);
    }
}
