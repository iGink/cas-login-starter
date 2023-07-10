package top.gink.cas.service;

import top.gink.cas.model.CasResp;

public interface CasReqService {
    CasResp serviceValidate(String ticket);
}
