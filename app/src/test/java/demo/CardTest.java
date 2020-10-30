package demo;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;



public class CardTest {

   /** Test the constructors and accessors against various kinds of cards **/
   @Test
   public void testAccessors() {
      Card blue8 = new Card(Color.BLUE, 8);
      assertEquals("Blue 8: color does not match", Color.BLUE, blue8.getColor());
      assertEquals("Blue 8: number does not match", 8, blue8.getNumber());
      assertEquals("Blue 8: rank does not match", Rank.NUMBER, blue8.getRank());

      Card yellowSkip = new Card(Color.YELLOW, Rank.SKIP);
      assertEquals("Yellow skip: color does not match", Color.YELLOW, yellowSkip.getColor());
      assertEquals("Yellow skip: number does not match", -1, yellowSkip.getNumber());
      assertEquals("Yellow skip: rank does not match", Rank.SKIP, yellowSkip.getRank());

      Card wildDraw4 = new Card(Color.NONE, Rank.WILD_D4, -1);
      assertEquals("Wild draw 4: color does not match", Color.NONE, wildDraw4.getColor());
      assertEquals("Wild draw 4: number does not match", -1, wildDraw4.getNumber());
      assertEquals("Wild draw 4: rank does not match", Rank.WILD_D4, wildDraw4.getRank());
   }

   /** Test the forfeitCost method for different kinds of cards */
   @Test
   public void testForfeitCost() {
      Card red6 = new Card(Color.RED, 6);
      Card yellow0 = new Card(Color.YELLOW, 0);
      Card greenSkip = new Card(Color.GREEN, Rank.SKIP);
      Card blueReverse = new Card(Color.BLUE, Rank.REVERSE);
      Card redDraw2 = new Card(Color.RED, Rank.DRAW_TWO);
      Card wild = new Card(Color.NONE, Rank.WILD);
      Card wildDraw4 = new Card(Color.NONE, Rank.WILD_D4, -1);
      Card custom = new Card(Color.NONE, Rank.CUSTOM, -1);

      assertEquals("Red 6: forfeit cost does not match", 6, red6.forfeitCost());
      assertEquals("Yellow 0: forfeit cost does not match", 0, yellow0.forfeitCost());
      assertEquals("Green skip: forfeit cost not match", 20, greenSkip.forfeitCost());
      assertEquals("Blue reverse: forfeit cost does not match", 20, blueReverse.forfeitCost());
      assertEquals("Red draw 2: forfeit cost does not match", 20, redDraw2.forfeitCost());
      assertEquals("Wild: forfeit cost does not match", 50, wild.forfeitCost());
      assertEquals("Wild draw 4: forfeit cost does not match", 50, wildDraw4.forfeitCost());
      assertEquals("Custom card: forfeit cost does not match", 50, custom.forfeitCost());
   }

   /** Tests the canPlayOn method for many combinations of cards */
   @Test
   public void testCanPlayOn() {
      Card[] cards = {
         new Card(Color.BLUE, 6),           // 0: blue 6
         new Card(Color.RED, 6),            // 1: red 6
         new Card(Color.BLUE, 2),           // 2: blue 2
         new Card(Color.BLUE, Rank.SKIP),   // 3: blue skip
         new Card(Color.RED, Rank.SKIP),    // 4: red skip
         new Card(Color.RED, Rank.REVERSE), // 5: red reverse
         new Card(Color.NONE, Rank.WILD)    // 6: wild
      };

      final boolean t = true, f = false;

      // For each combination of cards, two booleans: one when blue
      // is the called color and one when red is
      boolean[][] answers = {
         // blue 6 on...
         { t, t,   // blue 6
           t, t,   // red 6
           t, t,   // blue 2
           t, t,   // blue skip
           f, f,   // red skip
           f, f,   // red reverse
           t, f }, // wild

         // red 6 on...
         { t, t,   // blue 6
           t, t,   // red 6
           f, f,   // blue 2
           f, f,   // blue skip
           t, t,   // red skip
           t, t,   // red reverse
           f, t }, // wild

         // blue 2 on...
         { t, t,   // blue 6
           f, f,   // red 6
           t, t,   // blue 2
           t, t,   // blue skip
           f, f,   // red skip
           f, f,   // red reverse
           t, f }, // wild

         // blue skip on...
         { t, t,   // blue 6
           f, f,   // red 6
           t, t,   // blue 2
           t, t,   // blue skip
           t, t,   // red skip
           f, f,   // red reverse
           t, f }, // wild

         // red skip on...
         { f, f,   // blue 6
           t, t,   // red 6
           f, f,   // blue 2
           t, t,   // blue skip
           t, t,   // red skip
           t, t,   // red reverse
           f, t }, // wild

         // red reverse on...
         { f, f,   // blue 6
           t, t,   // red 6
           f, f,   // blue 2
           f, f,   // blue skip
           t, t,   // red skip
           t, t,   // red reverse
           f, t }, // wild

         // wild on...
         { t, t,   // blue 6
           t, t,   // red 6
           t, t,   // blue 2
           t, t,   // blue skip
           t, t,   // red skip
           t, t,   // red reverse
           t, t } // wild
      };

      for (int i = 0; i < cards.length; ++i) {
         Card bottomCard = cards[i];
         for (int j = 0; j < cards.length; ++j) {
            Card topCard = cards[j];

            boolean canPlayBlueCalled = answers[j][2 * i],
                    canPlayRedCalled = answers[j][2 * i + 1];

            assertEquals("Can play " + topCard + " on " + bottomCard + " (called color: blue) incorrect",
               canPlayBlueCalled, topCard.canPlayOn(bottomCard, Color.BLUE));
            assertEquals("Can play " + topCard + " on " + bottomCard + " (called color: red) incorrect",
               canPlayRedCalled, topCard.canPlayOn(bottomCard, Color.RED));
         }
      }
   }

   /** Test the isWildCard method for some cards, wild and not */
   @Test
   public void testIsWildCard() {
      Card green4 = new Card(Color.GREEN, 4);
      Card yellowSkip = new Card(Color.YELLOW, Rank.SKIP);
      Card wild = new Card(Color.NONE, Rank.WILD);
      Card wildDraw4 = new Card(Color.NONE, Rank.WILD_D4, -1);

      assertEquals("Green 4 isWildcard incorrect", false, green4.isWildCard());
      assertEquals("Yellow skip isWildcard incorrect", false, yellowSkip.isWildCard());
      assertEquals("Wild isWildcard incorrect", true, wild.isWildCard());
      assertEquals("Wild draw 4 isWildcard incorrect", true, wildDraw4.isWildCard());
   }
}
