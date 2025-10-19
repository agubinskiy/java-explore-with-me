package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query(value = "SELECT app, uri, COUNT(*) as hits " +
            "FROM stats " +
            "WHERE datetime BETWEEN :start AND :end " +
            "AND (:uris IS NULL OR uri IN (:uris)) " +
            "GROUP BY app, uri " +
            "ORDER BY hits DESC",
            nativeQuery = true)
    List<Object[]> findStats(@Param("start") LocalDateTime start,
                             @Param("end") LocalDateTime end,
                             @Param("uris") List<String> uris);

    @Query(value = "SELECT app, uri, COUNT(DISTINCT ip) as hits " +
            "FROM stats " +
            "WHERE datetime BETWEEN :start AND :end " +
            "AND (:uris IS NULL OR uri IN (:uris)) " +
            "GROUP BY app, uri " +
            "ORDER BY hits DESC",
            nativeQuery = true)
    List<Object[]> findUniqueStats(@Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end,
                                   @Param("uris") List<String> uris);
}
