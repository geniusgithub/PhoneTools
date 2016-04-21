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

import java.util.ArrayList;

import com.geniusgithub.phonetools.util.AlwaysLog;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telecom.PhoneAccountHandle;
import android.text.TextUtils;

/**
 * Information for a contact as needed by the Call Log.
 */
public final class DialerSearchBean {
	private final static String TAG = "DialerSearchBean";
	
	public long callogId;
    public Uri lookupUri;
    public String name;
    public String mNickName;
    public int type = 0;
    public String label;
    public String number;
    public String formattedNumber;
    public String normalizedNumber;
    public String mPhotoUri;
    public long mPhotoId;
    public String mLookupkey;

    // The following lines are provided and maintained by Mediatek Inc.
    //-1 indicates phone contacts, >0 indicates sim id for sim contacts.
    public int simId;
    public long duration;
    public String countryIso;
    public int vtCall = 0;
    public String geocode;
    public int contactSimId;
    public long date;
    public int nNumberTypeId;
    public int isRead;
    public String ipPrefix;

    public int isSdnContact;
    public int count = 1;
    public int mNumberPesentation=1;
    
    public String pinYin;
    public String dataOffsets;
    public String nameOffsets;
    
	public String mYPSourceId;	//for yellow page
	public String mYPSystemId;	//for yellow page
	public String mNamePinyin;	//for yellow page
    public int mIndex;
    public long lContactID;
    public boolean isConferenceCall = false;
    public String firstNumber = "";
    public long conference_id = 0;
    public ArrayList<String> conferenceList;
    public int mSearchInfoType;
    
    public static DialerSearchBean getBeanfromCallLogCursor(Context context, Cursor cursor) {

        DialerSearchBean dialerSearchBean = new DialerSearchBean();
    	dialerSearchBean.callogId = cursor.getLong(CallLogQuery.ID);
    	dialerSearchBean.number = cursor.getString(CallLogQuery.NUMBER);     
    	dialerSearchBean.formattedNumber = dialerSearchBean.number;
        dialerSearchBean.date =  cursor.getLong(CallLogQuery.DATE);
        dialerSearchBean.duration =  cursor.getLong(CallLogQuery.DURATION);
        dialerSearchBean.type = cursor.getInt(CallLogQuery.CALL_TYPE);
        dialerSearchBean.simId = cursor.getInt(CallLogQuery.ACCOUNT_ID);
        dialerSearchBean.vtCall = cursor.getInt(CallLogQuery.FEATURES);

        return dialerSearchBean;
    }

  
    
    public static DialerSearchBean EMPTY = new DialerSearchBean();

    @Override
    public int hashCode() {
        // Uses only name and contactUri to determine hashcode.
        // This should be sufficient to have a reasonable distribution of hash codes.
        // Moreover, there should be no two people with the same lookupUri.
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lookupUri == null) ? 0 : lookupUri.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DialerSearchBean other = (DialerSearchBean) obj;
        if (date != other.date) return false;
//        if (!UriUtils.areEqual(lookupUri, other.lookupUri)) return false;
        if (!TextUtils.equals(name, other.name)) return false;
        if (type != other.type) return false;
        if (!TextUtils.equals(label, other.label)) return false;
        if (!TextUtils.equals(number, other.number)) return false;
        if (!TextUtils.equals(formattedNumber, other.formattedNumber)) return false;
        if (!TextUtils.equals(normalizedNumber, other.normalizedNumber)) return false;
//        if (!UriUtils.areEqual(photoUri, other.photoUri)) return false;
        if (contactSimId != other.contactSimId) return false;
        if (isRead != other.isRead) return false;
        return true;
    }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("[name=").append(name).append("],");
    	sb.append("[number=").append(number).append("],");
    	sb.append("[type=").append(type).append("],");
    	sb.append("[formattedNumber=").append(formattedNumber).append("],");
    	sb.append("[simId=").append(simId).append("],");
    	sb.append("[duration=").append(duration).append("],");
    	sb.append("[mNumberPesentation=").append(mNumberPesentation).append("],");
    	sb.append("[lContactID=").append(lContactID).append("],");
    	sb.append("[mIndex=").append(mIndex).append("]");
    	return sb.toString();
    }
}
