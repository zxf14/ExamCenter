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
  `id` int(11) NOT NULL,
  `quizId` int(11) NOT NULL,
  `studentId` varchar(100) COLLATE utf8_bin NOT NULL,
  `content` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '存选择的option id 的数组',
  PRIMARY KEY (`id`),
  KEY `studentId` (`studentId`),
  KEY `quizId` (`quizId`),
  CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`quizId`) REFERENCES `quiz` (`id`),
  CONSTRAINT `answer_ibfk_2` FOREIGN KEY (`studentId`) REFERENCES `student` (`student_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学生的回答';

-- ----------------------------
--  Table structure for `exam`
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam` (
  `start_time` datetime DEFAULT NULL,
  `id` int(11) NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `subject` varchar(20) DEFAULT NULL COMMENT '科目',
  `teacherId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `teacherId` (`teacherId`),
  CONSTRAINT `teacherId` FOREIGN KEY (`teacherId`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='一次老师发起的考试';

-- ----------------------------
--  Table structure for `groups`
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `teacher_id` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `students` varchar(5000) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `teacherId` (`teacher_id`),
  CONSTRAINT `groups_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `option`
-- ----------------------------
DROP TABLE IF EXISTS `option`;
CREATE TABLE `option` (
  `id` int(11) NOT NULL,
  `questionId` int(11) NOT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `isRight` tinyint(4) DEFAULT '0' COMMENT '0错误1正确',
  PRIMARY KEY (`id`),
  KEY `questionId` (`questionId`),
  CONSTRAINT `questionId` FOREIGN KEY (`questionId`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='题目选项';

-- ----------------------------
--  Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `content` varchar(10000) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='倒入的题目';

-- ----------------------------
--  Table structure for `quiz`
-- ----------------------------
DROP TABLE IF EXISTS `quiz`;
CREATE TABLE `quiz` (
  `examId` int(11) DEFAULT NULL,
  `questionId` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `value` int(11) DEFAULT NULL COMMENT '分值',
  PRIMARY KEY (`id`),
  KEY `examId` (`examId`),
  KEY `questionId` (`questionId`),
  CONSTRAINT `quiz_ibfk_1` FOREIGN KEY (`examId`) REFERENCES `exam` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `quiz_ibfk_2` FOREIGN KEY (`questionId`) REFERENCES `question` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考试试题';

-- ----------------------------
--  Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `student_no` varchar(100) COLLATE utf8_bin NOT NULL,
  `mail` varchar(100) COLLATE utf8_bin NOT NULL,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `password` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`student_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `testee`
-- ----------------------------
DROP TABLE IF EXISTS `testee`;
CREATE TABLE `testee` (
  `studentId` varchar(100) COLLATE utf8_bin NOT NULL,
  `examId` int(11) NOT NULL,
  `student_mail` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `score` int(11) DEFAULT '0' COMMENT '分数',
  PRIMARY KEY (`studentId`,`examId`),
  KEY `examId` (`examId`),
  CONSTRAINT `testee_ibfk_1` FOREIGN KEY (`studentId`) REFERENCES `student` (`student_no`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `testee_ibfk_2` FOREIGN KEY (`examId`) REFERENCES `exam` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='参加考试者';

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `password` varchar(45) COLLATE utf8_bin NOT NULL,
  `user_name` varchar(100) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
