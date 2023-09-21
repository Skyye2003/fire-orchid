/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.32 : Database - os
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
USE `os`;

/*Table structure for table `disk_content` */

DROP TABLE IF EXISTS `disk_content`;

CREATE TABLE `disk_content` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '盘块号',
  `status` int DEFAULT NULL COMMENT '盘状态',
  `content` varchar(71) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '盘内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=384 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `disk_content` */

insert  into `disk_content`(`id`,`status`,`content`) values 
(1,-1,'0'),
(2,-1,'0'),
(3,-1,'fi1yx4004002/di1  8006000/di2  8007000/ te  8015000/  a b4016001'),
(4,5,'HellWord.'),
(5,-1,'yes,indeed'),
(6,-1,''),
(7,-1,'fi2ji4010001/fi3tt4011001/di3  8008000'),
(8,-1,'fi4sc4012001/di4  8009000/fitte4013001/ te t4014001'),
(9,-1,''),
(10,-1,''),
(11,-1,''),
(12,-1,''),
(13,-1,''),
(14,-1,''),
(15,-1,''),
(16,-1,''),
(17,0,'0'),
(18,0,'0'),
(19,0,'0'),
(20,0,'0'),
(21,0,'0'),
(22,0,'0'),
(23,0,'0'),
(24,0,'0'),
(25,0,'0'),
(26,0,'0'),
(27,0,'0'),
(28,0,'0'),
(29,0,'0'),
(30,0,'0'),
(31,0,'0'),
(32,0,'0'),
(33,0,'0'),
(34,0,'0'),
(35,0,'0'),
(36,0,'0'),
(37,0,'0'),
(38,0,'0'),
(39,0,'0'),
(40,0,'0'),
(41,0,'0'),
(42,0,'0'),
(43,0,'0'),
(44,0,'0'),
(45,0,'0'),
(46,0,'0'),
(47,0,'0'),
(48,0,'0'),
(49,0,'0'),
(50,0,'0'),
(51,0,'0'),
(52,0,'0'),
(53,0,'0'),
(54,0,'0'),
(55,0,'0'),
(56,0,'0'),
(57,0,'0'),
(58,0,'0'),
(59,0,'0'),
(60,0,'0'),
(61,0,'0'),
(62,0,'0'),
(63,0,'0'),
(64,0,'0'),
(65,0,'0'),
(66,0,'0'),
(67,0,'0'),
(68,0,'0'),
(69,0,'0'),
(70,0,'0'),
(71,0,'0'),
(72,0,'0'),
(73,0,'0'),
(74,0,'0'),
(75,0,'0'),
(76,0,'0'),
(77,0,'0'),
(78,0,'0'),
(79,0,'0'),
(80,0,'0'),
(81,0,'0'),
(82,0,'0'),
(83,0,'0'),
(84,0,'0'),
(85,0,'0'),
(86,0,'0'),
(87,0,'0'),
(88,0,'0'),
(89,0,'0'),
(90,0,'0'),
(91,0,'0'),
(92,0,'0'),
(93,0,'0'),
(94,0,'0'),
(95,0,'0'),
(96,0,'0'),
(97,0,'0'),
(98,0,'0'),
(99,0,'0'),
(100,0,'0'),
(101,0,'0'),
(102,0,'0'),
(103,0,'0'),
(104,0,'0'),
(105,0,'0'),
(106,0,'0'),
(107,0,'0'),
(108,0,'0'),
(109,0,'0'),
(110,0,'0'),
(111,0,'0'),
(112,0,'0'),
(113,0,'0'),
(114,0,'0'),
(115,0,'0'),
(116,0,'0'),
(117,0,'0'),
(118,0,'0'),
(119,0,'0'),
(120,0,'0'),
(121,0,'0'),
(122,0,'0'),
(123,0,'0'),
(124,0,'0'),
(125,0,'0'),
(126,0,'0'),
(127,0,'0'),
(128,0,'0');

/*Table structure for table `file_info` */

DROP TABLE IF EXISTS `file_info`;

CREATE TABLE `file_info` (
  `id` int NOT NULL COMMENT '文件id',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件绝对路径',
  `attribute` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件属性',
  `start_id` int DEFAULT NULL COMMENT '文件起始盘块号',
  `size` int DEFAULT NULL COMMENT '文件长度',
  `op_type` int DEFAULT NULL COMMENT '0读1写',
  `read_dnum` int DEFAULT NULL COMMENT '读文件的磁盘盘块号',
  `read_bnum` int DEFAULT NULL COMMENT '读文件的磁盘第几字节',
  `write_dnum` int DEFAULT NULL COMMENT '写文件的磁盘盘块号',
  `write_bnum` int DEFAULT NULL COMMENT '写文件的磁盘盘块号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `file_info` */

insert  into `file_info`(`id`,`file_path`,`attribute`,`start_id`,`size`,`op_type`,`read_dnum`,`read_bnum`,`write_dnum`,`write_bnum`) values 
(4,'fi1.yx','4',4,2,0,4,0,5,10);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
