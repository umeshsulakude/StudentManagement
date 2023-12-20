CREATE TABLE `student` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `age` int NOT NULL,
                           `name` varchar(255) DEFAULT NULL,
                           `gender` varchar(20) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

