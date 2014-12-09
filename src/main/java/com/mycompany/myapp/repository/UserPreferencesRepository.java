package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the UserPreferences entity.
 */
public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {

}
