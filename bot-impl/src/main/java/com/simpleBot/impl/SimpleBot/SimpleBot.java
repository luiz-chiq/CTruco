package com.simpleBot.impl.SimpleBot;

import com.bueno.spi.model.CardToPlay;
import com.bueno.spi.model.GameIntel;
import com.bueno.spi.model.TrucoCard;
import com.bueno.spi.service.BotServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class SimpleBot implements BotServiceProvider {

    @Override
    public boolean getMaoDeOnzeResponse(GameIntel intel) {
        return hasAGreatHand(intel);
    }

    @Override
    public boolean decideIfRaises(GameIntel intel) {
        return hasAGreatHand(intel);
    }

    @Override
    public CardToPlay chooseCard(GameIntel intel) {

        if(!checkIfWillBeTheFirstToPlay(intel)) return CardToPlay.of(getLowestCardToWin(intel).get());
        if(getRoundNumber(intel) == 1) return CardToPlay.of(getLowestCard(intel));
        if(getRoundNumber(intel) == 2) return CardToPlay.of(getLowestCard(intel));
        return CardToPlay.of(getHighestCard(intel));
    }

    @Override
    public int getRaiseResponse(GameIntel intel) {
        double average = getAverageCardValue(intel);
        if (getAmountOfManilhas(intel) > 0 || average > 8) return 1;
        if (average > 6) return 0;
        return -1;
    }

    @Override
    public String getName() {
        return BotServiceProvider.super.getName();
    }

    public TrucoCard getHighestCard(GameIntel intel) {
        TrucoCard highestCard = intel.getCards().get(0);
        for (TrucoCard card : intel.getCards())
            if (card.relativeValue(intel.getVira()) > highestCard.relativeValue(intel.getVira())) highestCard = card;
        return highestCard;
    }

    public TrucoCard getLowestCard(GameIntel intel) {
        TrucoCard lowestCard = intel.getCards().get(0);
        for (TrucoCard card : intel.getCards())
            if (lowestCard.relativeValue(intel.getVira()) > card.relativeValue(intel.getVira())) lowestCard = card;
        return lowestCard;
    }

    double getAverageCardValue(GameIntel intel) {
        int values = 0;
        for (TrucoCard card : intel.getCards())
            values += card.relativeValue(intel.getVira());
        return  (double) values / intel.getCards().size();
    }

    int getRoundNumber (GameIntel intel) {
        return intel.getRoundResults().size() + 1;
    }

    int getAmountOfManilhas(GameIntel intel){
        int amount = 0;
        for (TrucoCard card: intel.getCards())
            if(card.isManilha(intel.getVira()))
                amount += 1;
        return amount;
    }

    Optional<TrucoCard> getLowestCardToWin(GameIntel intel) {
        TrucoCard cardToPlay = intel.getCards().get(0);
        TrucoCard vira = intel.getVira();
        TrucoCard opponentCard = intel.getOpponentCard().orElseThrow(() -> new NoSuchElementException("O oponente ainda não jogou a carta dele"));
        int opponentCardRelativeValue = opponentCard.relativeValue(vira);
        for (TrucoCard card : intel.getCards())
            if (opponentCardRelativeValue < card.relativeValue(vira) &&
                    card.relativeValue(vira) < cardToPlay.relativeValue(vira) ) cardToPlay = card;
        return Optional.ofNullable(cardToPlay);
    }

    boolean checkIfWillBeTheFirstToPlay(GameIntel intel){
        return intel.getOpponentCard().isEmpty();
    }

    boolean hasAGreatHand(GameIntel intel){
        if (getAmountOfManilhas(intel) > 0) return true;
        if (intel.getOpponentScore() > 7) {
            if (getAmountOfManilhas(intel) > 0) return true;
            return getAverageCardValue(intel) < 7;
        }
        return true;
    }
}