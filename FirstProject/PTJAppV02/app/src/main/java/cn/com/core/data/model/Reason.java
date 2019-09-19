package cn.com.core.data.model;

import java.util.ArrayList;

public class Reason {
    private String title;
    private String content;
    private String reason;
    private boolean checked;
    private ArrayList<Reason> listDatas;


    //  标题数组;
    private String[] titles  ={"确认劳动关系",
                               "计时工资",
                               "带薪年休假工资",
                               "病假工资",
                               "试用期工资",
                               "加班费：延时加班工资",
                               "加班费：休息日加班工资",
                               "加班费：法定节假日加班工资",
                               "经济补偿",
                               "赔偿金",
                               "解除劳动合同代通知金",
                               "二倍工资差额",
                               "福利：防暑降温费",
                               "福利：冬季取暖补贴",
                               "工伤保险待遇：工伤医疗费",
                               "工伤保险待遇：住院伙食补助费",
                               "工伤保险待遇：一次性伤残补助金",
                               "工伤保险待遇：一次性工伤医疗补助金",
                               "工伤保险待遇：职工伤残津贴",
                               "工伤保险待遇：停工留薪期工资",
                               "工伤保险待遇：一次性伤残就业补助金"};
    //  内容数组;
    private String[] contents={"要求确认双方自X年X月X日至X年X月X日期间存在劳动关系。",
                               "要求支付自X年X月X日至X年X月X日期间工资X元。",
                               "要求支付X年X月至X年X月期间未休带薪年休假工资X元。",
                               "要求支付X年X月X日至X年X月X日期间病假工资X元。",
                               "要求支付X年X月X日至X年X月X日试用期期间工资X元。",
                               "要求支付X年X月X日至X年X月X日期间延时加班工资X元，延时加班X小时。",
                               "要求支付X年X月X日至X年X月X日期间休息日加班工资X元，休息日加班X小时。",
                               "要求支付X年X月X日至X年X月X日期间法定节假日加班工资X元，法定节假日加班X小时。",
                               "要求支付解除劳动关系的经济补偿金X元。",
                               "要求支付违法解除劳动合同赔偿金X元。",
                               "要求支付解除劳动合同代通知金X元。",
                               "要求支付X年X月X日至X年X月X日期间未订立劳动合同二倍工资X元。",
                               "要求支付X年X月X日至X年X月X日期间防暑降温费X元。",
                               "要求支付X年X月X日至X年X月X日期间冬季取暖补贴X元。",
                               "要求支付X年X月X日至X年X月X日期间工伤医疗费X元。",
                               "要求支付X年X月X日至X年X月X日期间住院伙食补助X元。",
                               "要求支付一次性伤残补助金X元。",
                               "要求支付一次性工伤医疗补助金X元。",
                               "要求支付伤残津贴X元。",
                               "要求支付X年X月X日至X年X月X日停工留薪期期间工资X元。",
                               "要求支付一次性伤残就业补助金X元。"};
    //  理由数组;
    private String[] reasons={"申请人X年X月X日至X年X月X日期间在被申请人处工作，要求确认双方存在劳动关系",
                              "申请人X年X月X日至X年X月X日期间正常提供劳动，被申请人未支付本人该期间工资。",
                              "被申请人未支付申请人X年X月至X年X月期间带薪年休假工资。",
                              "本人X年X月X日至X年X月X日期间请病假，被申请人未支付该期间病假工资。",
                              "本人X年X月X日至X年X月X日期间为试用期，被申请人未支付该期间工资。",
                              "本人X年X月X日至X年X月X日期间存在延时加班情况，被申请人未支付该期间加班工资。",
                              "本人X年X月X日至X年X月X日期间存在休息日加班情况。",
                              "本人X年X月X日至X年X月X日期间存在法定节假日加班情况。",
                              "双方当事人于X年X月X日解除劳动关系。",
                              "被申请人于X年X月X日违法与申请人解除劳动关系。",
                              "双方当事人于X年X月X日解除劳动关系。",
                              "双方X年X月X日至X年X月X日期间未订立书面劳动合同。",
                              "被申请人未支付X年X月X日至X年X月X日期间防暑降温费。",
                              "被申请人未支付X年X月X日至X年X月X日期间冬季取暖补贴。",
                              "被申请人未支付X年X月X日至X年X月X日期间工伤医疗费。",
                              "被申请人未支付申请人X年X月X日至X年X月X日住院期间伙食补助。",
                              "经鉴定申请人为工伤X级，被申请人未支付申请人一次性伤残补助金。",
                              "经鉴定申请人为工伤X级，被申请人未支付申请人一次性工伤医疗补助金。",
                              "申请人经鉴定为工伤X级，被申请人未支付申请人伤残津贴。",
                              "经认定X年X月X日至X年X月X日为停工留薪期，被申请人未支付该期间工资。",
                              "申请人经鉴定为工伤X级，被申请人未支付申请人一次性伤残就业补助金。"};


    public Reason(String title, String content,String reason,boolean checked) {
        this.title = title;
        this.content = content;
        this.reason=reason;
        this.checked=checked;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getReason() {
        return reason;
    }

    public Reason() {
        super();
        listDatas=new ArrayList<>();
        int size=titles.length;
        //  将数据进行添加;
        for(int i=0;i<size;i++){
//            Log.i("MyLog","title:"+titles[i]+"content:"+contents[i]+"reason:"+reasons[i]);
            Reason reason=new Reason(titles[i],contents[i],reasons[i],false);
            listDatas.add(reason);
        }
    }

    public ArrayList<Reason> getListDatas() {

        return listDatas;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
