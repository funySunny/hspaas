package com.huashi.mms.task.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;

import com.alibaba.druid.util.StringUtils;
import com.huashi.common.third.model.MobileCatagory;
import com.huashi.mms.passage.domain.MmsPassageAccess;

/**
 * TODO 用户路由通道信息
 * 
 * @author zhengying
 * @version V1.0
 * @date 2016年10月11日 上午11:57:29
 */
public class MmsRoutePassage implements Serializable {

    private static final long              serialVersionUID = 988928139119189884L;

    private Integer                        userId;

    /**
     * 通道手机号码信息（手机号码以逗号分割）
     */
    private Map<Integer, String>           passageMobiles   = new HashMap<>();

    /**
     * 未找到对应通道手机号码集合
     */
    private Set<String>                    unkonwnMobiles   = new HashSet<>();

    /**
     * 通道信息
     */
    private Map<Integer, MmsPassageAccess> passaegAccesses  = new HashMap<>();

    /**
     * 错误信息
     */
    private String                         errorMessage;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Map<Integer, String> getPassageMobiles() {
        return passageMobiles;
    }

    public void setPassageMobiles(Map<Integer, String> passageMobiles) {
        this.passageMobiles = passageMobiles;
    }

    public Map<Integer, MmsPassageAccess> getPassaegAccesses() {
        return passaegAccesses;
    }

    public void setPassaegAccesses(Map<Integer, MmsPassageAccess> passaegAccesses) {
        this.passaegAccesses = passaegAccesses;
    }

    /**
     * TODO 加入通道ID和手机号码对应关系(即同一个通道下面拥有手机号码集合信息)
     * 
     * @param cmcp 运营商
     * @param passageId
     * @param mobiles
     */
    public void addPassageMobilesMapping(Integer passageId, String mobiles) {
        if (MapUtils.isEmpty(passageMobiles) || !passageMobiles.containsKey(passageId)) {
            passageMobiles.put(passageId, mobiles);
            return;
        }

        // 拼接手机号码
        passageMobiles.put(passageId, passageMobiles.get(passageId) + MobileCatagory.MOBILE_SPLIT_CHARCATOR + mobiles);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Set<String> getUnkonwnMobiles() {
        return unkonwnMobiles;
    }

    public void setUnkonwnMobiles(Set<String> unkonwnMobiles) {
        this.unkonwnMobiles = unkonwnMobiles;
    }

    public void addUnknownMobiles(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return;
        }

        this.unkonwnMobiles.addAll(Arrays.asList(mobile.split(MobileCatagory.MOBILE_SPLIT_CHARCATOR)));
    }

}
