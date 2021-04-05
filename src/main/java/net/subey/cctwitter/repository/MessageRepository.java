package net.subey.cctwitter.repository;

import net.subey.cctwitter.entity.Message;
import net.subey.cctwitter.entity.User;
import net.subey.cctwitter.view.TimelineView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {

    Page<Message> findAllByUserOrderByIdDesc(User user, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE EXISTS " +
            "(SELECT 1 FROM User u WHERE m.user member of u.following AND u =?1) " +
            "ORDER BY m.id DESC")
    Page<Message> findAllByUserFollowing(User user, Pageable pageable);

    // For projections you need use aliases
    @Query("SELECT m.user.nick AS user, m.body AS body FROM Message m WHERE EXISTS " +
            "(SELECT 1 FROM User u WHERE m.user member of u.following AND u =?1) " +
            "ORDER BY m.id DESC")
    Page<TimelineView> findAllViewByUserFollowing(User user, Pageable pageable);
}

