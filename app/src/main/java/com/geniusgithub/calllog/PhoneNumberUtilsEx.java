package com.geniusgithub.calllog;

import com.geniusgithub.phonetools.util.CommonLog;
import com.geniusgithub.phonetools.util.LogFactory;

import android.os.SystemProperties;
import android.service.voice.AlwaysOnHotwordDetector;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;

public class PhoneNumberUtilsEx {

    private static final CommonLog log = LogFactory.createLog();
    
    /*
     * Special characters
     *
     * (See "What is a phone number?" doc)
     * 'p' --- GSM pause character, same as comma
     * 'n' --- GSM wild character
     * 'w' --- GSM wait character
     */
    public static final char PAUSE = ',';
    public static final char WAIT = ';';
    public static final char WILD = 'N';

    
    
    
    /** True if c is ISO-LATIN characters 0-9, *, # , +, WILD  */
    public final static boolean
    isDialable(char c) {
        return (c >= '0' && c <= '9') || c == '*' || c == '#' || c == '+' || c == WILD;
    }
    
    
    /** This any anything to the right of this char is part of the
     *  post-dial string (eg this is PAUSE or WAIT)
     */
    public final static boolean
    isStartsPostDial (char c) {
        return c == PAUSE || c == WAIT;
    }
    
    
    /**
     * Returns Default voice subscription Id.
     */
    private static int getDefaultVoiceSubId() {
    	  return SubscriptionManager.getDefaultVoiceSubId();
    }
    
    private static int getSlotId(int subID) {
        int slotId = SubscriptionManager.getSlotId(subID);
        return slotId;
    }
    
    
    /**
     * Checks a given number against the list of
     * emergency numbers provided by the RIL and SIM card.
     *
     * @param number the number to look up.
     * @return true if the number is in the list of emergency numbers
     *         listed in the RIL / SIM, otherwise return false.
     */
    public static boolean isEmergencyNumber(String number, StringBuffer sBuffer) {
        return isEmergencyNumber(getDefaultVoiceSubId(), number, sBuffer);
    }
    
    /**
     * Checks a given number against the list of
     * emergency numbers provided by the RIL and SIM card.
     *
     * @param subId the subscription id of the SIM.
     * @param number the number to look up.
     * @return true if the number is in the list of emergency numbers
     *         listed in the RIL / SIM, otherwise return false.
     * @hide
     */
    public static boolean isEmergencyNumber(int subId, String number, StringBuffer sBuffer) {
        // Return true only if the specified number *exactly* matches
        // one of the emergency numbers listed by the RIL / SIM.
        return isEmergencyNumberInternal(subId, number, true /* useExactMatch */, sBuffer);
    }
    
    /**
     * Helper function for isEmergencyNumber(String) and
     * isPotentialEmergencyNumber(String).
     *
     * @param subId the subscription id of the SIM.
     * @param number the number to look up.
     *
     * @param useExactMatch if true, consider a number to be an emergency
     *           number only if it *exactly* matches a number listed in
     *           the RIL / SIM.  If false, a number is considered to be an
     *           emergency number if it simply starts with the same digits
     *           as any of the emergency numbers listed in the RIL / SIM.
     *           (Setting useExactMatch to false allows you to identify
     *           number that could *potentially* result in emergency calls
     *           since many networks will actually ignore trailing digits
     *           after a valid emergency number.)
     *
     * @return true if the number is in the list of emergency numbers
     *         listed in the RIL / sim, otherwise return false.
     */
    private static boolean isEmergencyNumberInternal(int subId, String number,
            boolean useExactMatch, StringBuffer sBuffer) {
        return isEmergencyNumberInternal(subId, number, null, useExactMatch, sBuffer);
    }
    
    /**
     * Helper function for isEmergencyNumber(String, String) and
     * isPotentialEmergencyNumber(String, String).
     *
     * @param subId the subscription id of the SIM.
     * @param number the number to look up.
     * @param defaultCountryIso the specific country which the number should be checked against
     * @param useExactMatch if true, consider a number to be an emergency
     *           number only if it *exactly* matches a number listed in
     *           the RIL / SIM.  If false, a number is considered to be an
     *           emergency number if it simply starts with the same digits
     *           as any of the emergency numbers listed in the RIL / SIM.
     *
     * @return true if the number is an emergency number for the specified country.
     * @hide
     */
    private static boolean isEmergencyNumberInternal(int subId, String number,
                                                     String defaultCountryIso,
                                                     boolean useExactMatch, StringBuffer sBuffer) {
    	
        // If the number passed in is null, just return false:
        if (number == null) return false;

        // If the number passed in is a SIP address, return false, since the
        // concept of "emergency numbers" is only meaningful for calls placed
        // over the cell network.
        // (Be sure to do this check *before* calling extractNetworkPortionAlt(),
        // since the whole point of extractNetworkPortionAlt() is to filter out
        // any non-dialable characters (which would turn 'abc911def@example.com'
        // into '911', for example.))
        if (isUriNumber(number)) {
            return false;
        }

        // Strip the separators from the number before comparing it
        // to the list.
        number = extractNetworkPortionAlt(number);
      

        String emergencyNumbers = "";
        int slotId = getSlotId(subId);
        
        String value1 = "subId:" + subId + ", slotid = " + slotId + ", extractNetworkPortionAlt number = " + number;
        log.d(value1);

        // retrieve the list of emergency numbers
        // check read-write ecclist property first
        String ecclist = (slotId <= 0) ? "ril.ecclist" : ("ril.ecclist" + slotId);

        emergencyNumbers = SystemProperties.get(ecclist, "");
        String value2 = " SystemProperties.get(" + ecclist + ") = " +  emergencyNumbers;
        log.d(value2);

        String ecclist1 = "ril.ecclist" + 1;
        String emergencyNumbers1 = SystemProperties.get(ecclist1, "");
        String value3 = " SystemProperties.get(" + ecclist1 + ") = " +  emergencyNumbers1;
        log.d(value3);
        String value4 =  " SystemProperties.get(ro.ril.ecclist) = " +  emergencyNumbers1;
        log.d(value4);
        if (TextUtils.isEmpty(emergencyNumbers)) {
            // then read-only ecclist property since old RIL only uses this
            emergencyNumbers = SystemProperties.get("ro.ril.ecclist");

        }
        
        if (sBuffer != null){
        	sBuffer.append(value1 + "\n" + value2 + "\n" + value3 + "\n" + value4);
        }


        if (!TextUtils.isEmpty(emergencyNumbers)) {
            // searches through the comma-separated list for a match,
            // return true if one is found.
            for (String emergencyNum : emergencyNumbers.split(",")) {
                // It is not possible to append additional digits to an emergency number to dial
                // the number in Brazil - it won't connect.
                if (useExactMatch || "BR".equalsIgnoreCase(defaultCountryIso)) {
                    if (number.equals(emergencyNum)) {
                        return true;
                    }
                } else {
                    if (number.startsWith(emergencyNum)) {
                        return true;
                    }
                }
            }
            // no matches found against the list!
            return false;
        }

        log.e( "System property doesn't provide any emergency numbers."
                + " Use embedded logic for determining ones.");

        // If slot id is invalid, means that there is no sim card.
        // According spec 3GPP TS22.101, the following numbers should be
        // ECC numbers when SIM/USIM is not present.
        emergencyNumbers = ((slotId < 0) ? "112,911,000,08,110,118,119,999" : "112,911");

        for (String emergencyNum : emergencyNumbers.split(",")) {
            if (useExactMatch) {
                if (number.equals(emergencyNum)) {
                    return true;
                }
            } else {
                if (number.startsWith(emergencyNum)) {
                    return true;
                }
            }
        }

        // No ecclist system property, so use our own list.
//        if (defaultCountryIso != null) {
//            ShortNumberUtil util = new ShortNumberUtil();
//            if (useExactMatch) {
//                return util.isEmergencyNumber(number, defaultCountryIso);
//            } else {
//                return util.connectsToEmergencyNumber(number, defaultCountryIso);
//            }
//        }

        return false;
    }
    
    
    /**
     * Determines if the specified number is actually a URI
     * (i.e. a SIP address) rather than a regular PSTN phone number,
     * based on whether or not the number contains an "@" character.
     *
     * @hide
     * @param number
     * @return true if number contains @
     */
    public static boolean isUriNumber(String number) {
        // Note we allow either "@" or "%40" to indicate a URI, in case
        // the passed-in string is URI-escaped.  (Neither "@" nor "%40"
        // will ever be found in a legal PSTN number.)
        return number != null && (number.contains("@") || number.contains("%40"));
    }
    
    /**
     * Extracts the network address portion and canonicalize.
     *
     * This function is equivalent to extractNetworkPortion(), except
     * for allowing the PLUS character to occur at arbitrary positions
     * in the address portion, not just the first position.
     *
     * @hide
     */
    public static String extractNetworkPortionAlt(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }

        int len = phoneNumber.length();
        StringBuilder ret = new StringBuilder(len);
        boolean haveSeenPlus = false;

        for (int i = 0; i < len; i++) {
            char c = phoneNumber.charAt(i);
            if (c == '+') {
                if (haveSeenPlus) {
                    continue;
                }
                haveSeenPlus = true;
            }
            if (isDialable(c)) {
                ret.append(c);
            } else if (isStartsPostDial (c)) {
                break;
            }
        }

        return ret.toString();
    }
}
