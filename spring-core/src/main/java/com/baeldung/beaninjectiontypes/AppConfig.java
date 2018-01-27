package com.baeldung.beaninjectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /**
     * Bean uses constructor injection
     *
     * @return HourlyNews bean
     */
    @Bean
    public HourlyNews hourlyNews() {
        HourlyNews hourlyNews = new HourlyNews(weatherUpdate());
        return hourlyNews;
    }

    /**
     * Bean using setter injection
     *
     * @return HourlyNews bean
     */
    /*@Bean
    public HourlyNews hourlyNews() {
        HourlyNews hourlyNews = new HourlyNews();
        hourlyNews.setWeatherUpdate(weatherUpdate());
        return hourlyNews;
    }*/

    @Bean
    public WeatherUpdate weatherUpdate() {
        return new LocalWeatherUpdate(); // or RestWeatherUpdate(). Just changing the class here,
                                         // allows to change the injected type
    }
}
