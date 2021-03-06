package net.subey.cctwitter.repository;

import net.subey.cctwitter.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findUserByNick(String nick);
}
