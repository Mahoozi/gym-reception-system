/*
 * Gym Reception System
 * Author: Yusuf Almahoozi
 *
 * Reception represents a gym reception desk. It holds a GymPasses object
 * (how many passes of each type have been sold) and an array of GymCard
 * objects (the membership cards registered at this desk).
 *
 * The class provides:
 *   - constructors that deep-copy the passes and cards they are given
 *   - comparisons with another reception by total pass value or pass counts
 *   - methods to add, remove and update membership cards
 *   - a method to add passes and return the new total value
 *   - toString and gymPassesBreakdown for displaying the reception's contents
 *
 * The cards array is null when the reception has no membership cards.
 */
public class Reception {

    // ---------------------------------------------------------
    // Fields
    // ---------------------------------------------------------
    private GymPasses passes; // Pass counts for this reception
    private GymCard[] cards;  // Membership cards at this reception (null when there are none)

    // ---------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------

    /** Creates a reception with no passes and no membership cards. */
    public Reception() {
        this.passes = new GymPasses();
        this.cards = null;
    }

    /** Creates a reception from deep copies of the given passes and cards. */
    public Reception(GymPasses passes, GymCard[] cards) {

        // Deep copy the GymPasses object
        this.passes = new GymPasses(passes);

        // Deep copy the cards; an empty or missing array means no cards
        if (cards == null || cards.length == 0) {
            this.cards = null;
        } else {
            this.cards = new GymCard[cards.length];
            for (int i = 0; i < cards.length; i++) {
                this.cards[i] = new GymCard(cards[i]);
            }
        }
    }

    // ---------------------------------------------------------
    // True if both receptions have the same total dollar value of passes
    // ---------------------------------------------------------
    public boolean Value_GymPass_comparison(Reception other) {
        if (other == null) return false;

        return this.passes.GymPassesTotal() == other.passes.GymPassesTotal();
    }

    // ---------------------------------------------------------
    // True if both receptions have the same number of passes of each type
    // ---------------------------------------------------------
    public boolean Similarity_NumberOfGymPasses(Reception other) {
        if (other == null) return false;
        return this.passes.equals(other.passes);
    }

    // ---------------------------------------------------------
    // Total dollar value of all passes at this reception
    // ---------------------------------------------------------
    public int getGymPassesTotalRevenue() {
        return this.passes.GymPassesTotal();
    }

    // ---------------------------------------------------------
    // Number of membership cards at this reception
    // ---------------------------------------------------------
    public int getGymCradsTotalAmount() {
        if (this.cards == null)
            return 0;
        return this.cards.length;
    }

    // ---------------------------------------------------------
    // Adds a copy of the given card and returns the new number of cards
    // ---------------------------------------------------------
    public int AddGymCard(GymCard newCard) {
        if (newCard == null) {
            return getGymCradsTotalAmount();
        }

        // Case 1: no cards yet, so create an array of size 1
        if (this.cards == null) {
            this.cards = new GymCard[1];
            this.cards[0] = new GymCard(newCard);
            return 1;
        }

        // Case 2: the reception already has cards, so grow the array by one
        GymCard[] temp = new GymCard[this.cards.length + 1];

        // Deep copy the existing cards into the new array
        for (int i = 0; i < this.cards.length; i++) {
            temp[i] = new GymCard(this.cards[i]);
        }

        // Add the new card at the end
        temp[temp.length - 1] = new GymCard(newCard);

        // Replace the old array with the larger one
        this.cards = temp;

        return this.cards.length;
    }

    // ---------------------------------------------------------
    // Removes the first card equal to target; returns true if a card was removed
    // ---------------------------------------------------------
    public boolean RemoveGymCard(GymCard target) {

        // Nothing to remove
        if (this.cards == null || target == null)
            return false;

        // Find the index of the matching card (uses GymCard.equals)
        int index = -1;
        for (int i = 0; i < this.cards.length; i++) {
            if (this.cards[i].equals(target)) {
                index = i;
                break;
            }
        }

        // Card not found
        if (index == -1)
            return false;

        // Removing the only card leaves the reception with no cards
        if (this.cards.length == 1) {
            this.cards = null;
            return true;
        }

        // Copy every card except the removed one into a smaller array
        GymCard[] temp = new GymCard[this.cards.length - 1];
        int tempIndex = 0;
        for (int i = 0; i < this.cards.length; i++) {
            if (i != index) {
                temp[tempIndex] = new GymCard(this.cards[i]);
                tempIndex++;
            }
        }

        this.cards = temp;
        return true;
    }

    // ---------------------------------------------------------
    // Updates the expiry day and month of the card at the given index
    // (does nothing if the index is out of range)
    // ---------------------------------------------------------
    public void updateGymCardExpiry(int index, int newDay, int newMonth) {

        if (this.cards == null || index < 0 || index >= this.cards.length) {
            return;
        }

        this.cards[index].setDay(newDay);
        this.cards[index].setmonth(newMonth);
    }

    // ---------------------------------------------------------
    // Adds passes on top of the existing counts and returns the new total value
    // ---------------------------------------------------------
    public int addGymPasses(int regular, int student, int senior, int weekend, int weekly) {
        this.passes.addGymPasses(regular, student, senior, weekend, weekly);
        return this.passes.GymPassesTotal();
    }

    // ---------------------------------------------------------
    // Two receptions are equal when they have the same total dollar value
    // of passes and the same number of membership cards
    // ---------------------------------------------------------
    public boolean equals(Reception other) {
        if (other == null)
            return false;

        return this.passes.GymPassesTotal() == other.passes.GymPassesTotal()
            && this.getGymCradsTotalAmount() == other.getGymCradsTotalAmount();
    }

    // ---------------------------------------------------------
    // Display: pass breakdown on the first line, then one line per card
    // ---------------------------------------------------------
    public String toString() {
        String result = gymPassesBreakdown() + "\n";

        if (cards == null || cards.length == 0) {
            result += "No membership card\n";
        } else {
            for (int i = 0; i < cards.length; i++) {
                result += cards[i].toString() + "\n";
            }
        }

        return result;
    }

    // ---------------------------------------------------------
    // Pass breakdown as "count x $price" for each type,
    // e.g. 10 x $7 + 4 x $5 + 1 x $4 + 1 x $12 + 1 x $42
    // ---------------------------------------------------------
    public String gymPassesBreakdown() {
        int r  = passes.Regularpass();
        int st = passes.Studentpass();
        int sr = passes.Seniorpass();
        int we = passes.Weekendpass();
        int ww = passes.Weeklypass();

        return r  + " x $7 + "
             + st + " x $5 + "
             + sr + " x $4 + "
             + we + " x $12 + "
             + ww + " x $42";
    }
}
