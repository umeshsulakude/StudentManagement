CREATE TABLE `student` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `age` int NOT NULL,
                           `name` varchar(255) DEFAULT NULL,
                           `gender` varchar(20) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `department` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `dept_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



ALTER TABLE student
ADD COLUMN department_id bigint,
ADD CONSTRAINT fk_department
    FOREIGN KEY (department_id)
    REFERENCES department(id);

