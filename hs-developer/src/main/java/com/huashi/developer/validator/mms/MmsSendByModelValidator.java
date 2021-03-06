package com.huashi.developer.validator.mms;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.dubbo.config.annotation.Reference;
import com.huashi.common.user.service.IUserBalanceService;
import com.huashi.constants.CommonContext.PlatformType;
import com.huashi.constants.OpenApiCode.CommonApiCode;
import com.huashi.constants.OpenApiCode.MmsApiCode;
import com.huashi.developer.exception.ValidateException;
import com.huashi.developer.prervice.MmsPrervice;
import com.huashi.developer.request.AuthorizationRequest;
import com.huashi.developer.request.mms.MmsSendByModelRequest;
import com.huashi.developer.validator.AuthorizationValidator;
import com.huashi.developer.validator.Validator;
import com.huashi.mms.template.service.IMmsTemplateService;

@Component
public class MmsSendByModelValidator extends Validator {

    @Autowired
    private AuthorizationValidator   passportValidator;
    @Reference
    private IUserBalanceService userBalanceService;
    @Reference
    private IMmsTemplateService mmsTemplateService;

    /**
     * TODO 用户参数完整性校验
     * 
     * @param paramMap
     * @param ip
     * @return
     * @throws ValidateException
     */
    public MmsSendByModelRequest validate(Map<String, String[]> paramMap, String ip) throws ValidateException {
        MmsSendByModelRequest mmsModelSendRequest = new MmsSendByModelRequest();
        validateAndParseFields(mmsModelSendRequest, paramMap);

        // 获取授权通行证实体
        AuthorizationRequest passportModel = passportValidator.validate(paramMap, ip, mmsModelSendRequest.getMobile());

        mmsModelSendRequest.setIp(ip);
        mmsModelSendRequest.setUserId(passportModel.getUserId());

        // 校验用户短信余额是否满足
        checkBalanceAvaiable(mmsModelSendRequest, passportModel);

        // 校验模板ID信息
        checkModelIdIsAvaiable(mmsModelSendRequest);
        
        // 暂时先不加
//        isBeyondMobileSize(mmsModelSendRequest.getMobile().split(MobileCatagory.MOBILE_SPLIT_CHARCATOR).length);

        return mmsModelSendRequest;
    }

    /**
     * TODO 检验模板ID是否可用
     * 
     * @param mmsModelSendRequest
     */
    private void checkModelIdIsAvaiable(MmsSendByModelRequest mmsModelSendRequest) throws ValidateException {
        boolean isMmsTemplateAvaiable = mmsTemplateService.isModelIdAvaiable(mmsModelSendRequest.getModelId(),
                                                                             mmsModelSendRequest.getUserId());
        if (!isMmsTemplateAvaiable) {
            throw new ValidateException(MmsApiCode.MMS_MODEL_ID_UNDEFAINED);
        }
    }

    /**
     * TODO 验证签名（携带手机号码签名模式）
     * 
     * @param mmsModelSendRequests
     * @param passportModel
     * @return
     * @throws ValidateException
     */
    private void checkBalanceAvaiable(MmsSendByModelRequest mmsSendByModelRequest, AuthorizationRequest authorizationRequest)
                                                                                                           throws ValidateException {
        // 此处需加入是否为后付款，如果为后付则不需判断余额
        // f.用户余额不足（通过计费微服务判断，结合4.1.6中的用户计费规则）
        boolean balanceEnough = userBalanceService.isBalanceEnough(authorizationRequest.getUserId(),
                                                                   PlatformType.MULTIMEDIA_MESSAGE_SERVICE,
                                                                   MmsPrervice.DEFAULT_FEE_IN_SINGLE.doubleValue());
        if (!balanceEnough) {
            throw new ValidateException(CommonApiCode.COMMON_BALANCE_NOT_ENOUGH);
        }

        mmsSendByModelRequest.setFee(MmsPrervice.DEFAULT_FEE_IN_SINGLE);
        mmsSendByModelRequest.setTotalFee(MmsPrervice.DEFAULT_FEE_IN_SINGLE);
    }

}
