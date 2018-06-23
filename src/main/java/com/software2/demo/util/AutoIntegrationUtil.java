package com.software2.demo.util;

import com.baidu.aip.nlp.AipNlp;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/*
自动化整合
输入一定数量的信息：用户id（方便修改用户能力值）
                    用户准确率（作为权重进行评估）
                    标注内容（这个。。。没什么好说的吧）
输入完成后使用run函数即可获得结果
不可通过getUsers获得userid和新能力值的hash表（由于能力值被改为准确率，该功能被关闭）
通过isneed_human_judge获得是否需要人工评估（最终答案权重低于总权重的60%则需要人工评估）
通过get_label可获得最终标注结果
 */
public class AutoIntegrationUtil {
    private double standard = 0.6;
    private HashMap<String,Double> answers = new HashMap<String, Double>();//答案及其价值
    private List<relation> list = new ArrayList<>();
    private String answer = "";
    private boolean need_human_judge = false;
    private AipNlp client = Client.getClient();
    // 传入可选参数调用接口
    private HashMap<String, Object> options = new HashMap<String, Object>();
    public AutoIntegrationUtil(){
        options.put("model", "CNN");
        answers = new HashMap<String, Double>();//答案及其价值
        list = new ArrayList<>();
    }
    public void input(String id,String label,double ability){
        relation r = new relation();
        r.setAbility(ability);
        r.setLabel(label);
        r.setUserID(id);
        list.add(r);
    }
    //进行聚类(填充answer属性)
    public void cluster(){
        int size = list.size();
        for(int i = 0;i<size-1;i++){
            for(int j = i+1;j<size;j++){
                relation a1 = list.get(i);
                relation b1 = list.get(j);
                String a,b;
                if(a1.isHas_answer()){
                    a = a1.getAnswer();
                }
                else
                    a = a1.getLabel();

                if(b1.isHas_answer()){
                    b = b1.getAnswer();
                }
                else
                    b = b1.getLabel();
                if(calculate_alike(a,b)>standard){
                    if(a.length()>b.length()){
                        a1.setAnswer(b);
                        b1.setAnswer(b);
                    }else{

                        a1.setAnswer(a);
                        b1.setAnswer(a);
                    }
                    a1.setHas_answer(true);
                    b1.setHas_answer(true);
                }
            }
        }
    }

    public double calculate_alike(String a,String b){
        // 短文本相似度
        JSONObject res = client.simnet(a, b, options);
        System.out.println(res);
        return res.getDouble("score");
    }
    public void run(){
        cluster();
        judge_data();
        //change_ability(answer);
    }
    //处理数据
    public void judge_data(){
        String label;
        double ability;
        for(relation r:list){
            label = r.getAnswer();
            //ability = r.getAbility();
            ability = 1;
            if(!answers.keySet().contains(label)){
                answers.put(label,ability);
            }else{
                double temp = answers.get(label)+ability;
                answers.put(label,temp);
            }
        }
        String temp_answer = null;
        double max = 0;
        double sum = 0;
        //得到价值最大标注
        for(String s:answers.keySet()){
            if(temp_answer==null){temp_answer = s;max = answers.get(s);}
            else if(answers.get(s)>max){
                temp_answer = s;
                max = answers.get(s);
            }
            sum += answers.get(s);
        }
        answer = temp_answer;
        //如果一样则判断是否需要人工评估
        if(answers.get(answer)<=sum*0.6){
            need_human_judge = true;
        }
    }
    //修改能力值作为权重
//    public void change_ability(String s){
//        for(relation r : list){
//            if(r.getLabel().equals(s)){
//                r.setAbility(r.getAbility()+1);
//            }else{
//                r.setAbility(r.getAbility()-1);
//            }
//        }
//    }

    public HashMap<String, Double> getUsers() {
        HashMap<String,Double> users = new HashMap<>();
        for(relation r:list){
            users.put(r.getUserID(),r.getAbility());
        }
        return users;
    }

    public String get_label(){
        return answer;
    }
    public boolean isNeed_human_judge(){
        return need_human_judge;
    }
}


class relation {
    String userID;
    double ability;
    String label;
    String answer;
    boolean has_answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isHas_answer() {
        return has_answer;
    }

    public void setHas_answer(boolean has_answer) {
        this.has_answer = has_answer;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getAbility() {
        return ability;
    }

    public void setAbility(double ability) {
        this.ability = ability;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
