package lt.bit.todo.data;

//import com.password4j.Password;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.DatatypeConverter;

@Entity
@Table(name = "vartotojai")
public class Vartotojas implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String vardas;
    @Column(nullable = false)
    private String slaptazodis;
    @Column(nullable = false)
    private Boolean admin;
//    @Column(nullable = false)
//    private String salt;
//    @OneToMany(mappedBy = "vartotojas")
//    private List<Uzduotis> uzduotys;

    public Vartotojas() {
        this.admin = false;
//        this.uzduotys = new ArrayList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVardas() {
        return vardas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public String getSlaptazodis() {
        return slaptazodis;
    }

    public void setSlaptazodis(String slaptazodis) {
        this.slaptazodis = slaptazodis;
    }

//    public String getDecocedSlaptazodis() {
//        if (this.slaptazodis == null) {
//            return null;
//        }
//        String decoded = "";
//        for (int i = 0; i < this.slaptazodis.length(); i++) {
//            char c = this.slaptazodis.charAt(i);
//            c -= 4 + i;
//            decoded += c;
//        }
//        return decoded;
//    }
//
//    public void setDecodedSlaptazodis(String slaptazodis) {
//        if (slaptazodis == null) {
//            this.slaptazodis = null;
//        } else {
//            String encoded = "";
//            for (int i = 0; i < slaptazodis.length(); i++) {
//                char c = slaptazodis.charAt(i);
//                c += 4 + i;
//                encoded += c;
//            }
//            this.slaptazodis = encoded;
//        }
//    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

//    public String getSalt() {
//        return salt;
//    }
//
//    public void setSalt(String salt) {
//        this.salt = salt;
//    }
    
//    public List<Uzduotis> getUzduotys() {
//        return uzduotys;
//    }
//
//    public void setUzduotys(List<Uzduotis> uzduotys) {
//        this.uzduotys = uzduotys;
//    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vartotojas other = (Vartotojas) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Vartotojas{" + "id=" + id + ", vardas=" + vardas + ", slaptazodis=" + slaptazodis + ", admin=" + admin + '}';
    }

    public static String encodePassword(String password) {
        if (password == null) {
            return null;
        } else {
            String encoded = null;
            try {
                MessageDigest md = MessageDigest.getInstance("SHA512");
                md.update(password.getBytes());
                byte[] digest = md.digest();
                encoded = DatatypeConverter.printHexBinary(digest).toLowerCase();
            } catch (NoSuchAlgorithmException ex) {
                System.err.println("Nepavyko uzsifruoti:");
                System.err.println(ex);
            }
            return encoded;
        }
    }
    
    public static String encodePassword(String password, String salt) {
        if (password == null) {
            return null;
        }
        if (salt == null) {
            salt = "";
        }
        String withSalt = password + salt;
        return encodePassword(withSalt);
    }
    
//    public static String encodeNewPassword(String password) {
//        return Password
//                .hash(password)
//                .addRandomSalt()
//                .withArgon2()
//                .getResult();
//    }
    
//    public static boolean checkPassword(String password, String realPassword) {
//        return Password
//                .check(password, realPassword)
//                .withArgon2();
//    }

    public static String randomString(int min, int max) {
        if (min < 0 || min > 200) {
            min = 10;
        }
        if (max < 0 || max > 200) {
            max = 20;
        }
        if (min > max) {
            int tmp = min;
            min = max;
            max = tmp;
        }
        int len = (int) (Math.random() * (max - min + 1) + min);
        String r = "";
        for (int i = 0; i < len; i++) {
            char c = (char) (Math.random() * 25 + 97);
            r += c;
        }
        return r;
    }

}
