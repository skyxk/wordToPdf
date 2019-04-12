package com.clt.ess.dao;

import com.clt.ess.entity.ConvertLog;

import java.util.List;

public interface IConvertLogDao {
    int addConvertLog(ConvertLog convertLog);
    ConvertLog findConvertLogById(int fid);

    ConvertLog findConvertLogByState();

    int updateConvertLog(ConvertLog c);
}
