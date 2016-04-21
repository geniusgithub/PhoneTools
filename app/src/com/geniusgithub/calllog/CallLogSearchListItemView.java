package com.geniusgithub.calllog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusgithub.phonetools.R;
import com.geniusgithub.phonetools.util.AlwaysLog;
import com.geniusgithub.phonetools.util.DebugLog;
import com.geniusgithub.phonetools.util.ViewUtil;
import com.geniusgithub.phonetools.util.ViewUtil;

public class CallLogSearchListItemView extends ViewGroup {

    private static final String TAG = "CallLogSearchListItemView";
    private Context mContext;
    
    /*************** all call log list item view define begin *****************/
    
    //  Header(Date) The view inside the call log list item view
    private ImageView mSubHeaderDivider;
    private Drawable  mDividerDrawable;
//    private TextView mCallDateTextView;
    
    // first line 3 elements
    private TextView mNameTextView;
//    private TextView mCallCountTextView;
//    private ImageView mVerticalDivider;
    private TextView mSignTextView;
    
    // second line 6 elements
    private ImageView mCallTypeIcon;
    private TextView mNumberTextView;
    private TextView mLocaleTextView;    
    private ImageView mCardIconImageView;
//    private TextView mCardNameTextView;    
    private TextView mDurationTextView;
    
    // right side 3 elements which occupy two line's space
    private TextView mCallTimeTextView;
    private ImageButton mCallDetailImageButton;
    private CheckBox mMultipleSelectCheckBox;

    private View mEmptyView;
    private ImageView mPhotoView;
    private ImageView mMaskPhotoView;
    /*************** all call log list item view define end ****************/
    
    private boolean mIsCallLogDateVisible;
    private int mCallLogDateBackgroundColor;
    private int mCallTimeTextColor;
    private int mCallDateHeight;                // height of call date 
    private int mPaddingLeft;                   // padding left for call log list item view;
//    private int mPaddingTop;                    // padding top for call log list item view;
    private int mPaddingRight;                  // padding right for call log list item view;
//    private int mPaddingBottom;                 // padding bottom for call log list item view;
    private int mCallDetailImageWidthSize;            // callDetail icon width size
    private int mCallDetailImageHeightSize;            // callDetail icon height size
    private int mCallDetailPaddingRight;            // callDetail icon paddingright
    private int mItemHeight;                    // height of call log list item view;
    private int mHorizontalMargin;              // horizontal margin between views;
    private int mSpecialHorizontalMargin;      // horizontal margin between name and call time;
    private int mCheckboxMarginLeft;            // 
    private int mSmallTextSize;                 // 
    private int mNormalTextSize;                 //
    private int mVerticalMargin;                // vertical margin between views
    private int mCallTimeTextViewMarginRight;   // margin right of call time;
//    private int mVerticalDividerWidth;
//    private int mVerticalDividerHeight;
    private int mDefaultPhotoViewSize = 0;

    public static final int[] itemViewIds = new int[] {
        R.id.calllog_multi_calldetail_image_button,
        R.id.calllog_multi_calltype_icon,
        R.id.calllog_multi_duration_textview,
        R.id.calllog_multi_local_textview,
        R.id.calllog_multi_name_textview,
        R.id.calllog_multi_number_textview,
        R.id.calllog_multi_sign_textview, R.id.calllog_multi_empty_view,
        R.id.calllog_multi_check_box, R.id.calllog_multi_header_divider };


    public CallLogSearchListItemView(Context context) {
        super(context);
        mContext = context;
        initialize();
    }
    
    public CallLogSearchListItemView(Context context, AttributeSet attrs) {
        super(context);
        mContext = context;
        initialize();
    }

    private void initialize() {
        initializeResources();
        initializeWidgets();
    }

    private void initializeWidgets() {
        // initial header;
        initialSubHeaderDivider();
        // initial first line elements;
        initialName();
        initialSign();
        initialCallTime();
        
        // initial second line elements;
        initialCallType();
        initialNumber();
        initialLocale();
        initialCardIcon();
        initialCallRingDuration();
        
        // initial right side;
        initialCallDetail();
        initialMultipelSelect();
        initEmptyView();
        // initial photoview
        initialPhotoView();
        initialMaskPhotoView();
    }
    
    private void initialSubHeaderDivider() {
        if (null == mSubHeaderDivider) {
            mSubHeaderDivider = new ImageView(mContext);
            mSubHeaderDivider.setId(R.id.calllog_multi_header_divider);
            mSubHeaderDivider.setBackground(mDividerDrawable);
            mSubHeaderDivider.setVisibility(GONE);
            addView(mSubHeaderDivider);
        }
    }

    private void initialMultipelSelect() {
        if (null == mMultipleSelectCheckBox) {
            mMultipleSelectCheckBox = new CheckBox(mContext);
            mMultipleSelectCheckBox.setId(R.id.calllog_multi_check_box);
            mMultipleSelectCheckBox.setVisibility(View.GONE);
            mMultipleSelectCheckBox.setFocusable(false);
            mMultipleSelectCheckBox.setClickable(false);
            addView(mMultipleSelectCheckBox);
        }
    }

    private void initEmptyView(){
    	if (null == mEmptyView){
    		mEmptyView = new View(mContext);
            mEmptyView.setId(R.id.calllog_multi_empty_view);
    		mEmptyView.setClickable(true);
    		addView(mEmptyView);
    	}
    }

    private void initialCallDetail() {
        if (null == mCallDetailImageButton) {
            mCallDetailImageButton = new ImageButton(mContext);
            mCallDetailImageButton.setId(R.id.calllog_multi_calldetail_image_button);
         //   mCallDetailImageButton.setImageResource(R.drawable.ic_details);
            mCallDetailImageButton.setImageResource(R.drawable.volte_close);
           // mCallDetailImageButton.setBackground(getResources().getDrawable(R.drawable.ic_details));
            // Caution!!! when image button's focusable is true, the item view in list view cann't receive click event 
            mCallDetailImageButton.setFocusable(false);
            mCallDetailImageButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(mContext, "onClick", Toast.LENGTH_SHORT).show();
				}
			});
            addView(mCallDetailImageButton);
        }
    }

    private void initialCallRingDuration() {
        if (null == mDurationTextView) {
            mDurationTextView = new TextView(mContext);
            mDurationTextView.setId(R.id.calllog_multi_duration_textview);
            mDurationTextView.setTextAppearance(mContext, R.style.Listitem_TextAppearanceSecond);
            addView(mDurationTextView);
        }
    }

    private void initialCardIcon() {
        if (null == mCardIconImageView) {
            mCardIconImageView = new ImageView(mContext);
            mCardIconImageView.setId(R.id.calllog_multi_card_icon);
            addView(mCardIconImageView);
        }
    }

    private void initialLocale() {
        if (null == mLocaleTextView) {
            mLocaleTextView = new TextView(mContext);
            mLocaleTextView.setId(R.id.calllog_multi_local_textview);
            mLocaleTextView.setTextAppearance(mContext, R.style.Listitem_TextAppearanceSecond);
            addView(mLocaleTextView);
        }
    }

    private void initialNumber() {
        if (null == mNumberTextView) {
            mNumberTextView = new TextView(mContext);
            mNumberTextView.setId(R.id.calllog_multi_number_textview);
            mNumberTextView.setTextAppearance(mContext, R.style.Listitem_TextAppearanceSecond);
            addView(mNumberTextView);
        }
    }

    private void initialCallType() {
        if (null == mCallTypeIcon) {
            mCallTypeIcon = new ImageView(mContext);
            mCallTypeIcon.setId(R.id.calllog_multi_calltype_icon);
            addView(mCallTypeIcon);
        }
    }

    private void initialCallTime() {
        if (null == mCallTimeTextView) {
            mCallTimeTextView = new TextView(mContext);
            mCallTimeTextView.setId(R.id.calllog_multi_calltime_textview);
            mCallTimeTextView.setTextColor(mCallTimeTextColor);
            mCallTimeTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallTextSize);
            addView(mCallTimeTextView);
        }
    }

    private void initialSign() {
        if (null == mSignTextView) {
            mSignTextView = new TextView(mContext);
            mSignTextView.setId(R.id.calllog_multi_sign_textview);
            mSignTextView.setTextColor(mCallTimeTextColor);
            mSignTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mNormalTextSize);
            addView(mSignTextView);
        }
    }

    private void initialName() {
        if (null == mNameTextView) {
            mNameTextView = new TextView(mContext);
            mNameTextView.setId(R.id.calllog_multi_name_textview);
            mNameTextView.setTextAppearance(mContext, R.style.Listitem_TextAppearancePrimary);
            mNameTextView.setSingleLine(true);
            addView(mNameTextView);
        }
    }

    private void initialPhotoView() {
        if (null == mPhotoView) {
            mPhotoView = new ImageView(mContext);
            mPhotoView.setId(R.id.calllog_multi_photo_view);
            mPhotoView.setLayoutParams(getDefaultPhotoLayoutParams());
            // Quick contact style used above will set a background - remove it
            mPhotoView.setBackground(null);
            mPhotoView.setFocusable(false);
            addView(mPhotoView);
        }
    }
    private void initialMaskPhotoView() {
        if (null == mMaskPhotoView) {
            mMaskPhotoView = new ImageView(mContext);
            mMaskPhotoView.setId(R.id.calllog_multi_mask_photo_view);
            mMaskPhotoView.setLayoutParams(getDefaultPhotoLayoutParams());
            mMaskPhotoView.setFocusable(false);
            addView(mMaskPhotoView);
        }
    }
    private void initializeResources() {
//        TypedArray typeArray = mContext.getTheme().obtainStyledAttributes(new int[] {R.attr.lenovo_listitem_padding_left});
        mPaddingLeft = (int) getResources().getDimension(R.dimen.lenovo_listitem_padding_left);
//        mPaddingTop = (int) getResources().getDimension(R.dimen.lenovo_listitem_padding_top);
        mPaddingRight = (int) getResources().getDimension(R.dimen.dialpad_listitem_horizontal_inner_margin);// this is special so not use lenovo_listitem_padding_right
//        mPaddingBottom = (int) getResources().getDimension(R.dimen.lenovo_listitem_padding_bottom);
        mCallDetailImageWidthSize = (int) getResources().getDimension(R.dimen.dialpad_listitem_calldetail_width_size);
        mCallDetailImageHeightSize = (int) getResources().getDimension(R.dimen.dialpad_listitem_calldetail_height_size);
        mCallDetailPaddingRight =  (int) getResources().getDimension(R.dimen.dialpad_listitem_calldetail_padding_right);
        mItemHeight = (int) getResources().getDimension(R.dimen.lenovo_listitem_minheight_doubleline);
        mCallDateHeight = (int) getResources().getDimension(R.dimen.calllog_listitem_subheader_minheight);
        mVerticalMargin = (int) getResources().getDimension(R.dimen.lenovo_listitem_vertical_inner_margin);
        mHorizontalMargin = (int) getResources().getDimension(R.dimen.calllog_listitem_horizontal_inner_margin);
        mSpecialHorizontalMargin =  (int) getResources().getDimension(R.dimen.dialpad_listitem_horizontal_specail_margin);
        mCheckboxMarginLeft =  (int) getResources().getDimension(R.dimen.lenovo_listitem_gap_between_checkbox_and_data);
        mSmallTextSize = (int) getResources().getDimension(R.dimen.lenovo_default_textsize_small);
        mNormalTextSize = (int) getResources().getDimension(R.dimen.lenovo_default_textsize_normal);
//        mVerticalDividerWidth = (int) getResources().getDimension(R.dimen.dialpad_listitem_vertical_divider_width);
//        mVerticalDividerHeight = (int) getResources().getDimension(R.dimen.dialpad_listitem_vertical_divider_height);
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(new int[] { R.attr.lenovo_listitem_subheader_background, 
                R.attr.lenovo_default_time, /*R.attr.lenovo_listitem_subheader_divider */android.R.attr.listDivider });
        mCallLogDateBackgroundColor = typedArray.getColor(0, 0);
        mCallTimeTextColor = typedArray.getColor(1, 0xffffffff);
        mDividerDrawable = typedArray.getDrawable(2);
        typedArray.recycle();

        TypedArray a = mContext.obtainStyledAttributes(R.styleable.LenovoListItemView);
        mDefaultPhotoViewSize = a.getDimensionPixelOffset(
                R.styleable.LenovoListItemView_lenovo_listitem_photo_size,
                mDefaultPhotoViewSize);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = getDefaultSize(0, widthMeasureSpec);// TODO limx3 what this different with resolveSize methods
        int measuredHeight = getDefaultSize(0, heightMeasureSpec);

        if (ViewUtil.isVisible(mNameTextView)) {
            mNameTextView.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        }        

//        if (IdeafriendUtils.isVisible(mCallCountTextView)) {
//            mCallCountTextView.measure(
//                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
//                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//        }
        
//        if (IdeafriendUtils.isVisible(mVerticalDivider)) {
//            mVerticalDivider.measure(
//                    MeasureSpec.makeMeasureSpec(mVerticalDividerWidth, MeasureSpec.EXACTLY),
//                    MeasureSpec.makeMeasureSpec(mVerticalDividerHeight, MeasureSpec.EXACTLY));
//        }
        
        if (mSignTextView != null) {
            mSignTextView.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        }

        if (ViewUtil.isVisible(mCallTimeTextView)) {
            mCallTimeTextView.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        }
        
        if (ViewUtil.isVisible(mCallTypeIcon)) {
            mCallTypeIcon.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        }
        // ignore this comment: mNumberTextView will be gone if corresponding name doesn't exist, but we need it's height to be the second line height for layout.
        if (ViewUtil.isVisible(mNumberTextView)) {
            mNumberTextView.measure(
                    MeasureSpec.makeMeasureSpec(0,  MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        }
        
        if (ViewUtil.isVisible(mLocaleTextView)) {
            mLocaleTextView.measure(
                    MeasureSpec.makeMeasureSpec(0,  MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        }
        
        
        if (ViewUtil.isVisible(mCardIconImageView)) {
            mCardIconImageView.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        }      
        
//        if(IdeafriendUtils.isVisible(mCardNameTextView)) {
//            mCardNameTextView.measure(
//                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
//                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//        }
        
        if (ViewUtil.isVisible(mDurationTextView)) {
            mDurationTextView.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        }
        
        if (ViewUtil.isVisible(mCallDetailImageButton)) {
                mCallDetailImageButton.measure(mCallDetailImageWidthSize, mCallDetailImageHeightSize);
        }

        if (ViewUtil.isVisible(mMultipleSelectCheckBox)) {
            mMultipleSelectCheckBox.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        }
        
        if (ViewUtil.isVisible(mSubHeaderDivider)) {
            mSubHeaderDivider.measure(
                    MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        }
        
        if(ViewUtil.isVisible(mEmptyView)){
        	mEmptyView.measure(
        			MeasureSpec.makeMeasureSpec(mPaddingLeft, MeasureSpec.EXACTLY),
        			MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY));
        }
        
        int adjustHeight;
        if (mIsCallLogDateVisible) {
            adjustHeight = mItemHeight + mCallDateHeight;
        } else {
            adjustHeight = mItemHeight;
        }
        if (ViewUtil.isVisible(mSubHeaderDivider)) {
            adjustHeight += mSubHeaderDivider.getMeasuredHeight();
        }
        setMeasuredDimension(measuredWidth, adjustHeight);
        
        if (ViewUtil.isVisible(mPhotoView)) {
            mPhotoView.measure(
                    MeasureSpec.makeMeasureSpec(mDefaultPhotoViewSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(mDefaultPhotoViewSize, MeasureSpec.EXACTLY));
        }
        if (ViewUtil.isVisible(mMaskPhotoView)) {
            mMaskPhotoView.measure(
                    MeasureSpec.makeMeasureSpec(mDefaultPhotoViewSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(mDefaultPhotoViewSize, MeasureSpec.EXACTLY));
        }
    }
    

    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = right - left;
        int height = bottom - top;
        
        int leftBound = mPaddingLeft;
        int topBound = 0/*mPaddingTop*/; // centered vertically, don't set vertical padding required by GUI.
        int rightBound = width - mPaddingRight;
//        int dividerTop = IdeafriendUtils.isVisible(mSubHeaderDivider) ? height - mSubHeaderDivider.getMeasuredHeight() : height/* - mPaddingBottom*/;
        int dividerTop = height - mSubHeaderDivider.getMeasuredHeight();
        // layout header
//        if (mIsCallLogDateVisible) {
//            mCallDateTextView.layout(0, 0, width, mCallDateHeight);
//            topBound += mCallDateHeight;
//            DebugLog.d(TAG, "onLayout: " + "left=" + left + ", top=" + top + ", width=" + width + ", height=" + height);
//        }
        //layout left side
        layoutLeftSide(changed, 0, topBound, leftBound, dividerTop);

        
        // layout right side
        rightBound = layoutRightSide(changed, leftBound, topBound, rightBound, dividerTop);

        /**
         * first line and second line layout centered vertically, and between which mVerticalMargin length vertically  :
         * 
         */
        int firstLineHeight = mNameTextView.getMeasuredHeight();
        int secondLineHeight;
        if (ViewUtil.isVisible(mNumberTextView)) {
            secondLineHeight = mNumberTextView.getMeasuredHeight();
        } else if (ViewUtil.isVisible(mLocaleTextView)) {
            secondLineHeight = mLocaleTextView.getMeasuredHeight();
        } else if (ViewUtil.isVisible(mDurationTextView)) {
            secondLineHeight = mDurationTextView.getMeasuredHeight();
        } else {
            secondLineHeight = mCallTypeIcon.getMeasuredHeight();
        }
//        int secondLineHeight = mNumberTextView.getMeasuredHeight();
        topBound = topBound + ((dividerTop - topBound) - (firstLineHeight + mVerticalMargin + secondLineHeight)) / 2;
        if (ViewUtil.isVisible(mMaskPhotoView)) {
            // Center the mask photo vertically
            int photoTopBound = (height - mMaskPhotoView.getMeasuredHeight()) / 2;
            mMaskPhotoView.layout(leftBound, photoTopBound, leftBound + mDefaultPhotoViewSize, photoTopBound + mDefaultPhotoViewSize);
        }
        if (ViewUtil.isVisible(mPhotoView)) {
            // Center the photo vertically
            int photoTopBound = (height - mPhotoView.getMeasuredHeight()) / 2;
            mPhotoView.layout(leftBound, photoTopBound, leftBound + mDefaultPhotoViewSize, photoTopBound + mDefaultPhotoViewSize);
            leftBound += mDefaultPhotoViewSize + mPaddingLeft;
        }
        layoutFirstLine(changed, leftBound, topBound, rightBound, dividerTop);
        // layout second line
        topBound += mNameTextView.getMeasuredHeight() + mVerticalMargin;
        layoutSecondLine(changed, leftBound, topBound, rightBound, topBound + secondLineHeight);
        if (ViewUtil.isVisible(mSubHeaderDivider)) {
            if (ViewUtil.isTextPhotoOn()) {
                mSubHeaderDivider.layout(leftBound, dividerTop, width, dividerTop + mSubHeaderDivider.getMeasuredHeight());                
            }else {
                mSubHeaderDivider.layout(0, dividerTop, width, dividerTop + mSubHeaderDivider.getMeasuredHeight());
            }
        }
    }
    
    private void layoutSecondLine(boolean changed, int left, int top, int right, int bottom) {
        int viewWidth;
        int viewHeight;
        int lineHeight = bottom - top;
        if (ViewUtil.isVisible(mCallTypeIcon)) {
            viewWidth = mCallTypeIcon.getMeasuredWidth(); 
            viewHeight = mCallTypeIcon.getMeasuredHeight();
            int viewTop = top + (lineHeight - viewHeight) / 2;
            mCallTypeIcon.layout(left, viewTop, left + viewWidth, viewTop + viewHeight);
            left += (viewWidth + mHorizontalMargin);
        }
        
        if(ViewUtil.isVisible(mDurationTextView)) {
            viewWidth = mDurationTextView.getMeasuredWidth();
            viewHeight = mDurationTextView.getMeasuredHeight();
            int viewTop = top + (lineHeight - viewHeight) / 2;
            mDurationTextView.layout(left, viewTop, left + viewWidth, viewTop + viewHeight);
            left += (viewWidth + mHorizontalMargin);
        }
        
        if (ViewUtil.isVisible(mNumberTextView)) {
            viewWidth = mNumberTextView.getMeasuredWidth();
            viewHeight = mNumberTextView.getMeasuredHeight();
            int remainWidth = right - left - mCheckboxMarginLeft;
            remainWidth = getRemainWidth(remainWidth);
            viewWidth = Math.min(viewWidth, remainWidth);
            TextPaint textPaint = mNumberTextView.getPaint();
            float mNumberTextViewWidth = textPaint.measureText(mNumberTextView.getText().toString());
            if(mNumberTextViewWidth >= viewWidth){
            	mNumberTextView.measure(MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY), MeasureSpec.UNSPECIFIED);
            	mNumberTextView.setSingleLine(true);
            	mNumberTextView.setEllipsize(TruncateAt.END);
            }
            mNumberTextView.layout(left, top, left + viewWidth, top + viewHeight);
            left += (viewWidth + mHorizontalMargin);
        } 
        
        if (ViewUtil.isVisible(mLocaleTextView)) {
            viewWidth = mLocaleTextView.getMeasuredWidth();
            viewHeight = mLocaleTextView.getMeasuredHeight();
            int remainWidth = right - left- mCheckboxMarginLeft;
            remainWidth = getRemainWidth(remainWidth);
            viewWidth = Math.min(viewWidth, remainWidth);
            TextPaint textPaint = mLocaleTextView.getPaint();
            float mNumberTextViewWidth = textPaint.measureText(mLocaleTextView.getText().toString());
            if (mNumberTextViewWidth >= viewWidth) {
				mLocaleTextView.measure(MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY), MeasureSpec.UNSPECIFIED);
				mLocaleTextView.setSingleLine(true);
				mLocaleTextView.setEllipsize(TruncateAt.END);
			}
            
            mLocaleTextView.layout(left, top, left + viewWidth, top + viewHeight);  
            left += (viewWidth + mHorizontalMargin);
        }   
        
//        if(IdeafriendUtils.isVisible(mCardNameTextView)) {
//            viewWidth = mCardNameTextView.getMeasuredWidth();
//            viewHeight = mCardNameTextView.getMeasuredHeight();
//            int viewTop = top + (lineHeight - viewHeight) / 2;
//            mCardNameTextView.layout(left, viewTop, left + viewWidth, viewTop + viewHeight);       
//            left += (viewWidth + mHorizontalMargin);
//        }

        if (ViewUtil.isVisible(mCardIconImageView)) {
            viewWidth = mCardIconImageView.getMeasuredWidth();
            viewHeight = mCardIconImageView.getMeasuredHeight();
            int viewTop = top + (lineHeight - viewHeight) / 2;
            mCardIconImageView.layout(right - viewWidth, viewTop, right, viewTop + viewHeight);
        }
    }
    
    private void layoutFirstLine(boolean changed, int left, int top, int right, int bottom) {
        int viewWidth;
        int viewHeight;
        
        if (ViewUtil.isVisible(mNameTextView)) {
            viewWidth = mNameTextView.getMeasuredWidth();
            viewHeight = mNameTextView.getMeasuredHeight();
            int remainWidth = right - left - mCheckboxMarginLeft;
            
            if (ViewUtil.isVisible(mSignTextView)) {
                remainWidth -= mSignTextView.getMeasuredWidth();
            }

            if (ViewUtil.isVisible(mCallTimeTextView)) {
                remainWidth -= mCallTimeTextView.getMeasuredWidth();
            }

	        TextPaint textPaint = mNameTextView.getPaint();
	        float textPaintWidth = textPaint.measureText(mNameTextView.getText().toString());
	        remainWidth = getRemainWidth(remainWidth);
	        if(textPaintWidth >= remainWidth){
	        	mNameTextView.measure(
	        			MeasureSpec.makeMeasureSpec(remainWidth, MeasureSpec.EXACTLY),
	        			MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
	        	mNameTextView.setEllipsize(TruncateAt.END);
	        }
            
            viewWidth = Math.min(viewWidth, remainWidth);
            
            mNameTextView.layout(left, top, left + viewWidth, top + viewHeight);
            
            left += (viewWidth/* + mSpecialHorizontalMargin*/);
        }
        
        if (ViewUtil.isVisible(mSignTextView)) {
            viewWidth = mSignTextView.getMeasuredWidth();
            viewHeight = mSignTextView.getMeasuredHeight();
            int viewTop = top + (mNameTextView.getMeasuredHeight() - viewHeight) / 2;
            mSignTextView.layout(left, viewTop, left + viewWidth, viewTop + viewHeight);
        }
        
        if (ViewUtil.isVisible(mCallTimeTextView)) {
            viewWidth = mCallTimeTextView.getMeasuredWidth();
            viewHeight = mCallTimeTextView.getMeasuredHeight();
            int viewTop;
            if (ViewUtil.isVisible(mCardIconImageView)) {
                viewTop = top + (mNameTextView.getMeasuredHeight() - viewHeight) / 2;
            } else {
                if (ViewUtil.isTextPhotoOn()) {
                    viewTop = top + (mNameTextView.getMeasuredHeight() - viewHeight) / 2;
                }else {
                    viewTop = (bottom - viewHeight) / 2;
                }
            }
            mCallTimeTextView.layout(right - viewWidth, viewTop, right, viewTop + viewHeight);
        }
    }
    
    private int layoutRightSide(boolean changed, int left, int top, int right, int bottom) {
    
        int viewWidth;
        int viewHeight;
        if (ViewUtil.isVisible(mMultipleSelectCheckBox)) {
            viewWidth = mMultipleSelectCheckBox.getMeasuredWidth();
            viewHeight = mMultipleSelectCheckBox.getMeasuredHeight();
            int viewTop = top + (bottom - top - viewHeight) / 2;
            mMultipleSelectCheckBox.layout(right - viewWidth, viewTop, right, viewTop + viewHeight);
            right -= (viewWidth + mCheckboxMarginLeft);
        }
        
        if (ViewUtil.isVisible(mCallDetailImageButton)) {
            // viewWidth = mCallDetailImageButton.getMeasuredWidth();
            // viewHeight = mCallDetailImageButton.getMeasuredHeight();
            viewWidth = mCallDetailImageWidthSize;
            viewHeight = mCallDetailImageHeightSize;
            int viewTop = top + (bottom - top - viewHeight) / 2;
          //  int viewTop = top;
            right = right + mPaddingRight - mCallDetailPaddingRight;
            mCallDetailImageButton.layout(right - viewWidth, viewTop, right, viewTop  + viewHeight);
            // right -= (viewWidth + mSpecialHorizontalMargin);
            right -= (viewWidth);
        }
        
        right += mCallTimeTextViewMarginRight;
        return right;
    }
    
    private void layoutLeftSide(boolean changed, int left, int top, int right, int bottom){
    	if(ViewUtil.isVisible(mEmptyView)){
    		mEmptyView.layout(left, top, right, bottom);
    	}
    }

    public void setNameTextView(CharSequence name) {
        if (TextUtils.isEmpty(name)) {
            mNameTextView.setVisibility(View.GONE);
        } else {
            mNameTextView.setText(name);
            mNameTextView.setVisibility(View.VISIBLE);
        }
    }
    
    public String getNameFromTextView(Context context){
    	if (mNameTextView != null){
    		return mNameTextView.getText().toString();
    	}else{
    		return "null";
    	}
    }
    
    public TextView getNameTextView(){
    	return mNameTextView;
    }
    
    public String getNumberFromTextView(Context context){
    	if (mNumberTextView != null){
    		return mNumberTextView.getText().toString();
    	}else{
    		return "null";
    	}
    }

    public void setSignTextView(CharSequence sign) {
    	mSignTextView.setTextColor(Color.BLACK);
        if (TextUtils.isEmpty(sign)) {
            mSignTextView.setVisibility(View.GONE);
        } else {
            mSignTextView.setText(sign);
            mSignTextView.setVisibility(View.VISIBLE);
        }
    }

    public void setCallTimeTextView(String callTime) {
    	mCallTimeTextView.setTextColor(Color.BLACK);
        if (TextUtils.isEmpty(callTime)) {
            mCallTimeTextView.setVisibility(View.GONE);
        } else {
            mCallTimeTextView.setText(callTime);
            mCallTimeTextView.setVisibility(View.VISIBLE);
        }
    }

    public void setCallTypeIcon(int callType, int isVTCall) {
        mCallTypeIcon.setVisibility(VISIBLE);
   //     mCallTypeIcon.set(callType, isVTCall);
    }

    public void setNumberTextView(String number) {
        if (TextUtils.isEmpty(number)) {
            mNumberTextView.setVisibility(View.GONE);
        } else {
            mNumberTextView.setText(number);
            mNumberTextView.setVisibility(View.VISIBLE);
        }
    }

    public void setLocaleTextView(String locale) {
        mLocaleTextView.setText(locale);
        if (TextUtils.isEmpty(locale)) {
            mLocaleTextView.setVisibility(View.GONE);
        } else {
            mLocaleTextView.setVisibility(View.VISIBLE);
        }
    }

    public void setDurationTextView(String duration) {
        if (TextUtils.isEmpty(duration)) {
            mDurationTextView.setVisibility(View.GONE);
        } else {
            mDurationTextView.setText(duration);
            mDurationTextView.setVisibility(View.VISIBLE);
        }
    }
    
    public ImageButton getCallDetailImageButton() {
        return mCallDetailImageButton;
    }

    public ImageView getCardIconImageView() {
        return mCardIconImageView;
    }

    public void setCardIconImageView(Drawable d) {
        mCardIconImageView.setImageDrawable(d);
        mCardIconImageView.setVisibility(VISIBLE);
    }

    public CheckBox getMultipleSelectCheckBox() {
        return mMultipleSelectCheckBox;
    }

    public void hideDivider() {
        mSubHeaderDivider.setVisibility(GONE);
    }

    public void showDivier() {
        mSubHeaderDivider.setVisibility(VISIBLE);
    }
    
//    private static final int CALL_DETAIL_DP = 48;
//    private static int sCallDetailSize = updateCallDetailSize(ContactsApplication.getApplication());
//    private static int  updateCallDetailSize(Context context){
//    	
//    	int size = DialerUtils.dip2px(context, CALL_DETAIL_DP);
//    	return size;
//    }

    /**
     * Gets a LayoutParam that corresponds to the default photo size.
     *
     * @return A new LayoutParam.
     */
    private LayoutParams getDefaultPhotoLayoutParams() {
        LayoutParams params = generateDefaultLayoutParams();
        params.width = getDefaultPhotoViewSize();
        params.height = params.width;
        return params;
    }

    protected int getDefaultPhotoViewSize() {
        return mDefaultPhotoViewSize;
    }

    public ImageView getPhotoView() {
        return mPhotoView;
    }

    public ImageView getMaskPhotoView() {
        return mMaskPhotoView;
    }

    public int getRemainWidth(int remainWidth) {
        if (!ViewUtil.isTextPhotoOn()) {
            if (ViewUtil.isVisible(mMaskPhotoView)) {
                remainWidth = remainWidth - mMaskPhotoView.getWidth() - mHorizontalMargin;
            }
        }
        return remainWidth;
    }
}
