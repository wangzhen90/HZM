package com.hengze.hengzemanager.modle;



import java.util.List;

/**
 * Created by dell on 2016/2/15.
 */
public class AddressNode {
    public String id ;
    public String name;
    public String father_id;
    public int level;
    public List<AddressNode> children;

    public void addChild(AddressNode node){
        this.children.add(node);
    }
}
