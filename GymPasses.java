/*
 * Gym Reception System
 * Author: Yusuf Almahoozi
 *
 * GymPasses tracks how many passes of each type a reception has sold:
 * Regular, Student, Senior, Weekend and Weekly. Each pass type has a fixed
 * price, which is used to calculate the total dollar value of all passes.
 *
 * The class provides constructors, getters and setters, a method to add
 * passes on top of the existing counts, a method to compute the total value,
 * and an equals method that compares two GymPasses by their pass counts.
 */
public class GymPasses {

    // ---------------------------------------------------------
    // Pass counts
    // ---------------------------------------------------------
    private int Regularpass; // Number of Regular passes
    private int Studentpass; // Number of Student passes
    private int Seniorpass;  // Number of Senior passes
    private int Weekendpass; // Number of Weekend passes
    private int Weeklypass;  // Number of Weekly passes

    // ---------------------------------------------------------
    // Price of each pass type, in whole dollars
    // ---------------------------------------------------------
    public static int Regularpass_price = 7;
    public static int Studentpass_price = 5;
    public static int Seniorpass_price  = 4;
    public static int Weekendpass_price = 12;
    public static int Weeklypass_price  = 42;

    // ---------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------

    /** Creates an object with zero passes of every type. */
    public GymPasses() {
        this.Regularpass = 0;
        this.Studentpass = 0;
        this.Seniorpass  = 0;
        this.Weekendpass = 0;
        this.Weeklypass  = 0;
    }

    /** Creates an object with the given number of passes of each type. */
    public GymPasses(int Regularpass, int Studentpass, int Seniorpass, int Weekendpass, int Weeklypass) {
        this.Regularpass = Regularpass;
        this.Studentpass = Studentpass;
        this.Seniorpass  = Seniorpass;
        this.Weekendpass = Weekendpass;
        this.Weeklypass  = Weeklypass;
    }

    /** Copy constructor. */
    public GymPasses(GymPasses other) {
        this.Regularpass = other.Regularpass;
        this.Studentpass = other.Studentpass;
        this.Seniorpass  = other.Seniorpass;
        this.Weekendpass = other.Weekendpass;
        this.Weeklypass  = other.Weeklypass;
    }

    // ---------------------------------------------------------
    // Accessors
    // ---------------------------------------------------------
    public int Regularpass() {
        return Regularpass;
    }

    public int Studentpass() {
        return Studentpass;
    }

    public int Seniorpass() {
        return Seniorpass;
    }

    public int Weekendpass() {
        return Weekendpass;
    }

    public int Weeklypass() {
        return Weeklypass;
    }

    // ---------------------------------------------------------
    // Mutators
    // ---------------------------------------------------------
    public void setRegularpass(int Regularpass) {
        this.Regularpass = Regularpass;
    }

    public void setStudentpass(int Studentpass) {
        this.Studentpass = Studentpass;
    }

    public void setSeniorpass(int Seniorpass) {
        this.Seniorpass = Seniorpass;
    }

    public void setWeekendpass(int Weekendpass) {
        this.Weekendpass = Weekendpass;
    }

    public void setWeeklypass(int Weeklypass) {
        this.Weeklypass = Weeklypass;
    }

    // ---------------------------------------------------------
    // Adds the given numbers of passes to the existing counts
    // (arguments in order: Regular, Student, Senior, Weekend, Weekly)
    // ---------------------------------------------------------
    public void addGymPasses(int a, int b, int c, int d, int e) {
        this.Regularpass += a;
        this.Studentpass += b;
        this.Seniorpass  += c;
        this.Weekendpass += d;
        this.Weeklypass  += e;
    }

    // ---------------------------------------------------------
    // Total dollar value of all passes (count x price for each type)
    // ---------------------------------------------------------
    public int GymPassesTotal() {
        int total = 0;
        total += Regularpass_price * Regularpass;
        total += Studentpass_price * Studentpass;
        total += Seniorpass_price  * Seniorpass;
        total += Weekendpass_price * Weekendpass;
        total += Weeklypass_price  * Weeklypass;
        return total;
    }

    // ---------------------------------------------------------
    // Display: count of each pass type
    // ---------------------------------------------------------
    public String toString() {
        return "This is the count of each gym pass in the gym with "
            + Regularpass + " of Regular passes "
            + Studentpass + " of Student passes "
            + Seniorpass  + " of Senior passes "
            + Weekendpass + " of Weekend passes "
            + Weeklypass  + " of Weekly passes.";
    }

    // ---------------------------------------------------------
    // Comparison: equal when every pass type has the same count
    // ---------------------------------------------------------
    public boolean equals(GymPasses other) {
        if (other == null) {
            return false;
        }
        return this.Regularpass == other.Regularpass &&
               this.Studentpass == other.Studentpass &&
               this.Seniorpass  == other.Seniorpass &&
               this.Weekendpass == other.Weekendpass &&
               this.Weeklypass  == other.Weeklypass;
    }
}
