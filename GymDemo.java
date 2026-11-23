/*
 * Gym Reception System
 * Author: Yusuf Almahoozi
 *
 * GymDemo is the console entry point for the Gym Reception System. It creates
 * five Reception objects with sample gym passes and membership cards, then
 * shows a menu that lets the user:
 *   - view the contents of all receptions or a single reception
 *   - compare receptions by total pass value, pass counts, or
 *     pass value together with the number of membership cards
 *   - add or remove membership cards
 *   - update a card's expiry date
 *   - add gym passes to a reception
 * The menu repeats until the user enters 0 to quit.
 *
 * All data is held in memory and is lost when the program exits.
 */
import java.util.Scanner;

public class GymDemo {

    public static void main(String[] args) {

        Scanner kb = new Scanner(System.in);

        // -------------------------------------------------
        // 1) WELCOME BANNER
        // -------------------------------------------------
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("| Welcome to Gym Reception System                                                         |");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println();

        // -------------------------------------------------
        // 2) CREATE THE 5 SAMPLE RECEPTIONS
        // -------------------------------------------------
        Reception[] receptions = new Reception[5];

        // Reception stores deep copies of its cards and only offers
        // RemoveGymCard(GymCard), which removes by value. To let the user pick a
        // card by index, the demo keeps its own parallel copy of each reception's
        // cards (cardsData) and keeps it in sync on every add, remove and update.
        GymCard[][] cardsData = new GymCard[5][];

        // Reception #0
        GymPasses p0 = new GymPasses(10, 4, 1, 1, 1);
        GymCard[] c0 = {
                new GymCard("Basic", "Scrump", 25, 12),
                new GymCard("Standard", "Pelekai", 3, 12)
        };
        receptions[0] = new Reception(p0, c0);
        cardsData[0] = c0;

        // Reception #1
        GymPasses p1 = new GymPasses(10, 4, 1, 1, 1);
        GymCard[] c1 = {
                new GymCard("Premium", "Scrump", 7, 12),
                new GymCard("Basic", "Jookiba", 24, 8)
        };
        receptions[1] = new Reception(p1, c1);
        cardsData[1] = c1;

        // Reception #2
        GymPasses p2 = new GymPasses(5, 13, 0, 4, 0);
        GymCard[] c2 = {
                new GymCard("PremiumPlus", "Pleakley", 1, 6),
                new GymCard("Basic", "Gantu", 18, 12),
                new GymCard("Standard", "Stitch", 5, 4)
        };
        receptions[2] = new Reception(p2, c2);
        cardsData[2] = c2;

        // Reception #3 (no membership cards)
        GymPasses p3 = new GymPasses(2, 8, 5, 0, 3);
        receptions[3] = new Reception(p3, null);
        cardsData[3] = null;

        // Reception #4 (no membership cards)
        GymPasses p4 = new GymPasses(2, 8, 5, 0, 3);
        receptions[4] = new Reception(p4, null);
        cardsData[4] = null;

        // -------------------------------------------------
        // 3) MAIN MENU LOOP
        // -------------------------------------------------
        boolean quit = false;

        while (!quit) {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("| What would you like to do?                                                              |");
            System.out.println("| 1 >> See the content of all Receptions                                                  |");
            System.out.println("| 2 >> See the content of one Reception                                                   |");
            System.out.println("| 3 >> List Receptions with same $ amount of gym passes                                   |");
            System.out.println("| 4 >> List Receptions with same number of gym passes types                               |");
            System.out.println("| 5 >> List Receptions with same $ amount of gym passes and same number of memberships    |");
            System.out.println("| 6 >> Add a membership card to an existing Reception                                     |");
            System.out.println("| 7 >> Remove an existing membership card from a Reception                                |");
            System.out.println("| 8 >> Update the expiry date of an existing membership card                              |");
            System.out.println("| 9 >> Add gym passes to a Reception                                                      |");
            System.out.println("| 0 >> To quit                                                                            |");
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println();
            System.out.print("Please enter your choice and press <Enter>:");

            int choice = readInt(kb);

            switch (choice) {
                case 1:
                    // -----------------------------------------
                    // OPTION 1: SEE CONTENT OF ALL RECEPTIONS
                    // -----------------------------------------
                    System.out.println("Content of each Reception:");
                    System.out.println("----------------------------------");
                    for (int i = 0; i < receptions.length; i++) {
                        System.out.println("Reception #" + i + ":");
                        System.out.println(receptions[i].toString());
                    }
                    break;

                case 2:
                    // -----------------------------------------
                    // OPTION 2: SEE CONTENT OF ONE RECEPTION
                    // -----------------------------------------
                    System.out.print("Which Reception do you want to see the content of? (Enter number 0 to 4): ");
                    int rIndex = readInt(kb);
                    while (rIndex < 0 || rIndex >= receptions.length) {
                        System.out.println("Sorry but there is no Reception number " + rIndex);
                        System.out.print("--> Try again: (Enter number 0 to 4): ");
                        rIndex = readInt(kb);
                    }
                    System.out.println(receptions[rIndex].toString());
                    break;

                case 3:
                    // -----------------------------------------
                    // OPTION 3: SAME TOTAL $ VALUE OF PASSES
                    // -----------------------------------------
                    System.out.println("List of Receptions with same total $ GymPasses:");
                    for (int i = 0; i < receptions.length; i++) {
                        for (int j = i + 1; j < receptions.length; j++) {
                            if (receptions[i].Value_GymPass_comparison(receptions[j])) {
                                System.out.println("Receptions " + i + " and " + j +
                                        " both have " + receptions[i].getGymPassesTotalRevenue());
                            }
                        }
                    }
                    break;

                case 4:
                    // -----------------------------------------
                    // OPTION 4: SAME NUMBER OF EACH PASS TYPE
                    // -----------------------------------------
                    System.out.println("List of Receptions with same passes types:");
                    for (int i = 0; i < receptions.length; i++) {
                        for (int j = i + 1; j < receptions.length; j++) {
                            if (receptions[i].Similarity_NumberOfGymPasses(receptions[j])) {
                                System.out.println("Receptions " + i + " and " + j +
                                        " both have " + receptions[i].gymPassesBreakdown());
                            }
                        }
                    }
                    break;

                case 5:
                    // -----------------------------------------
                    // OPTION 5: SAME $ VALUE + SAME NUMBER OF CARDS
                    // -----------------------------------------
                    System.out.println("List of Receptions with same $ amount of passes and same number of memberships :");
                    System.out.println("");
                    for (int i = 0; i < receptions.length; i++) {
                        for (int j = i + 1; j < receptions.length; j++) {
                            if (receptions[i].equals(receptions[j])) {
                                System.out.println("Receptions " + i + " and " + j);
                            }
                        }
                    }
                    break;

                case 6:
                    // -----------------------------------------
                    // OPTION 6: ADD MEMBERSHIP CARD
                    // -----------------------------------------
                    System.out.print("Which Reception do you want to add a membership to? (Enter number 0 to 4): ");
                    int addIndex = readInt(kb);
                    while (addIndex < 0 || addIndex >= receptions.length) {
                        System.out.println("Sorry but there is no Reception number " + addIndex);
                        System.out.print("--> Try again: (Enter number 0 to 4): ");
                        addIndex = readInt(kb);
                    }

                    // The type and name are read as whole lines so they may contain
                    // spaces (e.g. "John Smith"). First drop the rest of the line
                    // that held the reception number.
                    skipRestOfLine(kb);

                    System.out.println("Please enter the following information so that we may complete the membership-");
                    System.out.print("--> Type of membership (Basic, Standard, Premium, PremiumPlus): ");
                    String type = readLine(kb);
                    System.out.print("--> name of the membership card holder: ");
                    String name = readLine(kb);
                    System.out.print("--> Expiry day number and month (separate by a space): ");
                    int day = readInt(kb);
                    int month = readInt(kb);

                    GymCard newCard = new GymCard(type, name, day, month);

                    int newCount = receptions[addIndex].AddGymCard(newCard);

                    // Keep the local copy (cardsData) in sync
                    if (cardsData[addIndex] == null) {
                        cardsData[addIndex] = new GymCard[1];
                        cardsData[addIndex][0] = newCard;
                    } else {
                        GymCard[] temp = new GymCard[cardsData[addIndex].length + 1];
                        for (int i = 0; i < cardsData[addIndex].length; i++) {
                            temp[i] = cardsData[addIndex][i];
                        }
                        temp[temp.length - 1] = newCard;
                        cardsData[addIndex] = temp;
                    }

                    System.out.println("You now have " + newCount + " GymCard");
                    break;

                case 7:
                    // -----------------------------------------
                    // OPTION 7: REMOVE MEMBERSHIP CARD
                    // -----------------------------------------
                    System.out.print("Which Reception do you want to remove a membership card from? (Enter number 0 to 4): ");
                    int remIndex = readInt(kb);
                    while (remIndex < 0 || remIndex >= receptions.length) {
                        System.out.println("Sorry but there is no Reception number " + remIndex);
                        System.out.print("--> Try again: (Enter number 0 to 4): ");
                        remIndex = readInt(kb);
                    }

                    int nbrCards = receptions[remIndex].getGymCradsTotalAmount();
                    if (nbrCards == 0) {
                        System.out.println("Sorry that Reception has no membership card");
                    } else {
                        System.out.print("Which membership card do you want to remove? (Enter number 0 to " + (nbrCards - 1) + "): ");
                        int cardIndex = readInt(kb);
                        while (cardIndex < 0 || cardIndex >= nbrCards) {
                            System.out.println("Sorry but there is no membership number " + cardIndex);
                            System.out.print("--> Try again: ");
                            System.out.print("\n(Enter number 0 to " + (nbrCards - 1) + "): ");
                            cardIndex = readInt(kb);
                        }

                        // Look up the selected card in the local copy, then ask Reception to remove it
                        GymCard target = cardsData[remIndex][cardIndex];
                        boolean removed = receptions[remIndex].RemoveGymCard(target);

                        if (removed) {
                            // Keep the local copy (cardsData) in sync. RemoveGymCard removes the
                            // FIRST card equal to target, which differs from cardIndex when the
                            // reception holds duplicate cards, so remove that same position here.
                            int removedAt = 0;
                            while (!cardsData[remIndex][removedAt].equals(target)) {
                                removedAt++;
                            }

                            if (nbrCards == 1) {
                                cardsData[remIndex] = null;
                            } else {
                                GymCard[] temp = new GymCard[nbrCards - 1];
                                int ti = 0;
                                for (int i = 0; i < nbrCards; i++) {
                                    if (i != removedAt) {
                                        temp[ti] = cardsData[remIndex][i];
                                        ti++;
                                    }
                                }
                                cardsData[remIndex] = temp;
                            }
                            System.out.println("membership card was removed successfully");
                        }
                    }
                    break;

                case 8:
                    // -----------------------------------------
                    // OPTION 8: UPDATE EXPIRY DATE OF A CARD
                    // -----------------------------------------
                    System.out.print("Which Reception do you want to update a membership card from? (Enter number 0 to 4): ");
                    int upIndex = readInt(kb);
                    while (upIndex < 0 || upIndex >= receptions.length) {
                        System.out.println("Sorry but there is no Reception number " + upIndex);
                        System.out.print("--> Try again: (Enter number 0 to 4): ");
                        upIndex = readInt(kb);
                    }

                    int upCards = receptions[upIndex].getGymCradsTotalAmount();
                    if (upCards == 0) {
                        System.out.println("Sorry that Reception has no membership card");
                    } else {
                        System.out.println("Which membership card do you want to update? (Enter number 0 to " + (upCards - 1) + "): ");
                        int which = readInt(kb);
                        while (which < 0 || which >= upCards) {
                            System.out.println("Sorry but there is no membership number " + which);
                            System.out.print("--> Try again: ");
                            System.out.println("\n(Enter number 0 to " + (upCards - 1) + "): ");
                            which = readInt(kb);
                        }

                        System.out.print("--> Enter new expiry date day number and month (separate by a space): ");
                        int newDay = readInt(kb);
                        int newMonth = readInt(kb);

                        receptions[upIndex].updateGymCardExpiry(which, newDay, newMonth);

                        // Keep the local copy in sync so a later removal still finds this card
                        if (cardsData[upIndex] != null) {
                            cardsData[upIndex][which].setDay(newDay);
                            cardsData[upIndex][which].setmonth(newMonth);
                        }

                        System.out.println("Expiry Date updated.");
                    }
                    break;

                case 9:
                    // -----------------------------------------
                    // OPTION 9: ADD GYM PASSES
                    // -----------------------------------------
                    System.out.print("Which Reception do you want to add passes to? (Enter number 0 to 4): ");
                    int passIndex = readInt(kb);
                    while (passIndex < 0 || passIndex >= receptions.length) {
                        System.out.println("Sorry but there is no Reception number " + passIndex);
                        System.out.print("--> Try again: (Enter number 0 to 4): ");
                        passIndex = readInt(kb);
                    }

                    System.out.println("How many Regular Pass($7), Student Pass($5), Senior Pass($4), Weekend Pass($12) and Weekly ");
                    System.out.print("Pass($42) gym passes do you want to add?\n");
                    System.out.print("Enter 5 numbers separated by a space: ");
                    int reg = readInt(kb);
                    int stu = readInt(kb);
                    int sen = readInt(kb);
                    int weekend = readInt(kb);
                    int weekly = readInt(kb);

                    int total = receptions[passIndex].addGymPasses(reg, stu, sen, weekend, weekly);
                    System.out.println("You now have $" + total);
                    break;

                case 0:
                    // -----------------------------------------
                    // OPTION 0: QUIT
                    // -----------------------------------------
                    System.out.println("Thank you for using Gym Reception System!");
                    quit = true;
                    break;

                default:
                    // -----------------------------------------
                    // INVALID MENU CHOICE
                    // -----------------------------------------
                    System.out.println("Sorry that is not a valid choice. Try again.");
                    break;
            }
        }

        kb.close();
    }

    // -------------------------------------------------
    // INPUT HELPERS
    // -------------------------------------------------

    /**
     * Reads the next whole number. A token that is not a whole number is
     * discarded and the user is asked again, so typing letters never crashes
     * the program. If input ends (for example Ctrl+D), the program exits.
     */
    private static int readInt(Scanner kb) {
        while (true) {
            if (!kb.hasNext()) {
                exitOnClosedInput();
            }
            if (kb.hasNextInt()) {
                return kb.nextInt();
            }
            String bad = kb.next();
            System.out.print("'" + bad + "' is not a whole number. Please enter a number: ");
        }
    }

    /**
     * Reads a whole line of text, trimmed, so values may contain spaces.
     * Blank lines are rejected and the user is asked again. If input ends,
     * the program exits.
     */
    private static String readLine(Scanner kb) {
        while (true) {
            if (!kb.hasNextLine()) {
                exitOnClosedInput();
            }
            String line = kb.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.print("This cannot be empty. Please try again: ");
        }
    }

    /** Discards whatever is left on the current input line (usually just the newline). */
    private static void skipRestOfLine(Scanner kb) {
        if (kb.hasNextLine()) {
            kb.nextLine();
        }
    }

    /** Ends the program cleanly when there is no more input to read. */
    private static void exitOnClosedInput() {
        System.out.println();
        System.out.println("Input closed. Thank you for using Gym Reception System!");
        System.exit(0);
    }
}
