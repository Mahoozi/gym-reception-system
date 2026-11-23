/*
 * Gym Reception System
 * Author: Yusuf Almahoozi
 *
 * GymCard represents a membership card issued to a gym member. It stores the
 * membership type, the card holder's name, and the card's expiry date
 * (day and month). Day and month values are validated: anything outside
 * 1-31 (day) or 1-12 (month) is stored as 0. isValidExpiry also checks the
 * day against the length of the month, so callers can reject dates such as
 * 31/02 before creating or updating a card.
 *
 * Reception keeps an array of GymCard objects for the cards registered at
 * each reception desk.
 */
public class GymCard {

    // ---------------------------------------------------------
    // Fields
    // ---------------------------------------------------------
    private String type;  // Membership type (e.g. Basic, Standard, Premium, PremiumPlus)
    private String name;  // Name of the card holder
    private int    day;   // Expiry day (1-31), or 0 if invalid/unset
    private int    month; // Expiry month (1-12), or 0 if invalid/unset

    // ---------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------

    /** Creates an empty card with no type, no name, and an unset expiry date. */
    public GymCard() {
        this.type  = null;
        this.name  = null;
        this.day   = 0;
        this.month = 0;
    }

    /** Creates a card with the given details; an out-of-range day or month is stored as 0. */
    public GymCard(String type, String name, int day, int month) {
        this.type = type;
        this.name = name;

        if (day >= 1 && day <= 31)
            this.day = day;
        else
            this.day = 0;

        if (month >= 1 && month <= 12)
            this.month = month;
        else
            this.month = 0;
    }

    /** Copy constructor: creates an independent copy of another card (or an empty card if null). */
    public GymCard(GymCard other) {
        if (other == null) {
            this.type  = null;
            this.name  = null;
            this.day   = 0;
            this.month = 0;
            return;
        }

        this.type = other.type;
        this.name = other.name;

        if (other.day >= 1 && other.day <= 31)
            this.day = other.day;
        else
            this.day = 0;

        if (other.month >= 1 && other.month <= 12)
            this.month = other.month;
        else
            this.month = 0;
    }

    // ---------------------------------------------------------
    // Expiry date validation
    // ---------------------------------------------------------

    // Days in each month. February allows 29 because cards store no year.
    private static final int[] DAYS_IN_MONTH = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /** True if day/month is a real calendar date (month 1-12, day within that month). */
    public static boolean isValidExpiry(int day, int month) {
        if (month < 1 || month > 12)
            return false;
        return day >= 1 && day <= DAYS_IN_MONTH[month - 1];
    }

    // ---------------------------------------------------------
    // Accessors
    // ---------------------------------------------------------
    public String type()  { return type;  }
    public String name()  { return name;  }
    public int    day()   { return day;   }
    public int    month() { return month; }

    // ---------------------------------------------------------
    // Mutators (day and month are validated the same way as in the constructor)
    // ---------------------------------------------------------
    public void settype(String type) {
        this.type = type;
    }

    public void setname(String name) {
        this.name = name;
    }

    public void setDay(int day) {
        if (day >= 1 && day <= 31)
            this.day = day;
        else
            this.day = 0;
    }

    public void setmonth(int month) {
        if (month >= 1 && month <= 12)
            this.month = month;
        else
            this.month = 0;
    }

    // ---------------------------------------------------------
    // Display: type, holder name and expiry date as DD/MM
    // ---------------------------------------------------------
    public String toString() {
        String dayString   = (day   < 10 ? "0" + day   : "" + day);
        String monthString = (month < 10 ? "0" + month : "" + month);
        // Example: Basic - Scrump - 25/12.
        return type + " - " + name + " - " + dayString + "/" + monthString + ".";
    }

    // ---------------------------------------------------------
    // Comparison: two cards are equal when type, name and expiry date all match
    // ---------------------------------------------------------
    public boolean equals(GymCard other) {
        if (other == null)
            return false;

        return this.type.equals(other.type) &&
               this.name.equals(other.name) &&
               this.day == other.day &&
               this.month == other.month;
    }
}
