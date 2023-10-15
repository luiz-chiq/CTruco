package com.casal.impl.vapoBot;

import com.bueno.spi.model.CardToPlay;
import com.bueno.spi.model.GameIntel;
import com.bueno.spi.model.TrucoCard;
import com.bueno.spi.service.BotServiceProvider;

import java.util.List;

public class VapoBot implements BotServiceProvider {

    @Override
    public boolean getMaoDeOnzeResponse(GameIntel intel) {
        return false;
    }

    @Override
    public boolean decideIfRaises(GameIntel intel) {
        // Se possuir uma média de cartas maiores que o valor 8, pedir truco:
//        System.out.println(getAverageCardValue(intel));
        if (getAverageCardValue(intel) > 8) {
            return true;
        }
        // Se o jogador até tiver feito, mas tiver feito com cartas baixas, pedir truco:
        else if (checkHowManyOpponentCardsAreBad(intel) > 1) {
            return true;
        }
        return false;
    }

    @Override
    public CardToPlay chooseCard(GameIntel intel) {
//        System.out.println("---------------");
//        System.out.println("getCards: " + intel.getCards());
//        System.out.println("getOpenCards: " + intel.getOpenCards());
//        System.out.println("getVira: " + intel.getVira());
//        System.out.println("getHandPoints: " + intel.getHandPoints());
//        System.out.println("getOpponentScore: " + intel.getOpponentScore());
//        System.out.println("getScore: " + intel.getScore());
//        System.out.println("getOpponentCard: " + intel.getOpponentCard());
//        System.out.println("getRoundResults: " + intel.getRoundResults());
//
//        System.out.println(" average (?): " + getAverageCardValue(intel));

//        if (intel.getOpponentCard().isPresent()) {
//            TrucoCard opponentCard = intel.getOpponentCard().get();
//            System.out.println(intel.getHandPoints());
//            if (intel.getHandPoints() == 0) {
//                System.out.println("vai jogar a maior carta");
//                return CardToPlay.of(getYourHighestCard(intel));
//            }
//
//        }
//        System.out.println(checkHowManyOpponentCardsAreBad(intel));
//        System.out.println("your highest card: " + getYourHighestCard(intel));
//        System.out.println("your lowest card: " + getYourLowestCard(intel));

//        System.out.println("handPoints: " + intel.getHandPoints());
//        System.out.println("roundResults: " + intel.getRoundResults());

        // Se ele possui uma derrota ou se não venceu ainda a primeira, tenta vencer:
        if (intel.getRoundResults().contains(GameIntel.RoundResult.LOST) || intel.getRoundResults().isEmpty()) {
//            System.out.println("vai jogar a maior carta");
            return CardToPlay.of(getYourHighestCard(intel));
        }



//        System.out.println("vai jogar a primeira carta");
        return CardToPlay.of(getYourLowestCard(intel));
    }

    @Override
    public int getRaiseResponse(GameIntel intel) {
        double average = getAverageCardValue(intel);
      if (average < 4 || average > 8) return 1;
      else if (average > 6) return 0;
      return -1;
    }

    private double getAverageCardValue(GameIntel intel){
        int values = 0;
//        int value = intel.getCards().get(0).relativeValue(intel.getVira());
        for (TrucoCard card : intel.getCards()) {
            values += card.relativeValue(intel.getVira());
        }
        double average = (double) values/intel.getCards().size();
        return average;
    }

    public TrucoCard getYourHighestCard(GameIntel intel){
        TrucoCard highestCard = intel.getCards().get(0);

        for (TrucoCard card : intel.getCards()) {
            if (card.relativeValue(intel.getVira()) > highestCard.relativeValue(intel.getVira())) {
                highestCard = card;
            }
        }

        return highestCard;
    }

    public TrucoCard getYourLowestCard(GameIntel intel) {
        TrucoCard lowestCard = intel.getCards().get(0);

        for (TrucoCard card : intel.getCards()) {
            if (lowestCard.relativeValue(intel.getVira()) > card.relativeValue(intel.getVira())) {
                lowestCard = card;
            }
        }

        return lowestCard;
    }

    public int checkHowManyOpponentCardsAreBad(GameIntel intel) {
        int badCardsCounter = 0;
        if (intel.getOpponentCard().isPresent()) {
            TrucoCard opponentCard = intel.getOpponentCard().get();
            if (opponentCard.relativeValue(intel.getVira()) < 8) {
                badCardsCounter++;
            }

        }
        return badCardsCounter;
    }

}
