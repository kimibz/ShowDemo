package com.xigua.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xigua.dao.ManageVirtualUsrDao;
import com.xigua.model.ManageVirtualUsr;
import com.xigua.service.UserMangeService;
@Service
public class UserMangeServiceImpl implements UserMangeService{
    
    @Autowired
    private ManageVirtualUsrDao Dao;
    @Override
    public List<ManageVirtualUsr> getAllDevice(String username) {
        // TODO Auto-generated method stub
        List<ManageVirtualUsr> list = Dao.getVirtualList(username);
        return list;
    }

}
