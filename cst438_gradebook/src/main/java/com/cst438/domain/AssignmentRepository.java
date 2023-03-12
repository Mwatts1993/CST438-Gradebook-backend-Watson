package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AssignmentRepository extends CrudRepository <Assignment, Integer> {

	@Query("select a from Assignment a where a.id = :id")
	Assignment findAssignmentById(int id);

	@Query("select a from Assignment a where a.needsGrading=1 and a.dueDate < current_date and a.course.instructor= :email order by a.id")
	List<Assignment> findNeedGradingByEmail(@Param("email") String email);
	@SuppressWarnings("unchecked")
	Assignment save(Assignment s);

@Modifying
@Query("DELETE from Assignment a where a.id = :id")
void deleteAssignment(@Param("id") int id);

}
