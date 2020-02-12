import java.util.Random;
import javax.swing.JFrame;
/**
 * Blackjack card game
 * @author
 *    Mukarram Faridi
 *    James MacCarthy
 *    Yuantai Pan
 *    Sanchit Sethi
 *    Preet Shah
 *    Stanley Thomas
 * @version 02/09/2020
 */
public class Blackjack extends JFrame {
	
   private Card c;
   private Card[] deck, dealer, player;
   private Card[][] draw;
   
   public Blackjack() {
      initComponents();
         deck = new Card[52];
         int k = 0;
         for (int i = 1; i < 14; ++i) {
            for (int j = 1; j < 5; ++j) {
               switch (j) {
                  case 1:
                     c = new Card(i, "Spades");
                     break;
                  case 2:
                     c = new Card(i, "Hearts");
                     break;
                  case 3:
                     c = new Card(i, "Clubs");
                     break;
                  case 4:
                     c = new Card(i, "Diamonds");
               }
            deck[k] = c;
            ++k;
         }
      }
      
      draw = new Card[52][8];
      for (int i = 0; i < 8; ++i) {
         for (int j = 0; j < 52; ++j) {
            draw[j][i] = deck[j];
         }
      }
      
      dealer = new Card[11];
      player = new Card[11];
   }
   
   // Sets up initial instance of two cards for both the dealer and the player
   private Card[] initialDeal(Card[] hand) {
       c = null;
      int drawIndex, deckIndex;
      Random random = new Random();
      
      drawIndex = random.nextInt(8);
      deckIndex = random.nextInt(52);
      
      while (c == null) {
         if (draw[deckIndex][drawIndex] != null) {
            c = draw[deckIndex][drawIndex];
            hand[0] = c;
            draw[deckIndex][drawIndex] = null;
         }
         drawIndex = random.nextInt(8);
         deckIndex = random.nextInt(52);
      }
      c = null;
      
      while (c == null) {
         if (draw[deckIndex][drawIndex] != null) {
            c = draw[deckIndex][drawIndex];
            hand[1] = c;
            draw[deckIndex][drawIndex] = null;
         }
         drawIndex = random.nextInt(8);
         deckIndex = random.nextInt(52);
      }
      c = null;
      
      return hand;
   
   }
   
   // Checks the score of Ace(s) in either the dealer's or player's hand
   public int checkAce(Card[] hand) {
      int total = 0;
      int aceCount = 0;
      int i = 0;
      while (hand[i] != null) {
         if (hand[i].getValue() > 9) {
            total += 10;
         } else
         if (hand[i].getValue() != 1) {
            total += hand[i].getValue();
         } else {
            ++aceCount;
         }
         ++i;
      }
      if ((aceCount > 0 && total + 10 + aceCount < 22) || total == 0) {
         return 10 + aceCount;
      } else {
         return aceCount;
      }
   }
   
   // Returns the total score of either the dealer's or player's hand
   public int stand(Card[] hand) {
      int total = 0;
      int i = 0;
      while (hand[i] != null) {
         if (hand[i].getValue() > 9) {
            total += 10;
         } else
         if (hand[i].getValue() != 1) {
            total += hand[i].getValue();
         }
         ++i;
      }
      return total + checkAce(hand);
   }
   
   // Adds one card to either the dealer's or player's hand and returns the total score of the hand after the hit
   private int hit(Card[] hand) {
       c = null;
      int drawIndex, deckIndex;
      int openIndex = 2;
      Random random = new Random();
      
      drawIndex = random.nextInt(8);
      deckIndex = random.nextInt(52);
      
      while (c == null) {
         if (draw[deckIndex][drawIndex] != null) {
            c = draw[deckIndex][drawIndex];
            for (int i = 0; i < hand.length; ++i) {
               if (hand[i] == null) {
                  openIndex = i;
                  break;
               }
            }
            hand[openIndex] = c;
            draw[deckIndex][drawIndex] = null;
         }
         drawIndex = random.nextInt(8);
         deckIndex = random.nextInt(52);
      }
      
      return stand(hand);
   }
   
   // Checks whether or not a hand is a bust
   public boolean checkBust(Card[] hand) {
      if (stand(hand) > 21) {
         return true;
      }
      return false;
   }
   
   // Automation of the dealer's turn after the player either busts or stands
   private void dealerPlay(){
      if (checkBust(player)) {
         dealerhand.setText("");
         for (Card d: dealer) {
            if (d != null) {
               switch (d.getValue()){
                  case 1:
                     dealerhand.append("A\n");
                     break;
                  case 11:
                     dealerhand.append("J\n");
                     break;
                  case 12:
                     dealerhand.append("Q\n");
                     break;
                  case 13:
                    dealerhand.append("K\n");
                    break;
                  default:
                    dealerhand.append(d.getValue() + "\n");
               }
            }
         }
         result.append("You busted " + stand(player) + " to " + stand(dealer) + ". Press Start to play again");
      } else {
         if (stand(player) == 21 || stand(dealer) == 21) {
            result.append("Blackjack! ");
         }
         while (stand(dealer) < 17) {
            hit(dealer);
            dealerhand.setText("");
            for (Card d: dealer) {
               if (d != null) {
                  switch (d.getValue()){
                     case 1:
                        dealerhand.append("A\n");
                        break;
                     case 11:
                        dealerhand.append("J\n");
                        break;
                     case 12:
                        dealerhand.append("Q\n");
                        break;
                     case 13:
                        dealerhand.append("K\n");
                        break;
                     default:
                        dealerhand.append(d.getValue() + "\n");
                  }
               }
            }
         }
         if (checkBust(dealer)) {
            result.append("You won " + stand(player) + " to " + stand(dealer) + ". Press Start to play again");
         } else
         if (stand(player) < stand(dealer)) {
            result.append("You lost " + stand(player) + " to " + stand(dealer) + ". Press Start to play again");
         } else
         if (stand(player) == stand(dealer)) {
            result.append("You tied " + stand(player) + " to " + stand(dealer) + ". Press Start to play again");
         } else {
            result.append("You won " + stand(player) + " to " + stand(dealer) + ". Press Start to play again");
         }
      }
   }
   
   // The main method that instantiates and runs Blackjack
   @SuppressWarnings("unchecked")
   public static void main(String[] args) {
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            Blackjack b = new Blackjack();
            b.setTitle("Blackjack");
            b.setResizable(false);
            b.setVisible(true);
         }
      });
   }
    
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {
      hit = new javax.swing.JButton();
      stay = new javax.swing.JButton();
      result = new javax.swing.JTextArea();
      jButton1 = new javax.swing.JButton();
      jScrollPane1 = new javax.swing.JScrollPane();
      playerhand = new javax.swing.JTextArea();
      jScrollPane2 = new javax.swing.JScrollPane();
      dealerhand = new javax.swing.JTextArea();
      jLabel1 = new javax.swing.JLabel();
      jLabel2 = new javax.swing.JLabel();

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

      hit.setText("HIT");
      hit.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            hitActionPerformed(evt);
         }
      });

      stay.setText("STAY");
      stay.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            stayActionPerformed(evt);
         }
      });

      result.setEditable(false);

      jButton1.setText("START");
      jButton1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
         }
      });

      playerhand.setColumns(20);
      playerhand.setRows(5);
      jScrollPane1.setViewportView(playerhand);

      dealerhand.setColumns(20);
      dealerhand.setRows(5);
      jScrollPane2.setViewportView(dealerhand);

      jLabel1.setText("Player ");

      jLabel2.setText("Dealer");

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
               .addGap(14, 14, 14)
                  .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                           .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                 .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(jLabel1)
                                       .addGap(72, 72, 72)
                                          .addComponent(jButton1)
                                             .addGap(64, 64, 64)
                                                .addComponent(jLabel2)
                                                   .addGap(108, 108, 108))
                                                      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                         .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                  .addComponent(hit, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                     .addGap(121, 121, 121)
                                                                        .addComponent(stay, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                           .addGap(104, 104, 104))
                                                                              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                 .addComponent(result, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                    .addGap(72, 72, 72)))));
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
               .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(layout.createSequentialGroup()
                     .addGap(8, 8, 8)
                        .addComponent(jButton1)
                           .addGap(18, 18, 18))
                              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                 .addContainerGap()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                       .addComponent(jLabel2)
                                          .addComponent(jLabel1))
                                             .addGap(6, 6, 6)))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                   .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                      .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                         .addGap(33, 33, 33)
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                               .addComponent(hit)
                                                                  .addComponent(stay))
                                                                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(result, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                           .addContainerGap(61, Short.MAX_VALUE)));

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      for (int i = 0; i < player.length; ++i) {
         player[i] = null;
      }
      for (int i = 0; i < dealer.length; ++i) {
         dealer[i] = null;
      }
      playerhand.setText("");
      dealerhand.setText("");
      result.setText("");
      initialDeal(player);
      for (Card d: player) {
         if (d != null) {
            switch (d.getValue()){
               case 1:
                  playerhand.append("A\n");
                  break;
               case 11:
                  playerhand.append("J\n");
                  break;
               case 12:
                  playerhand.append("Q\n");
                  break;
               case 13:
                  playerhand.append("K\n");
                  break;
               default:
                  playerhand.append(d.getValue() + "\n");
            }
         }
      }
      initialDeal(dealer);
      dealerhand.append("Hole Card\n");
      for (int i = 1; i < dealer.length; ++i) {
         if (dealer[i] != null) {
            switch (dealer[i].getValue()){
               case 1:
                 dealerhand.append("A\n");
                 break;
               case 11:
                 dealerhand.append("J\n");
                 break;
               case 12:
                 dealerhand.append("Q\n");
                 break;
               case 13:
                 dealerhand.append("K\n");
                 break;
               default:
                 dealerhand.append(dealer[i].getValue() + "\n");
            }
         }
      }
   }//GEN-LAST:event_jButton1ActionPerformed

   private void hitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hitActionPerformed
      if (result.getText().equals("")) {
         if (!checkBust(player)) {
            hit(player);
            playerhand.setText("");
            for (Card d: player) {
               if (d != null) {
                  switch (d.getValue()){
                     case 1:
                        playerhand.append("A\n");
                        break;
                     case 11:
                        playerhand.append("J\n");
                        break;
                     case 12:
                        playerhand.append("Q\n");
                        break;
                     case 13:
                        playerhand.append("K\n");
                        break;
                     default:
                        playerhand.append(d.getValue() + "\n");
                  }
               }
            }
         }
         if (checkBust(player)) {
            dealerPlay();
         }
      }
   }//GEN-LAST:event_hitActionPerformed

   private void stayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stayActionPerformed
      if (result.getText().equals("")) {
         dealerhand.setText("");
         for (Card d: dealer) {
            if (d != null) {
               switch (d.getValue()){
                  case 1:
                     dealerhand.append("A\n");
                     break;
                  case 11:
                     dealerhand.append("J\n");
                     break;
                  case 12:
                     dealerhand.append("Q\n");
                     break;
                  case 13:
                     dealerhand.append("K\n");
                     break;
                  default:
                     dealerhand.append(d.getValue() + "\n");
               }
            }
         }
         dealerPlay();
      }
   }//GEN-LAST:event_stayActionPerformed

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JTextArea dealerhand;
   private javax.swing.JButton hit;
   private javax.swing.JButton jButton1;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JScrollPane jScrollPane2;
   private javax.swing.JTextArea playerhand;
   private javax.swing.JTextArea result;
   private javax.swing.JButton stay;
   // End of variables declaration//GEN-END:variables
}