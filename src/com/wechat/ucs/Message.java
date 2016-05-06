package com.wechat.ucs;


public class Message {

    private static final long serialVersionUID = 6381955587455415786L;
    // ========== Common ==========
    private String accessType; // 接入类型(消息类别，短信：sms 微信：wechat 易信：credulity 转分配：assign 邮件：email)
    private String content; // 消息内容(短信：短信内容，微信/易信：文本内容)
    private String sender; // 消息发送者
    private String receiver;// 消息接收者
    private String status; // 消息状态（0：未处理、1：处理中、2：已处理）
    private String realSendTime; // 实际发送时间
    private String appId; // 公共账号唯一标识
    private String buttonKey; // 微信菜单点击事件key值

    // 保留字段
    // 坐席分配
    private String sourceAgentId; // 源坐席编号
    private String destAgentId; // 目标坐席编号
    private String source; // 消息来源 （短信、微信等）
    private String destination; // 消息发送目标 （发送渠道：短信、微信等）
    private String expireTime; // 超期时间
    // 消息重发
    private Integer sendTimes; // 发送次数
    private String lastSendTime; // 最后发送时间

    // ========== 短信 ==========
    private String scheduleBeginTime; // 计划开始时间 (可为空，短信默认为早8晚8)
    private String scheduleEndTime; // 计划结束时间 (可为空，短信默认为早8晚8)

    // ========== 微信 ==========
    private String showName; // 微信显示用户名
   
    private String messageType; // 消息类型（文本：text 图片：image 语音：voice 视频：video
                                // 地理位置：location 链接：link 图文消息：news 音乐：music）
    // 图片
    private String picUrl; // 图片链接
    private String mediaId; // 消息媒体id（图片、语音、视频类型消息） 

    // 保留字段
    // voice 和 video
    private String format; // 语音格式，如amr，speex等
    private String thumbMediaId; // 视频消息缩略图的媒体id
    // location
    private String location_X; // 地理位置维度
    private String location_Y; // 地理位置经度
    private String scale; // 地图缩放大小
    private String label; // 地理位置信息
    // link
    private String title; // 消息标题
    private String description; // 消息描述
    private String url; // 消息链接

    // 新增
    private String direction; // 0：接收  1：发送
    private String localPicUrl; // 本地图片路径
    private int countMsg;
    private String customerId;
    private String customerFlag;

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRealSendTime() {
        return realSendTime;
    }

    public void setRealSendTime(String realSendTime) {
        this.realSendTime = realSendTime;
    }

    public String getSourceAgentId() {
        return sourceAgentId;
    }

    public void setSourceAgentId(String sourceAgentId) {
        this.sourceAgentId = sourceAgentId;
    }

    public String getDestAgentId() {
        return destAgentId;
    }

    public void setDestAgentId(String destAgentId) {
        this.destAgentId = destAgentId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(Integer sendTimes) {
        this.sendTimes = sendTimes;
    }

    public String getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(String lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    public String getScheduleBeginTime() {
        return scheduleBeginTime;
    }

    public void setScheduleBeginTime(String scheduleBeginTime) {
        this.scheduleBeginTime = scheduleBeginTime;
    }

    public String getScheduleEndTime() {
        return scheduleEndTime;
    }

    public void setScheduleEndTime(String scheduleEndTime) {
        this.scheduleEndTime = scheduleEndTime;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getLocation_X() {
        return location_X;
    }

    public void setLocation_X(String location_X) {
        this.location_X = location_X;
    }

    public String getLocation_Y() {
        return location_Y;
    }

    public void setLocation_Y(String location_Y) {
        this.location_Y = location_Y;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getLocalPicUrl() {
        return localPicUrl;
    }

    public void setLocalPicUrl(String localPicUrl) {
        this.localPicUrl = localPicUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getButtonKey() {
        return buttonKey;
    }

    public void setButtonKey(String buttonKey) {
        this.buttonKey = buttonKey;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFlag() {
        return customerFlag;
    }

    public void setCustomerFlag(String customerFlag) {
        this.customerFlag = customerFlag;
    }

    public int getCountMsg() {
        return countMsg;
    }

    public void setCountMsg(int countMsg) {
        this.countMsg = countMsg;
    }
    
}
