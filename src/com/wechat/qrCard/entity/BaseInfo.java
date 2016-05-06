package com.wechat.qrCard.entity;

import java.io.Serializable;

public class BaseInfo implements Serializable {

	private static final long serialVersionUID = -7201502812082281829L;

	private String logo_url; // 卡券的商户 logo，尺寸为 300*300

	/**
	 * "CODE_TYPE_TEXT"，文本 "CODE_TYPE_BARCODE"，一 维码 "CODE_TYPE_QRCODE"，二 维码；，
	 * “CODE_TYPE_ONLY_QRCODE”,二维码无 code 显示 “CODE_TYPE_ONLY_BARCODE”,一维码无 code
	 * 显示
	 */
	private String code_type; // code 码展示类型。

	/**
	 * （填写直接提供服务的商户 名 ， 第 三 方 商 户 名 填 写 在 source 字段）
	 */
	private String brand_name;// 商户名字,字数上限为 12 个汉 字。

	private String title; // 券名，字数上限为 9 个汉字。(建 议涵盖卡券属性、服务及金额)

	private String sub_title;// 券名的副标题，字数上限为 18个汉字。

	private String color; // 券颜色。按色彩规范标注填写 Color010-Color100

	/**
	 * （一句话描述，展示在首页， 示例：请出示二维码核销卡券）
	 */
	private String notice;// 使用提醒，字数上限为 9 个汉 字。

	private String service_phone;// 客服电话

	private String source; // 第三方来源名，例如同程旅游 格瓦拉

	private String description;// 使用说明。长文本描述，可以分 行，上限为 1000 个汉字。

	private int get_limit;// 每人最大领取次数，不填写默认等于 quantity。

	private boolean use_custom_code;// 是否自定义 code 码。填写 true 或 false，不填代表默认为
									// false
	private boolean bind_openid;// 是否指定用户领取，填写 true 或 false。不填代表默认为否

	private boolean can_share; // 领取卡券原生页面是否可分享， 填写 true 或 false，true 代表
								// 可分享。默认可分享

	private boolean can_give_friend;// 卡券是否可转赠，填写 true 或 false,true 代表可转赠。默认可
									// 转赠。

	// private String location_id_list;// 门店位置 ID。商户需在 mp 平 台上录入门店信息或调用批量
	// // 导入门店信息接口获取门店位 置 ID。

	private DateInfo date_info;// 使用日期，有效期的信息

	private Sku sku;// 商品信息

	private String url_name_type;
	private String custom_url;

	public String getUrl_name_type() {
		return url_name_type;
	}

	public void setUrl_name_type(String url_name_type) {
		this.url_name_type = url_name_type;
	}

	public String getCustom_url() {
		return custom_url;
	}

	public void setCustom_url(String custom_url) {
		this.custom_url = custom_url;
	}

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public String getCode_type() {
		return code_type;
	}

	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSub_title() {
		return sub_title;
	}

	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getService_phone() {
		return service_phone;
	}

	public void setService_phone(String service_phone) {
		this.service_phone = service_phone;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getGet_limit() {
		return get_limit;
	}

	public void setGet_limit(int get_limit) {
		this.get_limit = get_limit;
	}

	public boolean isUse_custom_code() {
		return use_custom_code;
	}

	public void setUse_custom_code(boolean use_custom_code) {
		this.use_custom_code = use_custom_code;
	}

	public boolean isBind_openid() {
		return bind_openid;
	}

	public void setBind_openid(boolean bind_openid) {
		this.bind_openid = bind_openid;
	}

	public boolean isCan_share() {
		return can_share;
	}

	public void setCan_share(boolean can_share) {
		this.can_share = can_share;
	}

	public boolean isCan_give_friend() {
		return can_give_friend;
	}

	public void setCan_give_friend(boolean can_give_friend) {
		this.can_give_friend = can_give_friend;
	}

	// public String getLocation_id_list() {
	// return location_id_list;
	// }
	//
	// public void setLocation_id_list(String location_id_list) {
	// this.location_id_list = location_id_list;
	// }

	public DateInfo getDate_info() {
		return date_info;
	}

	public void setDate_info(DateInfo date_info) {
		this.date_info = date_info;
	}

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

}
// logo_url
// 卡券的商户 logo，尺寸为
// 300*300。
// 是
// code_type
// code 码展示类型。
// 是
// "CODE_TYPE_TEXT"，文本
// "CODE_TYPE_BARCODE"，一
// 维码
// "CODE_TYPE_QRCODE"，二
// 维码；，
// “CODE_TYPE_ONLY_QRCO
// DE”,二维码无 code 显示
// “CODE_TYPE_ONLY_BARC
// ODE”,一维码无 code 显示
// 是
// brand_name
// 商户名字,字数上限为 12 个汉
// 字。（填写直接提供服务的商户
// 名 ， 第 三 方 商 户 名 填 写 在
// source 字段）
// 是
// title
// 券名，字数上限为 9 个汉字。(建
// 议涵盖卡券属性、服务及金额)
// 是
// sub_title
// 券名的副标题，字数上限为 18
// 个汉字。
// 否
// color
// 券颜色。按色彩规范标注填写
// Color010-Color100
// 是
// notice
// 使用提醒，字数上限为 9 个汉
// 字。（一句话描述，展示在首页，
// 示例：请出示二维码核销卡券）
// 是
// service_phone
// 客服电话。
// 否
// source
// 第三方来源名，例如同程旅游、
// 格瓦拉。
// 否
// description
// 使用说明。长文本描述，可以分
// 行，上限为 1000 个汉字。
// 是
// get_limit
// 每人最大领取次数，不填写默认
// 等于 quantity。
// 否
// use_custom_code
// 是否自定义 code 码。填写 true
// 或 false，不填代表默认为
// false。
// 否
// bind_openid
// 是否指定用户领取，填写 true
// 或 false。不填代表默认为否。
// 否
// can_share
// 领取卡券原生页面是否可分享，
// 填写 true 或 false，true 代表
// 可分享。默认可分享。
// 否
// 9
// can_give_friend
// 卡券是否可转赠，填写 true 或
// false,true 代表可转赠。默认可
// 转赠。
// 否
// location_id_list
// 门店位置 ID。商户需在 mp 平
// 台上录入门店信息或调用批量
// 导入门店信息接口获取门店位
// 置 ID。
// 否
// date_info
// 使用日期，有效期的信息
// 是
// type
// 使用时间的类型
// 是
// 1：固定日期区间，2：固定时
// 长（自领取后按天算）
// 是
// begin_timesta
// mp
// 固定日期区间专用，表示起用时
// 间 。 从 1970 年 1 月 1 日
// 00:00:00 至起用时间的秒数，
// 最终需转换为字符串形态传入，
// 下同。（单位为秒）
// 是
// end_timestamp
// 固定日期区间专用，表示结束时
// 间。（单位为秒）
// 是
// fixed_term
// 固定时长专用，表示自领取后多
// 少天内有效。（单位为天）
// 是
// fixed_begin_ter
// m
// 固定时长专用，表示自领取后多
// 少天开始生效。（单位为天）
// 是
// sku
// 商品信息。
// 是
// quantity
// 上架的数量。（不支持填写 0
// 或无限大）
// 是
// url_name_type
// 商户自定义 cell 名称
// 否
// 10
// 外卖：
// URL_NAME_TYPE_TAKE_AW
// AY
// 在线预订：
// URL_NAME_TYPE_RESERVA
// TION
// 立即使用
// URL_NAME_TYPE_USE_IMM
// EDIATELY
// 在线预约
// URL_NAME_TYPE_APPOINT
// MENT
// 在线兑换
// URL_NAME_TYPE_EXCHAN
// GE
// 会员服务
// URL_NAME_TYPE_VIP_SERVI
// CE
// 会员尊享
// URL_NAME_TYPE_VIP_PRIVI
// LEGE
// 航班动态
// URL_NAME_TYPE_FLIGHT_D
// YNAMIC
// （与 custom_url 字段共同使
// 用）
// 否
// custom_url
// 商户自定义 url 地址，支持卡券
// 页内跳转,跳转页面内容需与自
// 定义 cell 名称保持一致。
// 否
// promotion_url_na
// me_type
// 特殊权限自定义 cell
// 否
// 11
// 再团一次
// URL_NAME_TYPE_BUY_GRO
// UPON_AGAIN
// 购买
// URL_NAME_TYPE_PURCHAS
// E
// 再次购买
// URL_NAME_TYPE_PURCHAS
// E_AGAIN
// （权限需单独开通，与
// promotion_url 字段共同使用）
// promotion_url
// 特殊权限自定义 url 地址，支持
// 卡券页内跳转,跳转页面内容需
// 与 cell 名称保持一致。（权限
// 需单独开通）