package com.szl.fed_platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szl.fed_platform.entity.Dataset;
import com.szl.fed_platform.mapper.DatasetMapper;
import com.szl.fed_platform.service.DatasetService;
import org.springframework.stereotype.Service;

@Service
public class DatasetServiceImpl extends ServiceImpl<DatasetMapper, Dataset> implements DatasetService {

    DatasetMapper datasetMapper;

    public DatasetServiceImpl(DatasetMapper datasetMapper) {
        this.datasetMapper = datasetMapper;
    }

    public String getNameById(int id) {
        Dataset dataset = datasetMapper.selectById(id); // MyBatis-Plus 提供的 selectById 方法
        return dataset != null ? dataset.getName() : "Unknown"; // 如果查询不到，返回 "Unknown"
    }
}
