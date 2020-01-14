package com.baeldung.hexagonal.infrastructure;

import com.baeldung.hexagonal.domain.HoroscopeRepositoryPort;
import com.google.common.collect.ImmutableMap;

public class InMemoryHoroscopeRepositoryAdapter implements HoroscopeRepositoryPort {

    final ImmutableMap<String,String> zodiacVsHoroscopeMap = ImmutableMap.<String, String>builder()
        .put("ARIES", "You are likely to hit at least one obstacle today") 
        .put("TAURUS", "Be careful who you show affection to today. After all, you don't want anyone to get the wrong idea") 
        .put("GEMINI", "Today, the good news you've wanted to hear for so long could finally arrive, and it might have a surprise too") 
        .put("CANCER", "Your original ideas need to be protected right now. They are in danger of being stolen!") 
        .put("LEO", "It's healthy to treat yourself right, but if you overindulge for too long, you run the risk of something else") 
        .put("VIRGO", "Your adventurous spirit is getting stronger right now, and your heart is doing its bidding.") 
        .put("LIBRA", "Celebrate the recent kindness a stranger has shown you by performing an act of kindness of your own") 
        .put("SCORPIO", "You could be fighting a very tough battle between your patience and your temper today.") 
        .put("SAGITTARIUS", "Before you get involved in any new event, sport, or relationship today, you need to put things on track")
        .put("CAPRICORN", "Walk down the quieter path today. Avoid the crowds, skip out on the hot spots, and say 'no, thanks'")
        .put("AQUARIUS", "The cast of characters in your life will start changing today, and you might not like who enters in your life")
        .put("PISCES", "It's not your responsibility to fix the problems in one of your friend's lives.")
        .build();
    
    
    @Override
    public String getHoroscope(String zodiacSign) {
        return zodiacVsHoroscopeMap.getOrDefault(
                zodiacSign.toUpperCase(), 
                String.format("The input zodiac sign [%s] is not valid", zodiacSign));
    }
}