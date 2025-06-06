package com.reslink.demo.repository;

import com.reslink.demo.model.UserSubmittedAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSubmittedAlertRepository extends JpaRepository<UserSubmittedAlert, Long> {
}