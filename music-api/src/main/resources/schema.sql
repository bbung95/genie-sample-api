DROP TABLE IF EXISTS `track`;
DROP TABLE IF EXISTS `album`;
DROP TABLE IF EXISTS `artist`;
DROP TABLE IF EXISTS `genre`;

CREATE TABLE `artist` (
                          `id`	INT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          `name`	VARCHAR(50)	NULL,
                          `birthday`	DATE	NULL,
                          `agency`	VARCHAR(50)	NULL,
                          `nationality`	VARCHAR(50)	NULL,
                          `contents`	TEXT	NULL,
                          `registrant`	VARCHAR(50)	NULL,
                          `created_date`	DATETIME	NULL,
                          `modified_date`	DATETIME	NULL
);

CREATE TABLE `album` (
                         `id`	INT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         `artist_id`	INT	NOT NULL,
                         `genre_id`	INT	NOT NULL,
                         `title`	VARCHAR(100)	NULL,
                         `release_date`	DATE	NULL,
                         `contents`	TEXT	NULL,
                         `registrant`	VARCHAR(50)	NULL,
                         `created_date`	DATETIME	NULL,
                         `modified_date`	DATETIME	NULL
);

CREATE TABLE `track` (
                         `id`	INT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         `album_id`	INT	NOT NULL,
                         `title`	VARCHAR(100)	NULL,
                         `play_time`	VARCHAR(100)	NULL,
                         `exposure`	VARCHAR(50)	NULL,
                         `orders`	INT	NULL,
                         `created_date`	DATETIME	NULL,
                         `modified_date`	DATETIME	NULL
);

CREATE TABLE `genre` (
                         `id`	INT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         `title`	VARCHAR(50)	NULL,
                         `created_date`	DATETIME	NULL,
                         `modified_date`	DATETIME	NULL
);

