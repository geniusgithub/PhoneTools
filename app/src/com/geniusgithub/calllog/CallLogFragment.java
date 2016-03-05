/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geniusgithub.calllog;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geniusgithub.phonetools.R;
import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

/**
 * Displays a list of call log entries. To filter for a particular kind of call
 * (all, missed or voicemails), specify it in the constructor.
 */
public class CallLogFragment extends Fragment implements CallLogQueryHandler.Listener{
    private static final String TAG = "CallLogFragment";

    private static final CommonLog log = LogFactory.createLog();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CallLogAdapter mAdapter;
    
    private CallLogQueryHandler mCallLogQueryHandler;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        final Activity activity = getActivity();
        final ContentResolver resolver = activity.getContentResolver();
        
        mCallLogQueryHandler = new CallLogQueryHandler(activity, resolver, this);
        
        setHasOptionsMenu(true);
    }
    
    
    @Override
	public void onDestroy() {

    	mCallLogQueryHandler.cancelListener();
    	
		super.onDestroy();
	}

    
//    /** Called by the CallLogQueryHandler when the list of calls has been fetched or updated. */
//    @Override
//    public boolean onCallsFetched(Cursor cursor) {
//        if (getActivity() == null || getActivity().isFinishing()) {
//            // Return false; we did not take ownership of the cursor
//            return false;
//        }
//        mAdapter.setLoading(false);
//        mAdapter.changeCursor(cursor);
//        // This will update the state of the "Clear call log" menu item.
//        getActivity().invalidateOptionsMenu();
//
//        boolean showListView = cursor != null && cursor.getCount() > 0;
//        mRecyclerView.setVisibility(showListView ? View.VISIBLE : View.GONE);
//
//        if (mScrollToTop) {
//            // The smooth-scroll animation happens over a fixed time period.
//            // As a result, if it scrolls through a large portion of the list,
//            // each frame will jump so far from the previous one that the user
//            // will not experience the illusion of downward motion.  Instead,
//            // if we're not already near the top of the list, we instantly jump
//            // near the top, and animate from there.
//            if (mLayoutManager.findFirstVisibleItemPosition() > 5) {
//                // TODO: Jump to near the top, then begin smooth scroll.
//                mRecyclerView.smoothScrollToPosition(0);
//            }
//            // Workaround for framework issue: the smooth-scroll doesn't
//            // occur if setSelection() is called immediately before.
//            mHandler.post(new Runnable() {
//               @Override
//               public void run() {
//                   if (getActivity() == null || getActivity().isFinishing()) {
//                       return;
//                   }
//                   mRecyclerView.smoothScrollToPosition(0);
//               }
//            });
//
//            mScrollToTop = false;
//        }
//        return true;
//    }




	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.call_log_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
  

        mAdapter = new CallLogAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);


        
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public void onResume() {
        super.onResume();
        log.i("onResume");
  //      fetchCalls();
    }

    @Override
    public void onPause() {
        super.onPause();
        
        
    }

    public void fetchCalls() {
        mCallLogQueryHandler.fetchCalls();
    }

	@Override
	public boolean onCallsFetched(Cursor cursor) {
        log.i("onCallsFetched cursor = " + cursor);
		  if (getActivity() == null || getActivity().isFinishing()) {
	            // Return false; we did not take ownership of the cursor
	            return false;
	        }
		  
		  if (cursor != null){
			  log.i("cursor.getCount = " + cursor.getCount());
		  }
		  
	        mAdapter.changeCursor(cursor);
	        return true;
	}

	public class DividerItemDecoration extends RecyclerView.ItemDecoration{
		
	}
}
