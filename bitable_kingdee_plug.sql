/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : 127.0.0.1:3306
 Source Schema         : bitable_kingdee_plug

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 12/12/2023 09:52:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for limit_package
-- ----------------------------
DROP TABLE IF EXISTS `limit_package`;
CREATE TABLE `limit_package` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `row_limit` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of limit_package
-- ----------------------------
BEGIN;
INSERT INTO `limit_package` VALUES (1, '试用', 10000);
INSERT INTO `limit_package` VALUES (2, '普通用户', 150);
INSERT INTO `limit_package` VALUES (3, '高级用户', 10000);
COMMIT;

-- ----------------------------
-- Table structure for tenant_auth
-- ----------------------------
DROP TABLE IF EXISTS `tenant_auth`;
CREATE TABLE `tenant_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_key` varchar(255) DEFAULT NULL,
  `authorization_id` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL,
  `pay_end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tenant_auth
-- ----------------------------
BEGIN;
INSERT INTO `tenant_auth` VALUES (1, 'ou_1', 1, '2023-11-28 17:18:38', '2023-11-28 17:18:38', NULL, NULL);
INSERT INTO `tenant_auth` VALUES (2, 'ou_2', 2, '2023-08-15 18:17:22', '2023-08-15 18:17:24', NULL, NULL);
INSERT INTO `tenant_auth` VALUES (3, 'ou_3', 2, '2023-11-28 18:43:49', '2023-11-28 18:44:17', NULL, NULL);
INSERT INTO `tenant_auth` VALUES (4, 'ou_4', 2, '2023-08-15 18:44:30', '2023-08-15 18:44:36', NULL, NULL);
INSERT INTO `tenant_auth` VALUES (5, 'ou_5', 3, '2023-08-08 18:44:51', '2023-08-15 18:44:58', NULL, NULL);
INSERT INTO `tenant_auth` VALUES (6, 'ou_6', 1, '2023-11-28 18:49:34', '2023-11-28 18:49:34', NULL, NULL);
INSERT INTO `tenant_auth` VALUES (7, 'ou_123', 1, '2023-11-28 19:07:50', '2023-11-28 19:07:50', NULL, NULL);
INSERT INTO `tenant_auth` VALUES (86, '2eda20a56c4f165b', 1, '2023-12-10 10:05:23', '2023-12-11 10:05:23', '2023-12-10 16:22:29', '2024-01-10 16:22:37');
INSERT INTO `tenant_auth` VALUES (87, '1234567890', 1, '2023-12-11 14:05:32', '2023-12-11 14:05:32', NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
