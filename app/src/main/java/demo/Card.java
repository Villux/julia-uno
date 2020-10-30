package demo;
/**
 * <p>A Card in an Uno deck. Each Card knows its particular type, which is
 * comprised of a 3-tuple (color, rank, number). Not all of these values
 * are relevant for every particular type of card, however; for instance,
 * wild cards have no color (getColor() will return Color.NONE) or number
 * (getNumber() will return -1).</p>
 * <p>A Card knows its forfeit cost (<i>i.e.</i>, how many points it counts
 * against a loser who gets stuck with it) and how it should act during
 * game play (whether it permits the player to change the color, what
 * effect it has on the game state, etc.)</p>
 * @since 1.0
 */
public class Card {
     private Color color;
     private Rank rank;
     private int number;

     /**
      * Constructor for non-number cards (skips, wilds, etc.)
      */
     public Card(Color color, Rank rank) {
          this.color = color;
          this.rank = rank;
          this.number = -1;

     }

     /**
      * Constructor for number cards.
      */
     public Card(Color color, int number) {
          this.color = color;
          this.number = number;

     }

     /**
      * Constructor to explicitly set entire card state.
      */
     public Card(Color color, Rank rank, int number) {
          this.color = color;
          this.rank = rank;
          this.number = number;

     }

     /**
      * Returns the number of points this card will count against a player
      * who holds it in his/her hand when another player goes out.
      */
     public int forfeitCost() {
          if (this.rank != null) {
               switch (this.rank){
                    case SKIP:
                    case REVERSE:
                    case DRAW_TWO:
                         return 20;
                    case WILD:
                    case WILD_D4:
                         return 50;
                    case CUSTOM:
                         return 50;
               }
          }
          return this.number;
     }

     /**
      * Returns true only if this Card can legally be played on the up card
      * passed as an argument. The second argument is relevant only if the
      * up card is a wild.
      * @param upCard An "up card" upon which the current object might (or might
      * not) be a legal play.
      * @param calledColor If the up card is a wild card, this parameter
      * contains the color the player of that color called.
      */
     public boolean canPlayOn(Card upCard, Color calledColor) {
          if (this.color == upCard.color) {
               return true;
          }
          if (this.color == calledColor && this.color == upCard.color) {
               return true;
          }
          if (color == calledColor && upCard.rank == rank.WILD_D4) {
               return true;
          }
          if (color == calledColor && upCard.rank == rank.WILD) {
               return true;
          }
          if (rank == rank.WILD || rank == rank.WILD_D4) {
               return true;
          }
          if (number == upCard.number && rank == upCard.rank.NUMBER) {
               return true;
          }
          if (rank != upCard.rank.NUMBER && rank == upCard.rank) {
               return true;
          }
          return false;
     }

     /**
      * Returns true only if playing this Card object would result in the
      * player being asked for a color to call. (In the standard game, this
      * is true only for wild cards.)
      */
     public boolean isWildCard() {
          if (this.rank == rank.WILD || this.rank == rank.WILD_D4) {
               return true;
          }
          else {
               return false;
          }
     }

     /**
      * Returns the color of this card, which is Color.NONE in the case of
      * wild cards.
      */
     public Color getColor() {
         return this.color;
     }

     /**
      * Returns the rank of this card, which is Rank.NUMBER in the case of
      * number cards (calling getNumber() will retrieve the specific
      * number.)
      */
     public Rank getRank() {
          if (this.rank != null) {
               return this.rank;
          }
          return rank.NUMBER;
     }

     /**
      * Returns the number of this card, which is guaranteed to be -1 for
      * non-number cards (cards of non-Rank.NUMBER rank.)
      */
     public int getNumber() {
          if (this.rank != null) {
               return -1;
          }
          return this.number;
     }

     /**
      * Returns this Card object as a string.
      */
     public String toString() {
         return String.valueOf(this.color) + String.valueOf(this.number);
     }
 }
