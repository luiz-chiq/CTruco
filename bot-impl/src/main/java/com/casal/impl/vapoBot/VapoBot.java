package com.casal.impl.vapoBot;

import com.bueno.spi.model.CardToPlay;
import com.bueno.spi.model.GameIntel;
import com.bueno.spi.model.TrucoCard;
import com.bueno.spi.service.BotServiceProvider;

public class VapoBot implements BotServiceProvider {

    @Override
    public boolean getMaoDeOnzeResponse(GameIntel intel) {
        return false;
    }

    @Override
    public boolean decideIfRaises(GameIntel intel) {
        if (getAverageCardValue(intel) > 7) {
            return true;
        }
        return false;
    }

    @Override
    public CardToPlay chooseCard(GameIntel intel) {
        System.out.println("---------------");
        System.out.println("getCards: " + intel.getCards());
        System.out.println("getOpenCards: " + intel.getOpenCards());
        System.out.println("getVira: " + intel.getVira());
        System.out.println("getHandPoints: " + intel.getHandPoints());
        System.out.println("getOpponentScore: " + intel.getOpponentScore());
        System.out.println("getScore: " + intel.getScore());
        System.out.println("getOpponentCard: " + intel.getOpponentCard());
        System.out.println("getRoundResults: " + intel.getRoundResults());

        System.out.println(" average (?): " + getAverageCardValue(intel));

        if (intel.getOpponentCard().isPresent()) {
            TrucoCard opponentCard = intel.getOpponentCard().get();
        }

        System.out.println("your highest card: " + getYourHighestCard(intel));
        return CardToPlay.of(intel.getCards().get(0));
    }

    @Override
    public int getRaiseResponse(GameIntel intel) {
        double average = getAverageCardValue(intel);
        if (average < 4 || average > 9) return 0;
        else if (average > 7) return 1;
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

    private TrucoCard getYourHighestCard(GameIntel intel){
        TrucoCard highestCard = intel.getCards().get(0);

        for (TrucoCard card : intel.getCards()) {
            if (card.relativeValue(intel.getVira()) > highestCard.relativeValue(intel.getVira())) {
                highestCard = card;
            }
        }

        return highestCard;
    }
}
