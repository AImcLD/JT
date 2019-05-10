package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

//创建商品对象  全部属性定义使用包装类型
@Data
@Accessors(chain = true)
@TableName("tb_item")
public class Item extends BasePojo{
	@TableId(type = IdType.AUTO)
	private Long id;				//商品id号
	private String title;			//商品标题信息
	private String sellPoint;		//商品卖点信息
	private Long   price;			//保存数据时扩大100倍,展现时缩小100倍//double有精度问题. double计算速度慢
	private Integer num;			//商品数量
	private String barcode;			//条形码
	private String image;			//保存图片url地址 a.jpg,b.jpg,c.jpg
	private Long   cid;				//商品分类信息
	private Integer status;			//1正常，2下架，3删除
}
