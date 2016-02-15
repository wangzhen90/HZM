package com.hengze.hengzemanager.modle;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;
import android.util.Log;

/**
 * Created by dell on 2016/2/15.
 */
public class AddressTree {

    private ArrayList<AddressNode> nodeList;

    private Map<String,AddressNode> guanqu = new HashMap<String,AddressNode>();
    private Map<String,AddressNode> zhen = new HashMap<String,AddressNode>();

    public AddressNode xianNode;

    public AddressTree(ArrayList<AddressNode> nodeList){
        this.nodeList = nodeList;
        this.buildTree();
    }

    private void buildTree(){
        xianNode = null;
        guanqu.clear();
        zhen.clear();
        for(int i = 0; i < nodeList.size(); i++){
            AddressNode node = nodeList.get(i);
            if("0".equals(node.father_id)){
                xianNode = node;
//                xian.put(node.id, node);//县
            }else{
                if(xianNode == null) return;

                if(xianNode.id.equals(node.father_id)){
//                    AddressNode parentNode = xian.get(node.father_id);//县
                    xianNode.addChild(node);//镇
                    guanqu.put(node.id,node);
                }else if(guanqu.containsKey(node.father_id)){
                    AddressNode parentNode = guanqu.get(node.father_id);//镇
                    parentNode.addChild(node);//灌区
                    zhen.put(node.id, node);
                }else if(zhen.containsKey(node.father_id)){
                    AddressNode parentNode = zhen.get(node.father_id);//灌区
                    parentNode.addChild(node);//村
                }
            }
        }

//      Iterator<Map.Entry<String,AddressNode>> iterator =  xian.entrySet().iterator();

//      while (iterator.hasNext()){
//          Map.Entry<String,AddressNode> entry = (Map.Entry) iterator.next();

//        AddressNode value =  entry.getValue();
//          Log.e("tree","县:"+value.name);
//        if(xianNode.children.size() > 0){
//
//            for(AddressNode child : xianNode.children){
//                Log.e("tree","灌区:"+child.name);
//                for(AddressNode node : child.children){
//                    Log.e("tree","镇:"+node.name);
//                    for(AddressNode n : node.children){
//                        Log.e("tree","村:"+n.name);
//                    }
//                }
//            }
//        }
    }

}
