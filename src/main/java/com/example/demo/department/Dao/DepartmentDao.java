package com.example.demo.department.Dao;

import com.example.demo.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d WHERE d.deptName = :deptName")
    Department getByName(@Param("deptName") String deptName);

}
