package com.bankapp.bank.config;

import com.bankapp.bank.service.LocationService;
import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Optional;

@Configuration
public class LocationConfig {

    @Bean
    @ConditionalOnProperty(havingValue = "GEOIP", prefix = "customer", name = "country-validation")
    LocationService locationService() {
        return ip -> {
            try {
                InetAddress ipAddress = InetAddress.getByName(ip);
                //Provide geoip db here
                DatabaseReader build = new DatabaseReader.Builder(new File("")).withCache(new CHMCache()).build();
                Optional<CountryResponse> country = build.tryCountry(ipAddress);
                if (country.isPresent()) {
                    return true;
                }
                return false;
            } catch (IOException e) {

            } catch (GeoIp2Exception e) {

            }
            return false;
        };
    }

    @Bean
    @ConditionalOnProperty(havingValue = "NONE", prefix = "customer", name = "country-validation")
    LocationService locationServiceNoOp(CustomerConfiguration customerConfiguration) {
        return input -> true;
    }

    @Bean
    @ConditionalOnProperty(havingValue = "ADDRESS", prefix = "customer", name = "country-validation")
    LocationService locationServiceByCountryCode(CustomerConfiguration customerConfiguration) {
        return countryCode -> customerConfiguration.getCountries().contains(countryCode);
    }
}
