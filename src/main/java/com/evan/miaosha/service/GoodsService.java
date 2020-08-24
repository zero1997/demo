package com.evan.miaosha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evan.miaosha.dao.GoodsDao;
import com.evan.miaosha.domain.Goods;
import com.evan.miaosha.domain.MiaoshaGoods;
import com.evan.miaosha.vo.GoodsVo;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-08-04
 */
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }


    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        System.out.println(goodsDao.getGoodsVoByGoodsId(goodsId).toString());
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goods) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        int ret = goodsDao.reduceStock(g);
        return ret > 0;
    }
}
