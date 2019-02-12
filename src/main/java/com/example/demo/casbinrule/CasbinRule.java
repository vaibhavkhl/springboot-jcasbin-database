package com.example.demo.casbinrule;

import com.example.demo.AuditModel;
import com.example.demo.casbinrule.CasbinRuleId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(CasbinRuleId.class)
@Table(name = "casbin_rule")
public class CasbinRule implements Serializable {
//    @Id
//    @GeneratedValue(generator = "id_generator")
//    @SequenceGenerator(
//            name = "id_generator",
//            sequenceName = "id_sequence",
//            initialValue = 1
//    )
//    private Long id;


    private String p_type;

    @Id
    private String v0;



    @Id
    private String v1;


    private String v2;


    private String v3;

    public String getP_type() {
        return p_type;
    }

    public void setP_type(String p_type) {
        this.p_type = p_type;
    }

    public String getV0() {
        return v0;
    }

    public void setV0(String v0) {
        this.v0 = v0;
    }


//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3;
    }

}
