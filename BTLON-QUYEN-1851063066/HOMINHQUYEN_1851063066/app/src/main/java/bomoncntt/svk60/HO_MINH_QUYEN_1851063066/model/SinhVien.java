package bomoncntt.svk60.HO_MINH_QUYEN_1851063066.model;

public class SinhVien {

    private  String masv;
    private String tensv;
    private String gt;
    private String lop;

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    private String hinhanh;

    public SinhVien() {
    }

    public SinhVien(String masv, String tensv, String gt, String lop,String hinhanh) {
        this.masv = masv;
        this.tensv = tensv;
        this.gt = gt;
        this.lop = lop;
        this.hinhanh = hinhanh;
    }


    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getTensv() {
        return tensv;
    }

    public void setTensv(String tensv) {
        this.tensv = tensv;
    }

    public String getGt() {
        return gt;
    }

    public void setGt(String gt) {
        this.gt = gt;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    @Override
    public String toString() {
        return "SinhVien{" +
                "masv='" + masv + '\'' +
                ", tensv='" + tensv + '\'' +
                ", gt='" + gt + '\'' +
                ", lop='" + lop + '\'' +
                ", hinhanh='" + hinhanh + '\'' +
                '}';
    }
}
