/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.7.17 : Database - insurance_card
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`insurance_card` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `insurance_card`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `login_name` varchar(18) NOT NULL COMMENT '登录名',
  `name` varchar(10) NOT NULL COMMENT '用户名称',
  `password` varchar(32) NOT NULL COMMENT '密码MD5',
  PRIMARY KEY (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

/*Table structure for table `agent` */

DROP TABLE IF EXISTS `agent`;

CREATE TABLE `agent` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `wx_id` varchar(32) DEFAULT NULL COMMENT '代理商微信OPENID',
  `phone_number` varchar(11) NOT NULL COMMENT '手机号',
  `name` varchar(10) NOT NULL COMMENT '姓名',
  `apply_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '申请的绑定类型:默认0',
  `status` tinyint(4) NOT NULL COMMENT '账号状态0：申请；1：绑定；2：解绑',
  `create_type` tinyint(4) NOT NULL COMMENT '创建账户类型0：代理商申请，1：管理员添加',
  `create_time` datetime NOT NULL COMMENT '记录创建时间',
  `bind_time` datetime DEFAULT NULL COMMENT '绑定时间',
  `unbind_time` datetime DEFAULT NULL COMMENT '解绑时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='代理商账户表';

/*Table structure for table `card` */

DROP TABLE IF EXISTS `card`;

CREATE TABLE `card` (
  `id` int(11) NOT NULL COMMENT '主键ID',
  `password` varchar(32) NOT NULL COMMENT '卡片密码',
  `type` tinyint(4) DEFAULT NULL COMMENT '卡片类型',
  `state` tinyint(4) DEFAULT NULL COMMENT '卡片状态0：未使用，1：已激活，2：已使用',
  `agent_id` int(11) DEFAULT NULL COMMENT '代理商ID',
  `customer_id` int(11) DEFAULT NULL COMMENT '客户ID',
  `active_time` datetime DEFAULT NULL COMMENT '激活时间',
  `used_time` datetime DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`id`),
  KEY `agent_id` (`agent_id`),
  KEY `customer_id` (`customer_id`),
  KEY `type` (`type`),
  CONSTRAINT `card_ibfk_1` FOREIGN KEY (`agent_id`) REFERENCES `agent` (`id`),
  CONSTRAINT `card_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `card_ibfk_3` FOREIGN KEY (`type`) REFERENCES `card_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保险卡片信息表';

/*Table structure for table `card_type` */

DROP TABLE IF EXISTS `card_type`;

CREATE TABLE `card_type` (
  `id` tinyint(4) NOT NULL COMMENT '主键ID',
  `name` varchar(10) NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='卡片类型表';

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户账户主键ID',
  `id_number` varchar(18) NOT NULL COMMENT '身份证号',
  `wx_id` varchar(32) NOT NULL COMMENT '微信OPENID',
  `phone_number` varchar(11) NOT NULL COMMENT '手机号',
  `name` varchar(10) NOT NULL COMMENT '姓名',
  `nick_name` varchar(10) DEFAULT NULL COMMENT '昵称',
  `address` varchar(30) DEFAULT NULL COMMENT '地址',
  `bind_state` bit(1) NOT NULL DEFAULT b'1' COMMENT '绑定状态1：绑定，0：解绑',
  `bind_time` datetime DEFAULT NULL COMMENT '绑定时间',
  `unbind_time` datetime DEFAULT NULL COMMENT '解绑时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='客户账户表';

/*Table structure for table `export_history` */

DROP TABLE IF EXISTS `export_history`;

CREATE TABLE `export_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `policy_id` int(11) NOT NULL COMMENT '保单ID',
  `export_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '导出时间',
  PRIMARY KEY (`id`),
  KEY `policy_id` (`policy_id`),
  CONSTRAINT `export_history_ibfk_1` FOREIGN KEY (`policy_id`) REFERENCES `policy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保险单导出记录表';

/*Table structure for table `joinin` */

DROP TABLE IF EXISTS `joinin`;

CREATE TABLE `joinin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '加盟商主键ID',
  `wx_id` varchar(32) DEFAULT NULL COMMENT '微信openid',
  `phone_number` varchar(11) NOT NULL COMMENT '手机号',
  `name` varchar(10) NOT NULL COMMENT '姓名',
  `apply_type` tinyint(4) NOT NULL COMMENT '申请绑定类型默认0',
  `state` tinyint(4) NOT NULL COMMENT '状态0：申请，1：已绑定，2：解绑',
  `create_type` tinyint(4) NOT NULL COMMENT '创建类型0：加盟商申请，1：管理员添加',
  `create_time` datetime NOT NULL COMMENT '记录创建时间',
  `bind_time` datetime DEFAULT NULL COMMENT '绑定时间',
  `unbind_time` datetime DEFAULT NULL COMMENT '解绑时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加盟商账户表';

/*Table structure for table `policy` */

DROP TABLE IF EXISTS `policy`;

CREATE TABLE `policy` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '保单主键ID',
  `card_id` int(11) NOT NULL COMMENT '卡片ID',
  `holder` varchar(10) NOT NULL COMMENT '投保人姓名',
  `recognizee` varchar(10) NOT NULL COMMENT '被保人姓名',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '中止时间',
  `export_state` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否导出',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录插入时间',
  PRIMARY KEY (`id`),
  KEY `card_id` (`card_id`),
  CONSTRAINT `policy_ibfk_1` FOREIGN KEY (`card_id`) REFERENCES `card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保单信息表';

/*Table structure for table `shop` */

DROP TABLE IF EXISTS `shop`;

CREATE TABLE `shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺主键ID',
  `name` varchar(10) NOT NULL COMMENT '店铺名称',
  `url` varchar(100) NOT NULL COMMENT '外部链接',
  `description` varchar(200) DEFAULT NULL COMMENT '店铺说明',
  `type` tinyint(4) NOT NULL COMMENT '店铺类型',
  `joinin_id` int(11) NOT NULL COMMENT '加盟商ID',
  `area_id` int(11) DEFAULT NULL COMMENT '所属区域ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  PRIMARY KEY (`id`),
  KEY `type` (`type`),
  KEY `joinin_id` (`joinin_id`),
  CONSTRAINT `shop_ibfk_1` FOREIGN KEY (`type`) REFERENCES `shop_type` (`id`),
  CONSTRAINT `shop_ibfk_2` FOREIGN KEY (`joinin_id`) REFERENCES `joinin` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家店铺信息表';

/*Table structure for table `shop_type` */

DROP TABLE IF EXISTS `shop_type`;

CREATE TABLE `shop_type` (
  `id` tinyint(4) NOT NULL COMMENT '店铺类型ID',
  `name` varchar(10) DEFAULT NULL COMMENT '店铺类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺类型表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
