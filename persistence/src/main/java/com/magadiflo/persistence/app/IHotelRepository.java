package com.magadiflo.persistence.app;

import com.magadiflo.domain.app.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHotelRepository extends JpaRepository<Hotel, Long> {

}
