package org.num.serice.impl;

import org.num.entity.Wt;
import org.num.serice.IWtSerice;

import com.alibaba.fastjson.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zxk
 * @date 2017-11-26 15:42
 */
public class WtSericeImpl implements IWtSerice {

    public String findAll() {
        List<Wt> wlist = new ArrayList<Wt>();
        Wt w = new Wt();
        w.setId(1);
        w.setYears(2017);
        w.setMonths(11);
        w.setDays(27);

        Wt w1 = new Wt();
        w1.setId(2);
        w1.setYears(2016);
        w1.setMonths(11);
        w1.setDays(27);

        Wt w2 = new Wt();
        w2.setId(2);
        w2.setYears(2015);
        w2.setMonths(11);
        w2.setDays(27);
        wlist.add(w);
        wlist.add(w1);
        wlist.add(w2);
        return JSON.toJSONString(wlist);
    }
}
