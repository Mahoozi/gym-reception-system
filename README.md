# Gym Reception System

A menu-driven Java console application for managing gym reception desks. Each
reception tracks the gym passes it has sold and the membership cards registered
at it. From the menu you can inspect receptions, compare them, and add, remove
or update their data.

## Features

- **View receptions:** list the contents of all five receptions, or of a
  single one: a pass breakdown (`count x $price` per pass type) and every
  membership card with its type, holder name and expiry date (`DD/MM`).
- **Compare receptions:**
  - by the total dollar value of their passes
  - by the number of passes of each type
  - by total pass value *and* number of membership cards
- **Manage membership cards:** add a card, remove a card by its position, and
  update a card's expiry day and month. The membership type and holder name
  are read as whole lines, so names with spaces such as `John Smith` work.
  Expiry dates are checked against the length of each month, so a date that
  does not exist (such as `31 2` or `31 4`) is rejected and asked for again.
  `29 2` is accepted because cards store no year.
- **Sell passes:** add passes of each type to a reception and see its new
  total value. Negative quantities are rejected and all five numbers are
  asked for again.
- **Input checks:** reception and card numbers are re-prompted until they are
  in range, and unknown menu choices are rejected. Anything that is not a
  whole number where a number is expected is rejected and asked for again.
  Closing input (Ctrl+D) ends the program cleanly.

### Pass types and prices

| Pass    | Price |
|---------|-------|
| Regular | $7    |
| Student | $5    |
| Senior  | $4    |
| Weekend | $12   |
| Weekly  | $42   |

The program starts with five sample receptions (numbered 0–4) so every menu
option can be tried straight away.

## Requirements

- A Java Development Kit (JDK), version 8 or newer. No external libraries
  or build tools are needed.
- Tested with JDK 24.0.2. The sources also compile with `--release 8`.

Check your installation with:

```bash
java -version
javac -version
```

## Build and run

From the project root:

```bash
javac -d out *.java
java -cp out GymDemo
```

The first command compiles all sources into `out/`, which is excluded from
version control. The second starts the interactive menu.

## Project structure

```
gym-reception-system/
├── GymDemo.java     # Entry point: sample data and the interactive menu
├── Reception.java   # A reception desk: its passes and membership cards
├── GymPasses.java   # Pass counts per type, prices and total value
├── GymCard.java     # A single membership card
├── README.md
└── .gitignore
```

### Class responsibilities

| Class       | Responsibility |
|-------------|----------------|
| `GymDemo`   | Builds five sample receptions, displays the menu, reads user input with `Scanner`, and calls into `Reception`. It keeps its own copy of each reception's cards so the user can pick a card by number. |
| `Reception` | Holds one `GymPasses` object and an array of `GymCard` objects, deep-copying both. It compares receptions (by total value, by pass counts, and by total value plus card count), adds, removes and updates cards, adds passes, and formats its contents for display. |
| `GymPasses` | Stores the count of Regular, Student, Senior, Weekend and Weekly passes, holds the price of each type, adds passes (rejecting negative quantities with an `IllegalArgumentException`), computes the total dollar value, and compares pass counts. |
| `GymCard`   | Stores a membership type, holder name and expiry day/month. It validates the day (1–31) and month (1–12), stores invalid values as `0`, provides `isValidExpiry(day, month)` to check a day against its month's length, and formats the card as `Type - Name - DD/MM.` |

## Example usage

Viewing all receptions (menu option 1) with the starting sample data:

```
Please enter your choice and press <Enter>:1
Content of each Reception:
----------------------------------
Reception #0:
10 x $7 + 4 x $5 + 1 x $4 + 1 x $12 + 1 x $42
Basic - Scrump - 25/12.
Standard - Pelekai - 03/12.

Reception #1:
10 x $7 + 4 x $5 + 1 x $4 + 1 x $12 + 1 x $42
Premium - Scrump - 07/12.
Basic - Jookiba - 24/08.

Reception #2:
5 x $7 + 13 x $5 + 0 x $4 + 4 x $12 + 0 x $42
PremiumPlus - Pleakley - 01/06.
Basic - Gantu - 18/12.
Standard - Stitch - 05/04.

Reception #3:
2 x $7 + 8 x $5 + 5 x $4 + 0 x $12 + 3 x $42
No membership card

Reception #4:
2 x $7 + 8 x $5 + 5 x $4 + 0 x $12 + 3 x $42
No membership card
```

Comparing receptions by total pass value (option 3):

```
Please enter your choice and press <Enter>:3
List of Receptions with same total $ GymPasses:
Receptions 0 and 1 both have 148
Receptions 0 and 2 both have 148
Receptions 1 and 2 both have 148
Receptions 3 and 4 both have 200
```

Adding a membership card to reception 4 (option 6), then adding one Regular pass
to reception 3 (option 9):

```
Please enter your choice and press <Enter>:6
Which Reception do you want to add a membership to? (Enter number 0 to 4): 4
Please enter the following information so that we may complete the membership-
--> Type of membership (Basic, Standard, Premium, PremiumPlus): Premium
--> name of the membership card holder: Pokemon
--> Expiry day number and month (separate by a space): 3 8
You now have 1 GymCard

Please enter your choice and press <Enter>:9
Which Reception do you want to add passes to? (Enter number 0 to 4): 3
How many Regular Pass($7), Student Pass($5), Senior Pass($4), Weekend Pass($12) and Weekly
Pass($42) gym passes do you want to add?
Enter 5 numbers separated by a space: 1 0 0 0 0
You now have $207
```

Enter `0` at the menu to quit.

## Current limitations

- **In-memory storage only.** All receptions, passes and cards live in memory.
  Every run starts from the same five sample receptions, and changes are lost
  when the program exits. There is no file or database persistence.
- **Fixed set of receptions.** There are always five receptions (0–4). They
  cannot be created or deleted from the menu.
- **No expiry year.** Cards store only a day and month, so there is no way to
  tell whether a card has already expired, and `29/02` is always allowed.
  The menu rejects impossible dates, but `GymCard`'s constructor and setters
  used directly still only check the 1–31 / 1–12 ranges and store
  out-of-range values as `0`.
- **Duplicate cards.** When a reception has two identical cards (same type,
  name and expiry date), removing one removes the first matching card, so
  the remaining cards may be listed in a different order.
- **Fixed prices.** Pass prices are set in code.
- **No automated tests.** Behaviour has been checked by running each menu
  flow through the console, not by a unit test suite.

## Author

Yusuf Almahoozi
