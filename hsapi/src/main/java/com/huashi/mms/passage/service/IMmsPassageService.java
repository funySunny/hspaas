package com.huashi.mms.passage.service;

import java.util.List;
import java.util.Map;

import com.huashi.common.vo.BossPaginationVo;
import com.huashi.mms.passage.domain.MmsPassage;
import com.huashi.mms.passage.domain.MmsPassageProvince;

/**
 * TODO 彩信通道服务
 * 
 * @author zhengying
 * @version V1.0
 * @date 2019年1月14日 下午4:00:47
 */
public interface IMmsPassageService {

    /**
     * 添加通道
     * 
     * @param passage
     * @param provinceCodes
     * @return
     */
    Map<String, Object> add(MmsPassage passage, String provinceCodes);

    /**
     * 修改通道
     * 
     * @param passage
     * @param provinceCodes
     * @return
     */
    Map<String, Object> update(MmsPassage passage, String provinceCodes);

    /**
     * 删除通道
     * 
     * @param id
     * @return
     */
    boolean deleteById(int id);

    /**
     * TODO 分页查询通道信息
     * 
     * @param pageNum
     * @param keyword
     * @return
     */
    BossPaginationVo<MmsPassage> findPage(int pageNum, String keyword);

    List<MmsPassage> findAll();

    /**
     * TODO 根据通道ID获取通道详细信息
     * 
     * @param id
     * @return
     */
    MmsPassage findById(int id);

    /**
     * TODO 根据通道组ID获取所有通道信息
     *
     * @param groupId
     * @return
     */
    List<MmsPassage> findByGroupId(int groupId);

    /**
     * TODO 获取最好的可用通道信息，结合优先级，通道状态等因素
     *
     * @param groupId
     * @return
     */
    MmsPassage getBestAvaiable(int groupId);

    /**
     * TODO 禁用或激活通道
     * 
     * @param id 通道ID
     * @param flag 状态标识
     * @return
     */
    boolean disabledOrActive(int id, int flag);

    /**
     * TODO 根据运营商查询通道信息
     * 
     * @param cmcp
     * @return
     */
    List<MmsPassage> getByCmcp(int cmcp);

    /**
     * 根据运营商、路由类型、状态查询全部可用通道组下面的通道
     * 
     * @param groupId 通道组id
     * @param cmcp 运营商
     * @param routeType 路由类型
     * @return
     */
    List<MmsPassage> findAccessPassages(int groupId, int cmcp, int routeType);

    List<MmsPassage> findByCmcpOrAll(int cmcp);

    /**
     * TODO 加载到REDIS
     * 
     * @return
     */
    boolean reloadToRedis();

    /**
     * 根据通道ID获取省份通道信息
     * 
     * @param passageId
     * @return
     */
    List<MmsPassageProvince> getPassageProvinceById(Integer passageId);

    /**
     * 根据省份代码和运营商获取通道信息
     * 
     * @param provinceCode
     * @param cmcp
     * @return
     */
    List<MmsPassage> getByProvinceAndCmcp(Integer provinceCode, int cmcp);

    /**
     * TODO 查询所有有效的通道代码信息
     * 
     * @return
     */
    List<String> findPassageCodes();

    /**
     * TODO 监控告警短信(专指发送配置系统配置中的告警手机号码，内部使用)
     * 
     * @param mobile
     * @param content
     * @return
     */
    boolean doMonitorMmsSend(String mobile, String content);

    /**
     * TODO 获取通道消息队列消费者数量
     * 
     * @param protocol 协议类型
     * @param passageCode 通道代码
     * @return
     */
    boolean isPassageBelongtoDirect(String protocol, String passageCode);

}
