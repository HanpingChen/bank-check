package com.cmb.bankcheck.message;

import java.util.List;

/**
 * created by chenhanping
 * Designer:chenhanping
 * Date:2019-07-26
 * Time:10:40
 * 响应信息封装类
 * T是代表返回的数据类型，一般也是一个封装类
 */
public class ResponseMessage<T> extends Message{

    private List<T> data;

    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String s1 = "status:"+this.getStatus() +"\n";
        stringBuilder.append(s1);
        String s2 = "msg:"+this.getMsg()+"\n";
        stringBuilder.append(s2);
        for(T d:this.getData()){
            String temp = d.toString()+"\n";
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }
}
