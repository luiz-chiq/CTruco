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
}
