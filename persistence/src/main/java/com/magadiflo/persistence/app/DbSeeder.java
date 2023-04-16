package com.magadiflo.persistence.app;

import com.magadiflo.domain.app.Hotel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {

    private final IHotelRepository hotelRepository;

    public DbSeeder(IHotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Hotel marriot = new Hotel("Marriot", 5, true);
        Hotel ibis = new Hotel("Ibis", 3, false);
        Hotel goldenTulip = new Hotel("Golden Tulip", 4, true);

        List<Hotel> hotels = Arrays.asList(marriot, ibis, goldenTulip);
        this.hotelRepository.saveAll(hotels);
    }

}
