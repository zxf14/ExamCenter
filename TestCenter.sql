/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : localhost
 Source Database       : TestCenter

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : utf-8

 Date: 12/02/2017 13:55:46 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `answer`
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `id`        INT(11)          NOT NULL,
  `quizId`    INT(11)          NOT NULL,
  `studentId` VARCHAR(100)
              COLLATE utf8_bin NOT NULL,
  `content`   VARCHAR(100)
              COLLATE utf8_bin DEFAULT NULL
  COMMENT '存选择的option id 的数组',
  PRIMARY KEY (`id`),
  KEY `studentId` (`studentId`),
  KEY `quizId` (`quizId`),
  CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`quizId`) REFERENCES `quiz` (`id`),
  CONSTRAINT `answer_ibfk_2` FOREIGN KEY (`studentId`) REFERENCES `student` (`student_no`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT ='学生的回答';

-- ----------------------------
--  Table structure for `exam`
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam` (
  `start_time` DATETIME    DEFAULT NULL,
  `id`         INT(11) NOT NULL,
  `end_time`   DATETIME    DEFAULT NULL,
  `subject`    VARCHAR(20) DEFAULT NULL
  COMMENT '科目',
  `quizCount`  INT(11) NOT NULL,
  `teacherId`  INT(11)     DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `teacherId` (`teacherId`),
  CONSTRAINT `teacherId` FOREIGN KEY (`teacherId`) REFERENCES `user` (`id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='一次老师发起的考试';

-- ----------------------------
--  Table structure for `groups`
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `teacher_id` INT(11)          DEFAULT NULL,
  `name`       VARCHAR(20)      DEFAULT NULL,
  `students`   VARCHAR(5000)    DEFAULT NULL,
  `id`         INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `teacherId` (`teacher_id`),
  CONSTRAINT `groups_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;

-- ----------------------------
--  Table structure for `option`
-- ----------------------------
DROP TABLE IF EXISTS `option`;
CREATE TABLE `option` (
  `id`         INT(11) NOT NULL,
  `questionId` INT(11) NOT NULL,
  `content`    VARCHAR(1000) DEFAULT NULL,
  `isRight`    TINYINT(4)    DEFAULT '0'
  COMMENT '0错误1正确',
  PRIMARY KEY (`id`),
  KEY `questionId` (`questionId`),
  CONSTRAINT `questionId` FOREIGN KEY (`questionId`) REFERENCES `question` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='题目选项';

-- ----------------------------
--  Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `content` VARCHAR(10000) DEFAULT NULL,
  `id`      INT(11) NOT NULL,
  `type`    INT(11)        DEFAULT '0',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='倒入的题目';

-- ----------------------------
--  Table structure for `quiz`
-- ----------------------------
DROP TABLE IF EXISTS `quiz`;
CREATE TABLE `quiz` (
  `examId`     INT(11) DEFAULT NULL,
  `questionId` INT(11) DEFAULT NULL,
  `id`         INT(11) NOT NULL,
  `value`      INT(11) DEFAULT NULL
  COMMENT '分值',
  PRIMARY KEY (`id`),
  KEY `examId` (`examId`),
  KEY `questionId` (`questionId`),
  CONSTRAINT `quiz_ibfk_1` FOREIGN KEY (`examId`) REFERENCES `exam` (`id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL,
  CONSTRAINT `quiz_ibfk_2` FOREIGN KEY (`questionId`) REFERENCES `question` (`id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='考试试题';

-- ----------------------------
--  Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `student_no` VARCHAR(100)
               COLLATE utf8_bin NOT NULL,
  `mail`       VARCHAR(100)
               COLLATE utf8_bin NOT NULL,
  `name`       VARCHAR(100)
               COLLATE utf8_bin NOT NULL,
  `password`   VARCHAR(45)
               COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`student_no`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;

-- ----------------------------
--  Table structure for `testee`
-- ----------------------------
DROP TABLE IF EXISTS `testee`;
CREATE TABLE `testee` (
  `studentId`    VARCHAR(100)
                 COLLATE utf8_bin NOT NULL,
  `examId`       INT(11)          NOT NULL,
  `student_mail` VARCHAR(30)
                 COLLATE utf8_bin DEFAULT NULL,
  `score`        INT(11)          DEFAULT '0'
  COMMENT '分数',
  PRIMARY KEY (`studentId`, `examId`),
  KEY `examId` (`examId`),
  CONSTRAINT `testee_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `student` (`student_no`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `testee_ibfk_2` FOREIGN KEY (`examId`) REFERENCES `exam` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT ='参加考试者';

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`        INT(11)          NOT NULL,
  `password`  VARCHAR(45)
              COLLATE utf8_bin NOT NULL,
  `user_name` VARCHAR(100)
              COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
