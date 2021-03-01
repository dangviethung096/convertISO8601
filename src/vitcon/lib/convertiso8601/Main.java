package vitcon.lib.convertiso8601;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) {
	    // write your code here
        String time = "2021-02-26T08:30:52.542-02:00";
        Calendar dateTime = convertIso8601(time);
        if (dateTime != null) {
            System.out.println(dateTime.getTime().toString());
        }

        System.out.println(TimeZone.getDefault().getDisplayName());
//        createTimeZone();
    }

    /**
     * convert string (with iso8601 time format) to calendar
     * @param iso8601Time
     * @return
     */
    public static Calendar convertIso8601(String iso8601Time) {

        Calendar dateTime = null;
        try {
            // Check format iso 8601 without timezone
            if (iso8601Time.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{0,10}[\\dZ:+-]+")) {
                // Init variable
                dateTime = Calendar.getInstance();
                // Get date and time
                int year = Integer.parseInt(iso8601Time.substring(0, 4));
                int month = Integer.parseInt(iso8601Time.substring(5, 7));
                int day = Integer.parseInt(iso8601Time.substring(8, 10));
                int hour = Integer.parseInt(iso8601Time.substring(11, 13));
                int minute = Integer.parseInt(iso8601Time.substring(14, 16));
                int second = Integer.parseInt(iso8601Time.substring(17, 19));
                // Set value
                dateTime.set(year, month - 1, day, hour, minute, second);

                // Time zone
                // Cut year, month, day
                iso8601Time = iso8601Time.substring(iso8601Time.indexOf('T') + 1);
                // Init time zone
                TimeZone timeZone = null;
                // check time zone
                if (iso8601Time.contains("Z")) {
                    // Get time zone
                    timeZone = TimeZone.getTimeZone("GMT+00:00");
                    dateTime.setTimeZone(timeZone);
                } else if (iso8601Time.contains("+") | iso8601Time.contains("-")) {
                    // Create timezone
                    int tzMinute = Integer.parseInt(iso8601Time.substring(iso8601Time.lastIndexOf(':') + 1));
                    // + or -
                    if (iso8601Time.contains("+")) {
                        int tzHour = Integer.parseInt(iso8601Time.substring(iso8601Time.indexOf('+') + 1, iso8601Time.lastIndexOf(':')));
                        timeZone = TimeZone.getTimeZone("GMT+" + tzHour + ":" + tzMinute);
                    } else {
                        int tzHour = Integer.parseInt(iso8601Time.substring(iso8601Time.indexOf('-') + 1, iso8601Time.lastIndexOf(':')));
                        timeZone = TimeZone.getTimeZone("GMT-" + tzHour + ":" + tzMinute);
                    }

                    dateTime.setTimeZone(timeZone);
                }
            }

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        // Return value
        return dateTime;
    }

    public static void createTimeZone() {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+00:01");
//        for (String id : TimeZone.getAvailableIDs()) {
//            System.out.println(id);
//            System.out.println(TimeZone.getTimeZone(id).getOffset(0)/3600000 + " : " + );
//        }
        System.out.println(timeZone.getOffset(0));
    }
}
