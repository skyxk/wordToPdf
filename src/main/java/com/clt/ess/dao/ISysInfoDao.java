package com.clt.ess.dao;

import com.clt.ess.entity.SysInfo;

public interface ISysInfoDao {
    SysInfo findSysInfoById(String sysid);
}
