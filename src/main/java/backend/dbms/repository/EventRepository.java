package backend.dbms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.dbms.models.Event;
import backend.dbms.models.Status;
import backend.dbms.models.User;

import java.util.List;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
  Optional<Event> findByEventName(String eventName);
  List<Event> findByEventStatus(Status eventStatus);
  List<Event> findByHolder(User holder);
}
