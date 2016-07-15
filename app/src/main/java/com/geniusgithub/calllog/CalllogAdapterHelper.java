package com.geniusgithub.calllog;

import android.content.Context;
import android.database.Cursor;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.geniusgithub.phonetools.R;
import com.geniusgithub.phonetools.util.ViewUtil;

public class CalllogAdapterHelper {

	private Context mContext;

	public CalllogAdapterHelper(Context context){
		mContext = context;

	}

    public   void bindView(CallLogSearchListItemView itemView, Context context, Cursor cursor) {
         DialerSearchBean bean = DialerSearchBean.getBeanfromCallLogCursor(mContext, cursor);


         bindDetail(itemView, bean);
         
         if (ViewUtil.isTextPhotoOn()) {
         	itemView.hideDivider();
         }else{
             itemView.showDivier();
         }
         // bind name and number
         bindNameAndNumber(itemView, bean);
         bindSign(itemView, bean);
         bindCallTime(itemView, bean);
         bindCallType(itemView, bean);
         bindLocale(itemView, bean);

         // bind sim card
         bindSimCard(itemView, bean);
         bindDuration(itemView, bean);

         //bind photo
         bindPhoto(itemView, bean);
         
         updatePhotoViewAndMaskPhotoView(itemView, cursor);

    }
    
    public void fillBean(DialerSearchBean bean) {
        bean.name = "genius";
        bean.lContactID = 2;
    }

    public void bindDetail(CallLogSearchListItemView itemView,DialerSearchBean bean) {
        itemView.getCallDetailImageButton().setTag(bean);
    }
    
    
    
    
    public void bindSign(CallLogSearchListItemView itemView, DialerSearchBean bean) {
        String signText = "诈骗电话";
        itemView.setSignTextView(signText);

    }

    public void bindCallTime(CallLogSearchListItemView itemView, DialerSearchBean bean) {
        // bind  call time
        itemView.setCallTimeTextView("1小时前");
    }

    public void bindCallType(CallLogSearchListItemView itemView, DialerSearchBean bean) {
        // bind call type icon
        itemView.setCallTypeIcon(bean.type, bean.vtCall);
    }



    public void bindLocale(CallLogSearchListItemView itemView, DialerSearchBean bean) {

        itemView.setLocaleTextView("北京");

    }
    
    public void emptyLocal(CallLogSearchListItemView itemView){
    	itemView.setLocaleTextView("");
    }

    /**
     *  bind call duration: we show the ring duration for miss call type and operator exclude CMCC only,
     *  ignore and hide the call duration otherwise.
     * @param itemView
     * @param bean
     */
    public void bindDuration(CallLogSearchListItemView itemView, DialerSearchBean bean) {
        String duration = "2分钟";
        		
        		
        itemView.setDurationTextView(duration);
    }
    
  
    /**
     * the item view in multiple select call log ListView is not distributes by date,
     * so we show call date in call time TextView, which is not elegant but effective.
     * @param itemView
     * @param bean
     */
    protected void bindCallDateToCallTime(CallLogSearchListItemView itemView, DialerSearchBean bean) {
        itemView.setCallTimeTextView("2010-1-2 18:00:00");
    }

    /**
     * @param itemView
     * @param dialerSearchBean
     */
    public void bindSimCard(CallLogSearchListItemView itemView, DialerSearchBean dialerSearchBean) {
        itemView.setCardIconImageView(mContext.getResources().getDrawable(R.drawable.ic_list_sim_color));
        itemView.getCardIconImageView().setVisibility(View.VISIBLE);
    }

    
    public void bindNameAndNumber(CallLogSearchListItemView itemView, DialerSearchBean dialerSearchBean) {
    
        CharSequence nameText;
        CharSequence numberText;
        CharSequence displayNumber = dialerSearchBean.number;

    	  if (!TextUtils.isEmpty(dialerSearchBean.name)) {
              nameText = dialerSearchBean.name;
              numberText = displayNumber;
          } else {

              nameText = displayNumber;
              numberText = "";
        
          }
  

        itemView.setNameTextView(nameText);
        itemView.setNumberTextView(numberText.toString());
    }
    
    public void bindPhoto(CallLogSearchListItemView itemView, DialerSearchBean dialerSearchBean) {
    
    }
    
    /** Creates a SpannableString for the given text which is bold and in the given color. */
    private CharSequence addBoldAndColor(CharSequence text, int color) {
        int flags = Spanned.SPAN_INCLUSIVE_INCLUSIVE;
        SpannableString result = new SpannableString(text);
        result.setSpan(new ForegroundColorSpan(color), 0, text.length(), flags);
        return result;
    }

    
    
    private void updatePhotoViewAndMaskPhotoView(View view, Cursor cursor){
        CallLogSearchListItemView itemView = (CallLogSearchListItemView) view;
        if (ViewUtil.isTextPhotoOn()) {
            itemView.getCallDetailImageButton().setVisibility(View.GONE);
            itemView.getPhotoView().setVisibility(View.VISIBLE);
            itemView.getMultipleSelectCheckBox().setVisibility(View.GONE);
            for (int i = 0; i < CallLogSearchListItemView.itemViewIds.length; i++) {
                View needMove =  view.findViewById(CallLogSearchListItemView.itemViewIds[i]);
                if(needMove != null && (needMove).getTranslationX() != 0){
                    (needMove).setTranslationX(0);
                }
            }
            
                itemView.getPhotoView().setClickable(true);
                itemView.getMaskPhotoView().setVisibility(View.GONE);
                itemView.setBackground(null);
            
        }else{
            itemView.getCallDetailImageButton().setVisibility(View.VISIBLE);
            itemView.getPhotoView().setVisibility(View.GONE);

      
                itemView.getMaskPhotoView().setVisibility(View.GONE);
                itemView.setBackground(null);
                for (int i = 0; i < CallLogSearchListItemView.itemViewIds.length; i++) {
                    View needMove =  view.findViewById(CallLogSearchListItemView.itemViewIds[i]);
                    if(needMove != null && (needMove).getTranslationX() != 0){
                        (needMove).setTranslationX(0);
                    }
                }
           
        }
    }
   
}
