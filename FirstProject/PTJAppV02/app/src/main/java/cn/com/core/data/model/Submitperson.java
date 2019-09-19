package cn.com.core.data.model;

import java.io.Serializable;

public class Submitperson implements Serializable {
    private boolean isInit;
    private String autoid;
    //  第1页填写内容;
    private String name="",birthday="",idcard="",sex="",
                   nation="",           // 民族
                   phone="",
                   householdaddress="", // 户籍地址
                   serviceaddress="",   // 送达地址
                   zipcode="",          // 邮政编码
                   urgentperson="",     // 紧急联系人
                   relationship="",relationtel="";
    //  第2页填写内容;
    private String unitname="",         // 单位名称
                   unitboss="",         // 法定代表
                   unitright="",        // 人员职务
                   unitaddress1="",     // 单位注册地址
                   unitaddress2="",     // 单位经营地址
                   unitcontactperson="",// 单位联系人
                   unitcontactright="", // 联系人职务
                   belongdept="",       // 所在部门
                   deptphone="";        // 单位电话
    //  第3页填写内容;
    private String submitreason="",submitcontent="";//    申请

    //  第4页内容;
    private String toaddress="",        // 送达地址;
                   tozipcode="",        // 邮政编码;
                   tocontactphone="",   // 联系电话;
                   tomailaddress="",    // 电子邮箱;
                   towechat="",         // 用户微信;
                   tocollector="",      // 代收人员;
                   tocollectorphone=""; // 联系电话;
    //  第5页内容;
    private String proofpath="";
    //  第6页内容;
    private String signature="";
    //  提交的时间;
    private String datetime="";

    public Submitperson() {
        //  进行初始化内容的控件表示;
        this.isInit=true;
    }

    public Submitperson(String autoid,String name, String birthday, String idcard, String sex, String nation, String phone, String householdaddress, String serviceaddress, String zipcode, String urgentperson, String relationship, String relationtel, String unitname, String unitboss, String unitright, String unitaddress1, String unitaddress2, String unitcontactperson, String unitcontactright, String belongdept, String deptphone, String submitreason, String submitcontent, String toaddress, String tozipcode, String tocontactphone, String tomailaddress, String towechat, String tocollector, String tocollectorphone, String proofpath, String signature) {
        this.autoid=autoid;
        this.name = name;
        this.birthday = birthday;
        this.idcard = idcard;
        this.sex = sex;
        this.nation = nation;
        this.phone = phone;
        this.householdaddress = householdaddress;
        this.serviceaddress = serviceaddress;
        this.zipcode = zipcode;
        this.urgentperson = urgentperson;
        this.relationship = relationship;
        this.relationtel = relationtel;
        this.unitname = unitname;
        this.unitboss = unitboss;
        this.unitright = unitright;
        this.unitaddress1 = unitaddress1;
        this.unitaddress2 = unitaddress2;
        this.unitcontactperson = unitcontactperson;
        this.unitcontactright = unitcontactright;
        this.belongdept = belongdept;
        this.deptphone = deptphone;
        this.submitreason = submitreason;
        this.submitcontent = submitcontent;
        this.toaddress = toaddress;
        this.tozipcode = tozipcode;
        this.tocontactphone = tocontactphone;
        this.tomailaddress = tomailaddress;
        this.towechat = towechat;
        this.tocollector = tocollector;
        this.tocollectorphone = tocollectorphone;
        this.proofpath = proofpath;
        this.signature = signature;
    }

    public Submitperson(String autoid,String name, String birthday, String idcard, String sex, String nation, String phone, String householdaddress, String serviceaddress, String zipcode, String urgentperson, String relationship, String relationtel, String unitname, String unitboss, String unitright, String unitaddress1, String unitaddress2, String unitcontactperson, String unitcontactright, String belongdept, String deptphone, String submitreason, String submitcontent, String toaddress, String tozipcode, String tocontactphone, String tomailaddress, String towechat, String tocollector, String tocollectorphone, String proofpath, String signature,String datetime) {
        this.autoid=autoid;
        this.name = name;
        this.birthday = birthday;
        this.idcard = idcard;
        this.sex = sex;
        this.nation = nation;
        this.phone = phone;
        this.householdaddress = householdaddress;
        this.serviceaddress = serviceaddress;
        this.zipcode = zipcode;
        this.urgentperson = urgentperson;
        this.relationship = relationship;
        this.relationtel = relationtel;
        this.unitname = unitname;
        this.unitboss = unitboss;
        this.unitright = unitright;
        this.unitaddress1 = unitaddress1;
        this.unitaddress2 = unitaddress2;
        this.unitcontactperson = unitcontactperson;
        this.unitcontactright = unitcontactright;
        this.belongdept = belongdept;
        this.deptphone = deptphone;
        this.submitreason = submitreason;
        this.submitcontent = submitcontent;
        this.toaddress = toaddress;
        this.tozipcode = tozipcode;
        this.tocontactphone = tocontactphone;
        this.tomailaddress = tomailaddress;
        this.towechat = towechat;
        this.tocollector = tocollector;
        this.tocollectorphone = tocollectorphone;
        this.proofpath = proofpath;
        this.signature = signature;
        this.datetime=datetime;
    }

    public String getAutoid() {
        return autoid;
    }

    public void setAutoid(String autoid) {
        this.autoid = autoid;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getIdcard() {
        return idcard;
    }

    public String getNation() {
        return nation;
    }

    public String getPhone() {
        return phone;
    }

    public String getHouseholdaddress() {
        return householdaddress;
    }

    public String getServiceaddress() {
        return serviceaddress;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getUrgentperson() {
        return urgentperson;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getRelationtel() {
        return relationtel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setHouseholdaddress(String householdaddress) {
        this.householdaddress = householdaddress;
    }

    public void setServiceaddress(String serviceaddress) {
        this.serviceaddress = serviceaddress;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setUrgentperson(String urgentperson) {
        this.urgentperson = urgentperson;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setRelationtel(String relationtel) {
        this.relationtel = relationtel;
    }

    public String getUnitname() {
        return unitname;
    }

    public String getUnitboss() {
        return unitboss;
    }

    public String getUnitright() {
        return unitright;
    }

    public String getUnitaddress1() {
        return unitaddress1;
    }

    public String getUnitaddress2() {
        return unitaddress2;
    }

    public String getUnitcontactperson() {
        return unitcontactperson;
    }

    public String getUnitcontactright() {
        return unitcontactright;
    }

    public String getBelongdept() {
        return belongdept;
    }

    public String getDeptphone() {
        return deptphone;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public void setUnitboss(String unitboss) {
        this.unitboss = unitboss;
    }

    public void setUnitright(String unitright) {
        this.unitright = unitright;
    }

    public void setUnitaddress1(String unitaddress1) {
        this.unitaddress1 = unitaddress1;
    }

    public void setUnitaddress2(String unitaddress2) {
        this.unitaddress2 = unitaddress2;
    }

    public void setUnitcontactperson(String unitcontactperson) {
        this.unitcontactperson = unitcontactperson;
    }

    public void setUnitcontactright(String unitcontactright) {
        this.unitcontactright = unitcontactright;
    }

    public void setBelongdept(String belongdept) {
        this.belongdept = belongdept;
    }

    public void setDeptphone(String deptphone) {
        this.deptphone = deptphone;
    }
    //

    public boolean isInit() {
        return isInit;
    }

    public String getSubmitreason() {
        return submitreason;
    }

    public String getSubmitcontent() {
        return submitcontent;
    }

    public String getToaddress() {
        return toaddress;
    }

    public String getTozipcode() {
        return tozipcode;
    }

    public String getTocontactphone() {
        return tocontactphone;
    }

    public String getTomailaddress() {
        return tomailaddress;
    }

    public String getTowechat() {
        return towechat;
    }

    public String getTocollector() {
        return tocollector;
    }

    public String getTocollectorphone() {
        return tocollectorphone;
    }

    public void setInit(boolean init) {
        isInit = init;
    }

    public void setSubmitreason(String submitreason) {
        this.submitreason = submitreason;
    }

    public void setSubmitcontent(String submitcontent) {
        this.submitcontent = submitcontent;
    }

    public void setToaddress(String toaddress) {
        this.toaddress = toaddress;
    }

    public void setTozipcode(String tozipcode) {
        this.tozipcode = tozipcode;
    }

    public void setTocontactphone(String tocontactphone) {
        this.tocontactphone = tocontactphone;
    }

    public void setTomailaddress(String tomailaddress) {
        this.tomailaddress = tomailaddress;
    }

    public void setTowechat(String towechat) {
        this.towechat = towechat;
    }

    public void setTocollector(String tocollector) {
        this.tocollector = tocollector;
    }

    public void setTocollectorphone(String tocollectorphone) {
        this.tocollectorphone = tocollectorphone;
    }

    public String getProofpath() {
        return proofpath;
    }

    public String getSignature() {
        return signature;
    }

    public void setProofpath(String proofpath) {
        this.proofpath = proofpath;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
