/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE IF NOT EXISTS `larablog` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `larablog`;

CREATE TABLE IF NOT EXISTS `posts` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `post` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `author_id` int(11) DEFAULT NULL,
  `published` tinyint(1) NOT NULL DEFAULT 0,
  `views` int(11) NOT NULL DEFAULT 0,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DELETE FROM `posts`;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` (`id`, `title`, `post`, `author_id`, `published`, `views`, `created_at`, `updated_at`) VALUES
	(2, 'Sample Post 2', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc lobortis, ante in pretium volutpat, risus metus molestie odio, non placerat magna diam sit amet augue. Donec ullamcorper ornare mauris, id bibendum lacus viverra vel. Curabitur in pellentesque metus. Quisque gravida molestie turpis, id tincidunt purus viverra vitae. In sollicitudin, arcu at interdum sagittis, massa mi cursus diam, eu sodales purus erat vitae dui. Curabitur tempus urna consequat odio pretium pellentesque. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Etiam tincidunt eros orci, sit amet pharetra eros accumsan vitae. Quisque a massa id nisi sodales rhoncus sed ut ante. Duis sed augue non velit feugiat varius eget eu velit. Nam commodo posuere massa sed convallis. Phasellus dapibus commodo lectus, vitae gravida lorem. Donec molestie a nisi ac mattis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Aliquam tempor ante et rutrum consequat.', 11, 1, 100, '2017-12-12 18:48:19', NULL),
	(3, 'Sample Post 3', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc lobortis, ante in pretium volutpat, risus metus molestie odio, non placerat magna diam sit amet augue. Donec ullamcorper ornare mauris, id bibendum lacus viverra vel. Curabitur in pellentesque metus. Quisque gravida molestie turpis, id tincidunt purus viverra vitae. In sollicitudin, arcu at interdum sagittis, massa mi cursus diam, eu sodales purus erat vitae dui. Curabitur tempus urna consequat odio pretium pellentesque. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Etiam tincidunt eros orci, sit amet pharetra eros accumsan vitae. Quisque a massa id nisi sodales rhoncus sed ut ante. Duis sed augue non velit feugiat varius eget eu velit. Nam commodo posuere massa sed convallis. Phasellus dapibus commodo lectus, vitae gravida lorem. Donec molestie a nisi ac mattis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Aliquam tempor ante et rutrum consequat.', 11, 1, 100, '2017-12-12 18:48:22', NULL),
	(4, 'Sample Post 2212', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc lobortis, ante in pretium volutpat, risus metus molestie odio, non placerat magna diam sit amet augue. Donec ullamcorper ornare mauris, id bibendum lacus viverra vel. Curabitur in pellentesque metus. Quisque gravida molestie turpis, id tincidunt purus viverra vitae. In sollicitudin, arcu at interdum sagittis, massa mi cursus diam, eu sodales purus erat vitae dui. Curabitur tempus urna consequat odio pretium pellentesque. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Etiam tincidunt eros orci, sit amet pharetra eros accumsan vitae. Quisque a massa id nisi sodales rhoncus sed ut ante. Duis sed augue non velit feugiat varius eget eu velit. Nam commodo posuere massa sed convallis. Phasellus dapibus commodo lectus, vitae gravida lorem. Donec molestie a nisi ac mattis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Aliquam tempor ante et rutrum consequat.', 11, 1, 100, '2017-12-12 18:48:24', NULL),
	(9, 'This is a Test', 'HJh sahjnasj hjas hjashj ashj ashj ashjsa sahjass naskjasnmas', 11, 0, 0, NULL, NULL),
	(10, 'Test Title', 'hjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj', 11, 0, 0, NULL, NULL),
	(11, 'Test Title 2', 'This is a as as sa asmas as as as as as', 11, 0, 0, NULL, NULL),
	(12, 'This is another Test Article', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don\'t look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn\'t anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.', 11, 0, 0, '2017-12-14 03:48:52', '2017-12-14 03:48:52');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_admin` int(10) unsigned NULL DEFAULT 0,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_email_unique` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `name`, `email`, `password`, `is_admin`, `remember_token`, `created_at`, `updated_at`) VALUES
	(11, 'Test User', 'test@test.com', '$2a$10$i/TgIqC3xRMnqCEbDryfxuZqhfM/qekfsaQUjBVPUKiiB3Nofr/wO', 0, NULL, '2017-12-14 03:40:57', '2017-12-14 03:40:57');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
