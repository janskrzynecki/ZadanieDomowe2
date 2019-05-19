package com.js.zadaniedomowe2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.js.zadaniedomowe2.tasks.TaskListContent;
//TaskFragment is a Fragment class responsible for displaying a list in a RecyclerVIew.
public class TaskFragment extends Fragment {
    private MyTaskRecyclerViewAdapter myTaskRecyclerViewAdapter;

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    public TaskFragment() {
    }

    @SuppressWarnings("unused")
    public static TaskFragment newInstance(int columnCount) {
        TaskFragment fragment = new TaskFragment ();
        Bundle args = new Bundle ();
        args.putInt ( ARG_COLUMN_COUNT, columnCount );
        fragment.setArguments ( args );
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

        if (getArguments () != null) {
            mColumnCount = getArguments ().getInt ( ARG_COLUMN_COUNT );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_task_list, container, false );

        if (view instanceof RecyclerView) {
            Context context = view.getContext ();
            RecyclerView recyclerView = ( RecyclerView ) view;
            recyclerView.setLayoutManager ( new LinearLayoutManager ( context ) );
            myTaskRecyclerViewAdapter = new MyTaskRecyclerViewAdapter ( TaskListContent.ITEMS, mListener );
            recyclerView.setAdapter ( myTaskRecyclerViewAdapter );
        }
        return view;
    }
//In the code snippet above, firstly it is checked whether the inflated view is an instance of
//RecylerView. Next, a new LinearLayouManager is created to manage the layout of the elements
//of the list and finally an adapter is created that will manage the contents of the list and connects the
//OnListFragmentInteractionListener with the list.

    @Override
    public void onAttach(Context context) {
        super.onAttach ( context );

        if (context instanceof OnListFragmentInteractionListener) {
            mListener = ( OnListFragmentInteractionListener ) context;
        } else {
            throw new RuntimeException ( context.toString ()
                    + " must implement OnListFragmentInteractionListener" );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach ();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK)
        {
            if(data != null)
            {
                boolean changeDataSet = data.getBooleanExtra ( TaskInfoActivity.DATA_CHANGED_KEY, false );
                if(changeDataSet)
                    notifyDataChange ();
            }
        }
    }
    //Camera

    public interface OnListFragmentInteractionListener {

        void OnDeleteClick(int position);
        //This method will be used to pass the onClick events on the elements of the list to the
        //holding Activity
        void onListFragmentClickInteraction(TaskListContent.Task task, int position);
//This method will be used to pass the onLongClick events on the elements of the list to the
//holding Activity
    }
    public void notifyDataChange()
    {
        myTaskRecyclerViewAdapter.notifyDataSetChanged ();

    }
    // following method in the TaskFragment class that will be used to notify the adapter
    //of data set change and refresh the list:
}