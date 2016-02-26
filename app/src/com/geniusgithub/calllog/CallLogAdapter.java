package com.geniusgithub.calllog;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geniusgithub.phonetools.R;
import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

public class CallLogAdapter extends RecyclerView.Adapter<ViewHolder>{

    private static final CommonLog log = LogFactory.createLog();
    
	private Context mContext;
    private Cursor mCursor;
    
    
	public CallLogAdapter(Context context){
		super();
		mContext = context;
	}
	
	
   public void changeCursor(Cursor cursor) {
        if (cursor == mCursor) {
            return;
        }

        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = cursor;

        if (cursor != null) {
            notifyDataSetChanged();
        }
    }
   
   
   @Override
   public int getItemCount() {
       if (mCursor == null) {
           return 0;
       }

       return mCursor.getCount();
   }
	
    public Object getItem(int position) {
        if (mCursor == null) {
            return null;
        }
        mCursor.moveToPosition(position);
        return mCursor;
    }
    
    
	
	private final int NORMAL_CALLLOG_TYPE = 0;
	
    @Override
    public int getItemViewType(int position) {
    	return NORMAL_CALLLOG_TYPE;
    }

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {

        switch (getItemViewType(position)) {
            case NORMAL_CALLLOG_TYPE:
                bindCallLogListViewHolder(viewHolder, position);
                break;
            default:
                bindCallLogListViewHolder(viewHolder, position);
                break;
        }

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

		switch (viewType) {
		case NORMAL_CALLLOG_TYPE:
			return createCallLogEntryViewHolder(viewGroup);

		default:
			break;
		}

		return createCallLogEntryViewHolder(viewGroup);
	}
	
	
	
    private ViewHolder createCallLogEntryViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.call_log_list_item, parent, false);
        CallLogListItemViewHolder viewHolder = new CallLogListItemViewHolder(view);

        return viewHolder;
    }
    
    private void bindCallLogListViewHolder(ViewHolder viewHolder, int position) {
        Cursor cursor = (Cursor) getItem(position);
        if (cursor == null) {
            return;
        }
        
        CallLogListItemViewHolder callLogListItemViewHolder = (CallLogListItemViewHolder) viewHolder;


        final String number = cursor.getString(CallLogQuery.NUMBER);
        final long date = cursor.getLong(CallLogQuery.DATE);
       
        callLogListItemViewHolder.setNumber(number);
        callLogListItemViewHolder.setDate(MyDataUtils.getDateShort(date));
    }
    
    


}
