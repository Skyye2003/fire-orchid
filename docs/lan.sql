/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : lan

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 31/08/2023 16:11:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for disk_content
-- ----------------------------
DROP TABLE IF EXISTS `disk_content`;
CREATE TABLE `disk_content`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '盘块号',
  `status` int NULL DEFAULT NULL COMMENT '盘状态',
  `content` varchar(71) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盘内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 384 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of disk_content
-- ----------------------------
INSERT INTO `disk_content` VALUES (1, 0, '0');
INSERT INTO `disk_content` VALUES (2, 0, '0');
INSERT INTO `disk_content` VALUES (3, 0, '0');
INSERT INTO `disk_content` VALUES (4, 0, '0');
INSERT INTO `disk_content` VALUES (5, 0, '0');
INSERT INTO `disk_content` VALUES (6, 0, '0');
INSERT INTO `disk_content` VALUES (7, 0, '0');
INSERT INTO `disk_content` VALUES (8, 0, '0');
INSERT INTO `disk_content` VALUES (9, 0, '0');
INSERT INTO `disk_content` VALUES (10, 0, '0');
INSERT INTO `disk_content` VALUES (11, 0, '0');
INSERT INTO `disk_content` VALUES (12, 0, '0');
INSERT INTO `disk_content` VALUES (13, 0, '0');
INSERT INTO `disk_content` VALUES (14, 0, '0');
INSERT INTO `disk_content` VALUES (15, 0, '0');
INSERT INTO `disk_content` VALUES (16, 0, '0');
INSERT INTO `disk_content` VALUES (17, 0, '0');
INSERT INTO `disk_content` VALUES (18, 0, '0');
INSERT INTO `disk_content` VALUES (19, 0, '0');
INSERT INTO `disk_content` VALUES (20, 0, '0');
INSERT INTO `disk_content` VALUES (21, 0, '0');
INSERT INTO `disk_content` VALUES (22, 0, '0');
INSERT INTO `disk_content` VALUES (23, 0, '0');
INSERT INTO `disk_content` VALUES (24, 0, '0');
INSERT INTO `disk_content` VALUES (25, 0, '0');
INSERT INTO `disk_content` VALUES (26, 0, '0');
INSERT INTO `disk_content` VALUES (27, 0, '0');
INSERT INTO `disk_content` VALUES (28, 0, '0');
INSERT INTO `disk_content` VALUES (29, 0, '0');
INSERT INTO `disk_content` VALUES (30, 0, '0');
INSERT INTO `disk_content` VALUES (31, 0, '0');
INSERT INTO `disk_content` VALUES (32, 0, '0');
INSERT INTO `disk_content` VALUES (33, 0, '0');
INSERT INTO `disk_content` VALUES (34, 0, '0');
INSERT INTO `disk_content` VALUES (35, 0, '0');
INSERT INTO `disk_content` VALUES (36, 0, '0');
INSERT INTO `disk_content` VALUES (37, 0, '0');
INSERT INTO `disk_content` VALUES (38, 0, '0');
INSERT INTO `disk_content` VALUES (39, 0, '0');
INSERT INTO `disk_content` VALUES (40, 0, '0');
INSERT INTO `disk_content` VALUES (41, 0, '0');
INSERT INTO `disk_content` VALUES (42, 0, '0');
INSERT INTO `disk_content` VALUES (43, 0, '0');
INSERT INTO `disk_content` VALUES (44, 0, '0');
INSERT INTO `disk_content` VALUES (45, 0, '0');
INSERT INTO `disk_content` VALUES (46, 0, '0');
INSERT INTO `disk_content` VALUES (47, 0, '0');
INSERT INTO `disk_content` VALUES (48, 0, '0');
INSERT INTO `disk_content` VALUES (49, 0, '0');
INSERT INTO `disk_content` VALUES (50, 0, '0');
INSERT INTO `disk_content` VALUES (51, 0, '0');
INSERT INTO `disk_content` VALUES (52, 0, '0');
INSERT INTO `disk_content` VALUES (53, 0, '0');
INSERT INTO `disk_content` VALUES (54, 0, '0');
INSERT INTO `disk_content` VALUES (55, 0, '0');
INSERT INTO `disk_content` VALUES (56, 0, '0');
INSERT INTO `disk_content` VALUES (57, 0, '0');
INSERT INTO `disk_content` VALUES (58, 0, '0');
INSERT INTO `disk_content` VALUES (59, 0, '0');
INSERT INTO `disk_content` VALUES (60, 0, '0');
INSERT INTO `disk_content` VALUES (61, 0, '0');
INSERT INTO `disk_content` VALUES (62, 0, '0');
INSERT INTO `disk_content` VALUES (63, 0, '0');
INSERT INTO `disk_content` VALUES (64, 0, '0');
INSERT INTO `disk_content` VALUES (65, 0, '0');
INSERT INTO `disk_content` VALUES (66, 0, '0');
INSERT INTO `disk_content` VALUES (67, 0, '0');
INSERT INTO `disk_content` VALUES (68, 0, '0');
INSERT INTO `disk_content` VALUES (69, 0, '0');
INSERT INTO `disk_content` VALUES (70, 0, '0');
INSERT INTO `disk_content` VALUES (71, 0, '0');
INSERT INTO `disk_content` VALUES (72, 0, '0');
INSERT INTO `disk_content` VALUES (73, 0, '0');
INSERT INTO `disk_content` VALUES (74, 0, '0');
INSERT INTO `disk_content` VALUES (75, 0, '0');
INSERT INTO `disk_content` VALUES (76, 0, '0');
INSERT INTO `disk_content` VALUES (77, 0, '0');
INSERT INTO `disk_content` VALUES (78, 0, '0');
INSERT INTO `disk_content` VALUES (79, 0, '0');
INSERT INTO `disk_content` VALUES (80, 0, '0');
INSERT INTO `disk_content` VALUES (81, 0, '0');
INSERT INTO `disk_content` VALUES (82, 0, '0');
INSERT INTO `disk_content` VALUES (83, 0, '0');
INSERT INTO `disk_content` VALUES (84, 0, '0');
INSERT INTO `disk_content` VALUES (85, 0, '0');
INSERT INTO `disk_content` VALUES (86, 0, '0');
INSERT INTO `disk_content` VALUES (87, 0, '0');
INSERT INTO `disk_content` VALUES (88, 0, '0');
INSERT INTO `disk_content` VALUES (89, 0, '0');
INSERT INTO `disk_content` VALUES (90, 0, '0');
INSERT INTO `disk_content` VALUES (91, 0, '0');
INSERT INTO `disk_content` VALUES (92, 0, '0');
INSERT INTO `disk_content` VALUES (93, 0, '0');
INSERT INTO `disk_content` VALUES (94, 0, '0');
INSERT INTO `disk_content` VALUES (95, 0, '0');
INSERT INTO `disk_content` VALUES (96, 0, '0');
INSERT INTO `disk_content` VALUES (97, 0, '0');
INSERT INTO `disk_content` VALUES (98, 0, '0');
INSERT INTO `disk_content` VALUES (99, 0, '0');
INSERT INTO `disk_content` VALUES (100, 0, '0');
INSERT INTO `disk_content` VALUES (101, 0, '0');
INSERT INTO `disk_content` VALUES (102, 0, '0');
INSERT INTO `disk_content` VALUES (103, 0, '0');
INSERT INTO `disk_content` VALUES (104, 0, '0');
INSERT INTO `disk_content` VALUES (105, 0, '0');
INSERT INTO `disk_content` VALUES (106, 0, '0');
INSERT INTO `disk_content` VALUES (107, 0, '0');
INSERT INTO `disk_content` VALUES (108, 0, '0');
INSERT INTO `disk_content` VALUES (109, 0, '0');
INSERT INTO `disk_content` VALUES (110, 0, '0');
INSERT INTO `disk_content` VALUES (111, 0, '0');
INSERT INTO `disk_content` VALUES (112, 0, '0');
INSERT INTO `disk_content` VALUES (113, 0, '0');
INSERT INTO `disk_content` VALUES (114, 0, '0');
INSERT INTO `disk_content` VALUES (115, 0, '0');
INSERT INTO `disk_content` VALUES (116, 0, '0');
INSERT INTO `disk_content` VALUES (117, 0, '0');
INSERT INTO `disk_content` VALUES (118, 0, '0');
INSERT INTO `disk_content` VALUES (119, 0, '0');
INSERT INTO `disk_content` VALUES (120, 0, '0');
INSERT INTO `disk_content` VALUES (121, 0, '0');
INSERT INTO `disk_content` VALUES (122, 0, '0');
INSERT INTO `disk_content` VALUES (123, 0, '0');
INSERT INTO `disk_content` VALUES (124, 0, '0');
INSERT INTO `disk_content` VALUES (125, 0, '0');
INSERT INTO `disk_content` VALUES (126, 0, '0');
INSERT INTO `disk_content` VALUES (127, 0, '0');
INSERT INTO `disk_content` VALUES (128, 0, '0');

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info`  (
  `id` int NOT NULL COMMENT '文件id',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件绝对路径',
  `attribute` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件属性',
  `start_id` int NULL DEFAULT NULL COMMENT '文件起始盘块号',
  `size` int NULL DEFAULT NULL COMMENT '文件长度',
  `op_type` int NULL DEFAULT NULL COMMENT '0读1写',
  `read_dnum` int NULL DEFAULT NULL COMMENT '读文件的磁盘盘块号',
  `read_bnum` int NULL DEFAULT NULL COMMENT '读文件的磁盘第几字节',
  `write_dnum` int NULL DEFAULT NULL COMMENT '写文件的磁盘盘块号',
  `write_bnum` int NULL DEFAULT NULL COMMENT '写文件的磁盘盘块号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_info
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
