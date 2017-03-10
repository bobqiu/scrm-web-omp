package com.youanmi.scrm.omp.util;

/**
 * @author liujie
 * @date 2015年7月15日
 * @since YouAnMi-OTO
 */
public class ImageUrlAppIconDTO {

    // ios的58 80 87 120 180的图标
    private String img_58;
    private String img_80;
    private String img_87;
    private String img_120;
    private String img_120_3x;
    private String img_180;

    // 安卓的48 72 96 144的图标
    private String img_48;
    private String img_72;
    private String img_96;
    private String img_144;


    public ImageUrlAppIconDTO() {
        super();
    }


    public ImageUrlAppIconDTO(String img_58, String img_80, String img_87, String img_120, String img_120_3x,
            String img_180, String img_48, String img_72, String img_96, String img_144) {
        super();
        this.img_58 = img_58;
        this.img_80 = img_80;
        this.img_87 = img_87;
        this.img_120 = img_120;
        this.img_120_3x = img_120_3x;
        this.img_180 = img_180;
        this.img_48 = img_48;
        this.img_72 = img_72;
        this.img_96 = img_96;
        this.img_144 = img_144;
    }


    public String getImg_58() {
        return img_58;
    }


    public void setImg_58(String img_58) {
        this.img_58 = img_58;
    }


    public String getImg_80() {
        return img_80;
    }


    public void setImg_80(String img_80) {
        this.img_80 = img_80;
    }


    public String getImg_87() {
        return img_87;
    }


    public void setImg_87(String img_87) {
        this.img_87 = img_87;
    }


    public String getImg_120() {
        return img_120;
    }


    public void setImg_120(String img_120) {
        this.img_120 = img_120;
    }


    public String getImg_180() {
        return img_180;
    }


    public void setImg_180(String img_180) {
        this.img_180 = img_180;
    }


    public String getImg_48() {
        return img_48;
    }


    public void setImg_48(String img_48) {
        this.img_48 = img_48;
    }


    public String getImg_72() {
        return img_72;
    }


    public void setImg_72(String img_72) {
        this.img_72 = img_72;
    }


    public String getImg_96() {
        return img_96;
    }


    public void setImg_96(String img_96) {
        this.img_96 = img_96;
    }


    public String getImg_144() {
        return img_144;
    }


    public void setImg_144(String img_144) {
        this.img_144 = img_144;
    }


    public String getImg_120_3x() {
        return img_120_3x;
    }


    public void setImg_120_3x(String img_120_3x) {
        this.img_120_3x = img_120_3x;
    }


    public String getUrls() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.img_58).append(",").append(this.img_80).append(",").append(this.img_87).append(",")
            .append(this.img_120).append(",").append(this.img_120_3x).append(",").append(this.img_180)
            .append(",").append(this.img_48).append(",").append(this.img_72).append(",").append(this.img_96)
            .append(",").append(this.img_144);
        return sb.toString();
    }

}
