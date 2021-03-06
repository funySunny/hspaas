package com.huashi.exchanger.domain;

import java.io.Serializable;

/**
 * TODO 调用厂商发送接口返回信息
 * 
 * @author zhengying
 * @version V1.0
 * @date 2016年9月28日 下午6:10:26
 */
public class ProviderSendResponse implements Serializable {

    private static final long serialVersionUID = -2875315786851717616L;

    /**
     * 消息ID
     */
    private String            sid;

    /**
     * 手机号码
     */
    private String            mobile;

    /**
     * 状态码
     */
    private String            statusCode;

    /**
     * 发送时间
     */
    private String            sendTime;

    /**
     * 是否成功
     */
    private boolean           isSuccess;

    /**
     * 备注信息（一般存报文详情）
     */
    private String            remark;

    /**
     * 通道代码
     */
    private String            passageCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ProviderSendResponse [sid=" + sid + ", mobile=" + mobile + ", statusCode=" + statusCode + ", sendTime="
               + sendTime + ", isSuccess=" + isSuccess + ", remark=" + remark + "]";
    }

    public String getPassageCode() {
        return passageCode;
    }

    public void setPassageCode(String passageCode) {
        this.passageCode = passageCode;
    }

}
