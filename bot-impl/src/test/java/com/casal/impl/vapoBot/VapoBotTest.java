package com.casal.impl.vapoBot;

import com.bueno.spi.model.CardRank;
import com.bueno.spi.model.CardSuit;
import com.bueno.spi.model.GameIntel;
import com.bueno.spi.model.TrucoCard;
import com.indi.impl.addthenewsoul.AddTheNewSoul;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VapoBotTest {

    private GameIntel.StepBuilder stepBuilder;
    private List<GameIntel.RoundResult> results;
    private List<TrucoCard> openCards;
    private List<TrucoCard> botCards;
    private TrucoCard vira;
    private VapoBot vapoBot;

    @BeforeEach
    public void setUp(){
        vapoBot = new VapoBot();
    }

    @Test
    void getYourHighestCard() {

    }

    @Test
    void getYourLowestCard() {

    }

    @Test
    @DisplayName("Should increase if sum of card values is above average")
    public void shouldIncreaseIfSumOfCardValuesIsAboveAverageTest(){
        TrucoCard vira = TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS);

        List<TrucoCard> openCards = Arrays.asList(
                TrucoCard.of(CardRank.SIX, CardSuit.DIAMONDS),
                TrucoCard.of(CardRank.FIVE, CardSuit.DIAMONDS));
        List<TrucoCard> cards = Arrays.asList(
                TrucoCard.of(CardRank.THREE, CardSuit.CLUBS),
                TrucoCard.of(CardRank.FOUR, CardSuit.HEARTS),
                TrucoCard.of(CardRank.SEVEN, CardSuit.CLUBS));

        TrucoCard opponentCard = TrucoCard.of(CardRank.FIVE, CardSuit.DIAMONDS);

        stepBuilder = GameIntel.StepBuilder.with()
                .gameInfo(List.of(GameIntel.RoundResult.LOST), openCards, vira, 1)
                .botInfo(cards, 0)
                .opponentScore(0)
                .opponentCard(opponentCard);
        assertTrue(vapoBot.decideIfRaises(stepBuilder.build()));
    }
}